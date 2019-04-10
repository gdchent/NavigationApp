package com.navigationapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SecondNativeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_native);

        findViewById(R.id.btn_rn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                startActivity(new Intent(SecondNativeActivity.this,MainActivity.class));
                MainApplication.getUpdateDownloadPackage().updateDownloadModule.nativeCallRnDetail("abc");

            }
        });
        findViewById(R.id.btn_sendmsg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SecondNativeActivity.this,MainActivity.class));
                MainApplication.getUpdateDownloadPackage().updateDownloadModule.nativeCallRnDetail("我是原生值");

            }
        });
    }
}
