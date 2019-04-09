package com.navigationapp.module;

import android.content.Context;
import android.util.Log;

import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.funtsui.updatelib.UpdateUtil;
import com.funtsui.updatelib.network.BaseNetApi;


public class UpdateDownloadModule extends ReactContextBaseJavaModule {

    public static final String MODULE_NAME="updateDownloadModule";
    public static final String EVENT_NAME = "nativeCallRn";
    public ReactApplicationContext context ;

    public UpdateDownloadModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.context = reactContext;
    }

    @Override
    public String getName() {
        return MODULE_NAME;
    }

    @Override
    public void initialize() {
        Log.i("gdchent","module_initialize");
        UpdateUtil.checkUpdate(new BaseNetApi.CallBack() {
            @Override
            public void callBackMethod(String apkUrl) {
                nativeCallRn(apkUrl);
            }
        });
    }

    @Override
    public boolean canOverrideExistingModule() {
        return false;
    }

    @Override
    public void onCatalystInstanceDestroy() {

    }

    /**
     * Native调用RN
     * @param msg
     */
    public void nativeCallRn(String msg) {
        Log.i("gdchent","apkUrl"+msg);
//        context.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
//                .emit(EVENT_NAME,msg);

    }
}
