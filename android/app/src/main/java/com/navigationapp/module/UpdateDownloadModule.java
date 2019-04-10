package com.navigationapp.module;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.funtsui.updatelib.UpdateUtil;
import com.funtsui.updatelib.network.BaseNetApi;
import com.navigationapp.NativeActivity;


public class UpdateDownloadModule extends ReactContextBaseJavaModule {

    public static final String MODULE_NAME = "updateDownloadModule";
    public static final String EVENT_NAME = "nativeCallRn";
    public static final String EVENT_DETAIL_NAME = "nativeCallRnDetail";
    public ReactApplicationContext context;

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
        Log.i("gdchent", "module_initialize");
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
     *
     * @param msg
     */
    public void nativeCallRn(String msg) {
        Log.i("gdchent", "apkUrl" + msg);
        context.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(EVENT_NAME, msg);

    }


    /**
     * 进入RN的详情页面
     */
    public void nativeCallRnDetail(String msg){
        Log.i("gdchent", "apkUrl" + msg);
        context.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(EVENT_DETAIL_NAME, msg);
    }

    /**
     * @param phone
     */
    @ReactMethod
    public void rnCallNative(String phone) {
// 跳转到打电话界面
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // 跳转需要添加flag, 否则报错
        context.startActivity(intent);
    }


    @ReactMethod
    public void gotoActivity(){
        Intent intent=new Intent(context, NativeActivity.class);
        context.startActivity(intent);
    }

    /**
     * Callback 方式
     * rn调用Native,并获取返回值
     *
     * @param msg
     * @param callback
     */
    @ReactMethod
    public void rnCallNativeFromCallback(String msg, Callback callback) {

        // 1.处理业务逻辑...
        String result = "处理结果：" + msg;
        // 2.回调RN,即将处理结果返回给RN
        callback.invoke(result);
    }


    /**
     * Promise
     * @param msg
     * @param promise
     */
    @ReactMethod
    public void rnCallNativeFromPromise(String msg, Promise promise) {

        Log.e("---","adasdasda");
        // 1.处理业务逻辑...
        String result = "处理结果：" + msg;
        // 2.回调RN,即将处理结果返回给RN
        promise.resolve(result);
    }

}
