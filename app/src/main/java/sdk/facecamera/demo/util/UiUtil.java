package sdk.facecamera.demo.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Looper;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import sdk.facecamera.demo.CameraApplication;

/**
 * Created by 云中双月 on 2018/4/2.
 */

public class UiUtil {
    /**
     *byte[]转bitmap
     */
    public static Bitmap Bytes2Bimap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }

    public static byte[] Bytes2ToByte(byte[][] bytes2){
        byte[] byte1d;
        int len = 0;
        for (byte[] element : bytes2) {
            len += element.length;
        }
        byte1d = new byte[len];
        int index = 0;
        for (byte[] array : bytes2) {
            for (byte element : array) {
                byte1d[index++] = element;
            }
        }
        return byte1d;
    }


    //一维数组转化为二维数组
    public static float[][] TwoArry(float[] onefloat){
        float[][] arr=new float[1][onefloat.length];
        for (int i = 0; i < onefloat.length; i++) {
            arr[0][i]=onefloat[i];
        }
        return arr;
    }

    //一维数组转化为二维数组
    public static byte[][] bmpTwoArry2(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] onefloat = baos.toByteArray();
        byte[][] arr=new byte[1][onefloat.length];
        for (int i = 0; i < onefloat.length; i++) {
            arr[0][i]=onefloat[i];
        }
        return arr;
    }

    public static Context getContext(){
        return CameraApplication.getContext();
    }
    public static Handler getHandler(){
        return CameraApplication.getHandler();
    }
    public static int getMainThreadId(){
        return CameraApplication.getMainThreadId();
    }


    //////////////////判断是否运行在主线程///////////
    public static boolean isRunOnUIThread(){
        //在android6.0系统中不行
      /*  int mypid = android.os.Process.myPid();
        if (mypid == getMainThreadId()){
            return true;
        }
        return false;*/
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }
    //运行在主线程
    public static void runOnUIThread(Runnable r){
        if(isRunOnUIThread()){
            //如果是主线程，就直接运行
            r.run();
        }else{
            //如果在子线程，就借助handler让其在主线程运行
            getHandler().post(r);
        }
    }

    // formatType格式为yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
    // data Date类型的时间
    public static String dateToString(Date data, String formatType) {
        return new SimpleDateFormat(formatType).format(data);
    }
}
