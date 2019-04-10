package com.navigationapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class NativeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native);
        findViewById(R.id.btn_nextact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(NativeActivity.this,SecondNativeActivity.class));
            }
        });
        findViewById(R.id.btn_rn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                startActivity(new Intent(NativeActivity.this,MainActivity.class));
                MainApplication.getUpdateDownloadPackage().updateDownloadModule.nativeCallRnDetail("abc");

            }
        });
        findViewById(R.id.btn_sendmsg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NativeActivity.this,MainActivity.class));
                MainApplication.getUpdateDownloadPackage().updateDownloadModule.nativeCallRnDetail("我是原生值");

            }
        });
    }



}
