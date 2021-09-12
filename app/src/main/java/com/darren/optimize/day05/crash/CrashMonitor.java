package com.darren.optimize.day05.crash;

import android.app.Application;
import android.os.Looper;
import android.os.MessageQueue;
import android.util.Log;

import androidx.annotation.NonNull;

public class CrashMonitor implements MessageQueue.IdleHandler {
    private static final String LOG_TAG = CommonLog.LOG_COMMON +  "CrashMonitor";

    private static final CrashMonitor instance = new CrashMonitor();
    private Application mApplication;

    private CrashMonitor(){

    }

    public void init(Application application){
        Log.i(LOG_TAG, "addIdleHandler");
        Looper.myQueue().addIdleHandler(this);
        this.mApplication = application;
    }

    public static CrashMonitor getInstance() {
        return instance;
    }

    @Override
    public boolean queueIdle() {
        Log.i(LOG_TAG, "queueIdle ...");
        // 监听所有的 Activity
        mApplication.registerActivityLifecycleCallbacks(LifecycleCallback.getInstance());
        ActivityManager activityManager = new ActivityManager();
        final ProcessFinisher processFinisher = new ProcessFinisher(mApplication, true, activityManager);

        Log.i(LOG_TAG, "setDefaultUncaughtExceptionHandler");
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(@NonNull Thread t, @NonNull Throwable e) {
                Log.i(LOG_TAG, "uncaughtException e :" + e.getLocalizedMessage());
                // 如果大家想把这套集成到自己的项目中，一定要配合 bugly
                // 先走 bugly 逻辑然后再走我们这里
                // 少了没写上传崩溃信息到服务器
                // 如果自己写一套，这在里应该先上报到自己的后台服务器
                e.printStackTrace();
                // 1. 把所有的状态信息清空，service ，activity 这些尽量都干掉
                // 2. 然后退到首页，但是不闪退到桌面 （记录所有的 activity）
                processFinisher.finishLastActivity(t);
                // 3. 也不触发系统的检测（提示卸载 app），不走系统的默认逻辑
                processFinisher.endApplication();
            }
        });
        return false;
    }
}
