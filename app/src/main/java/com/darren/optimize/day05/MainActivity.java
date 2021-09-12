package com.darren.optimize.day05;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import com.darren.optimize.day05.crash.CrashMonitor;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 在首页的空闲时候触发
        CrashMonitor.getInstance().init(getApplication());
        /*new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                //主线程异常拦截
                while (true) {
                    try {
                        Looper.loop();//主线程的异常会从这里抛出
                    } catch (Throwable e) {
                        e.printStackTrace();
                        // 再上传到服务器，就不会隐藏问题了 ， 0.3% crash ->  20% bug
                    }
                }
            }
        });*/
        // 都知道这么写，但是底层的原理大家是否了解？
        /*final Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler =Thread.getDefaultUncaughtExceptionHandler();
        Log.e("TAG","defaultUncaughtExceptionHandler -> " + defaultUncaughtExceptionHandler);
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(@NonNull Thread t, @NonNull Throwable e) {
                // 为什么会进入这里，java 虚拟机怎么处理的？
                // Android 系统层又是怎么做处理的？
                // 对于大部分同学来讲无所谓，会闪退到桌面
                e.printStackTrace();
                defaultUncaughtExceptionHandler.uncaughtException(t, e);
            }
        });*/
    }

    public void jump(View view) {
        Intent intent = new Intent(this,SecondActivity.class);
        startActivity(intent);
    }
}
