package sdk.facecamera.sdk.utils;

import android.content.Context;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaFormat;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by 任梦林 on 2018/4/16.
 */

public class H264Decoder {
    private SurfaceHolder mHolder;
    private Context mContext;
    private MediaCodec mCodec;
    private boolean isStart; //标志位，判断是否在播放
    private byte[] lastBuf;
    private List<byte[]> nals = new ArrayList<>(30);
    private boolean csdSet = false; // sps和pps是否设置，在mediacodec里面可以不显式设置到mediaformat，但需要在buffer的前两帧给出

    public H264Decoder(SurfaceHolder holder, Context ctx){
        mHolder = holder;
        mContext = ctx;
    }

    public synchronized void handleH264(byte[] buffer) {
        byte[] typedAr = null;
        if (buffer == null || buffer.length < 1) return;
        if (lastBuf != null) {
            typedAr = new byte[buffer.length + lastBuf.length];
            System.arraycopy(lastBuf, 0, typedAr, 0, lastBuf.length);
            System.arraycopy(buffer, 0, typedAr, lastBuf.length, buffer.length);
        } else {
            typedAr = buffer;
        }
        int lastNalEndPos = 0;
        byte b1 = -1; // 前一个
        byte b2 = -2; // 前二个
        List<Integer> nalStartPos = new ArrayList<Integer>();
        for (int i = 0; i < typedAr.length - 1; i += 2) { // 可能出现的bug，length小于2
            byte b_0 = typedAr[i];
            byte b_1 = typedAr[i + 1];

            if (b1 == 0 && b_0 == 0 && b_1 == 0) {
                nalStartPos.add(i - 1);
            } else if (b_1 == 1 && b_0 == 0 && b1 == 0 && b2 == 0) {
                nalStartPos.add(i - 2);
            }
            b2 = b_0;
            b1 = b_1;
        }
        if (nalStartPos.size() > 1) {
            for (int i = 0; i < nalStartPos.size() - 1; ++i) {
                byte[] nal = new byte[nalStartPos.get(i + 1) - nalStartPos.get(i)];
                System.arraycopy(typedAr, nalStartPos.get(i), nal, 0, nal.length);
                nals.add(nal);
                lastNalEndPos = nalStartPos.get(i + 1);
            }
        } else {
            lastNalEndPos = nalStartPos.get(0);
        }
        if (lastNalEndPos != 0 && lastNalEndPos < typedAr.length) {
            lastBuf = new byte[typedAr.length - lastNalEndPos];
            System.arraycopy(typedAr, lastNalEndPos, lastBuf, 0, typedAr.length - lastNalEndPos);
        } else {
            if (lastBuf == null) {
                lastBuf = typedAr;
            }
            byte[] _newBuf = new byte[lastBuf.length + buffer.length];
            System.arraycopy(lastBuf, 0, _newBuf, 0, lastBuf.length);
            System.arraycopy(buffer, 0, _newBuf, lastBuf.length, buffer.length);
            lastBuf = _newBuf;
        }
        boolean sps = false;
        boolean pps = false;
        boolean lastSps = false;
        if (!csdSet) {
            if (nals.size() > 0) {
                Iterator<byte[]> it = nals.iterator();
                while (it.hasNext() && !csdSet) {
                    byte[] nal = it.next();
                    if ((nal[4] & 0x1f) == 7) {
                        sps = true;
                        lastSps = true;
                        continue;
                    }
                    if ((nal[4] & 0x1f) == 8 && lastSps) {
                        pps = true;
                        csdSet = true;
                        continue;
                    }
                    it.remove();
                }
            }
        }
        if (!csdSet) {
            nals.clear();
        }
        //LogUtils.i("csdSet");
        if (nals.size() > 0) {
            Iterator<byte[]> it = nals.iterator();
            while (it.hasNext()) {
                ByteBuffer inputBuffer;
                int inputBufferIndex = mCodec.dequeueInputBuffer(10);
                if (inputBufferIndex >= 0) {
                    // 版本判断。当手机系统小于 5.0 时，用arras
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        inputBuffer = mCodec.getInputBuffer(inputBufferIndex);
                    } else {
                        ByteBuffer[] inputBuffers = mCodec.getInputBuffers();
                        inputBuffer = inputBuffers[inputBufferIndex];
                    }
                    byte[] nal = it.next();
                    inputBuffer.put(nal);
                    mCodec.queueInputBuffer(inputBufferIndex, 0, nal.length, 0, 0);
                    it.remove();
                    continue;
                }
                break;
            }
        }
        /**
         *清理内存
         */

        while (nals.size() > 30) {
            nals.remove(0);
        }
        /*if (nals.size() >30){
            int idx = 0;
            while (idx++<30){
                nals.remove(0);
            }
            lastBuf = null;
        }*/
        MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
        int outputBufferIndex = mCodec.dequeueOutputBuffer(bufferInfo, 100);
        while (outputBufferIndex >= 0) {
            //一定要调用
            mCodec.releaseOutputBuffer(outputBufferIndex, true);
            outputBufferIndex = mCodec.dequeueOutputBuffer(bufferInfo, 0);
        }

    }

    public void play() {
        //ToastUtils.show("加载中，请稍后...");
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;
        try {
            mCodec = MediaCodec.createDecoderByType("video/avc");
            //软解
            //mCodec = MediaCodec.createByCodecName(mDecoder);
            MediaCodecInfo info = mCodec.getCodecInfo();
            MediaFormat mediaFormat = MediaFormat.createVideoFormat("video/avc", screenWidth, screenHeight);
            mCodec.configure(mediaFormat, mHolder.getSurface(), null, 0);
            mCodec.start();
            isStart = true;
        } catch (Exception ex) {
            isStart = false;
            Log.i("H264Decoder","初始化错误: " + ex.getMessage());
        }
        //"192.168.5.235"
        //boolean configConnectRet = client.connectConfigPort(ipStr, 9527, 500);
        //boolean streamConnectRet = client.connectStreamPort(ipStr, 20000, 500);
        /*if (configConnectRet && streamConnectRet) {

        } else {
            if (isStart) {
                mCodec.stop();
                //释放资源
                mCodec.release();
                isStart = false;
            }
            //ToastUtils.show("设备连接失败,请检查网络连接");
            //UIUtils.runOnUIThread(() -> ToastUtils.show("设备连接失败,请检查网络连接"));
            Log.i("H264Decoder","设备连接失败,请检查网络连接");
        }*/
    }

    public void stopPlay(){
        if (isStart){
            mCodec.stop();
            //释放资源
            mCodec.release();
            isStart = false;
        }
    }
}
