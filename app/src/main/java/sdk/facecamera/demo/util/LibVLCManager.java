package sdk.facecamera.demo.util;

import android.content.Context;
import android.net.Uri;
import android.view.SurfaceView;

import org.videolan.libvlc.IVLCVout;
import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.Media;
import org.videolan.libvlc.MediaPlayer;

import java.util.ArrayList;

public class LibVLCManager {

    /**
     * 视频播放相关
     *
     * @param savedInstanceState
     */
    private LibVLC libVLC = null;
    private MediaPlayer mediaPlayer; //libvlc下

    public LibVLCManager() {
    }

    public void initPlayer(String ip, SurfaceView surfaceView) {
        if (mediaPlayer != null && libVLC != null) {
            releasePlayer();
        }
        ArrayList<String> options = new ArrayList<>();
        options.add("--aout=opensles");
        options.add("--audio-time-stretch");
        options.add("-vvv");
        libVLC = new LibVLC(options);

        mediaPlayer = new MediaPlayer(libVLC);
        IVLCVout vout = mediaPlayer.getVLCVout();
        vout.setVideoView(surfaceView);
        vout.attachViews();

//        vout.setWindowSize(surfaceView.getWidth(), surfaceView.getHeight());

        Media m = new Media(libVLC, Uri.parse(String.format("rtsp://%s", ip)));
        int cache = 1000;
        m.addOption(":network-caching=" + cache);
        m.addOption(":file-caching=" + cache);
        m.addOption(":live-cacheing=" + cache);
        m.addOption(":sout-mux-caching=" + cache);
        m.addOption(":codec=mediacodec,iomx,all");
        mediaPlayer.setMedia(m);
        startPlay();
        mediaPlayer.setEventListener(new MediaPlayer.EventListener() {
            @Override
            public void onEvent(MediaPlayer.Event event) {
                LogUtils.i("MediaPlayer event:" + event.getTimeChanged());
            }
        });
    }

    /**
     * 开始播放
     */
    public void startPlay(){
        if(mediaPlayer!=null){
            mediaPlayer.play();
        }
    }

    /**
     * 暂停播放
     */
    public void pausePlay(){
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    public void releasePlayer() {
        if (mediaPlayer != null && libVLC != null) {
            mediaPlayer.stop();
            IVLCVout vout = mediaPlayer.getVLCVout();
            vout.detachViews();
            libVLC.release();
            mediaPlayer.release();
            mediaPlayer = null;
            libVLC = null;
        }
    }
}
