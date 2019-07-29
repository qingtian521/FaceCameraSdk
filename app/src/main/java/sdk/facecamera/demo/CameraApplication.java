package sdk.facecamera.demo;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import sdk.facecamera.demo.crash.CrashHandler;

/**
 * Created by 云中双月 on 2018/4/2.
 */

public class CameraApplication extends Application{


    private static Handler mHandler;
    private  static Context mContext;
    private static int mainThreadId;
    private RefWatcher refWatcher;

    @Override
    public void onCreate() {
        super.onCreate();

        mHandler = new Handler();
        mContext = getApplicationContext();
        mainThreadId = android.os.Process.myPid();
        refWatcher = setupLeakCanary();
        CrashHandler.getInstance().init(this,true);
    }

    private RefWatcher setupLeakCanary(){
        if(LeakCanary.isInAnalyzerProcess(this)) return RefWatcher.DISABLED;
        return LeakCanary.install(this);
    }

    /**
     * 获取RefWatcher
     */
    public static RefWatcher getRefWather(Context context) {
        CameraApplication leakApp = (CameraApplication) context.getApplicationContext();
        return leakApp.refWatcher;
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
