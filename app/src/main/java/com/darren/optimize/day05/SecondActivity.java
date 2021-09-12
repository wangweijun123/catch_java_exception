package com.darren.optimize.day05;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void jump(View view){
        // 执行到了这里，会有一个异常，虚拟机拿到了这个异常，
        Integer.parseInt("0x01");
    }
}
