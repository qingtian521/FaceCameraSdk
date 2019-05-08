package sdk.facecamera.demo;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import sdk.facecamera.demo.crash.CrashHandler;

/**
 * Created by 云中双月 on 2018/4/2.
 */

public class CameraApplication extends Application{


    private static Handler mHandler;
    private  static Context mContext;
    private static int mainThreadId;

    @Override
    public void onCreate() {
        super.onCreate();

        mHandler = new Handler();
        mContext = getApplicationContext();
        mainThreadId = android.os.Process.myPid();

        CrashHandler.getInstance().init(this,true);
    }


    public static Handler getHandler(){
        return mHandler;
    }
    public static int getMainThreadId(){
        return mainThreadId;
    }

    public static Context getContext() {
        return mContext;
    }
}
