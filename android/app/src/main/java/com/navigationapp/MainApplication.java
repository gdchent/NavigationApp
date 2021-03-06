package com.navigationapp;

import android.app.Application;

import com.facebook.react.ReactApplication;
import com.funtsui.updatelib.UpdateUtil;
import com.navigationapp.publicshpackage.UpdateDownloadPackage;
import com.microsoft.codepush.react.CodePush;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.shell.MainReactPackage;
import com.facebook.soloader.SoLoader;
import com.swmansion.gesturehandler.react.RNGestureHandlerPackage;

import java.util.Arrays;
import java.util.List;

public class MainApplication extends Application implements ReactApplication {


    private static UpdateDownloadPackage updateDownloadPackage = new UpdateDownloadPackage();
    private final ReactNativeHost mReactNativeHost = new ReactNativeHost(this) {

        @Override
        protected String getJSBundleFile() {
            return CodePush.getJSBundleFile();
        }


        @Override
        protected List<ReactPackage> getPackages() {
            return Arrays.<ReactPackage>asList(
                    new MainReactPackage(),
                    updateDownloadPackage,
                    new RNGestureHandlerPackage(),
                    new CodePush(getResources().getString(R.string.reactNativeCodePush_androidDeploymentKey), getApplicationContext(), BuildConfig.DEBUG)

            );
        }

        @Override
        protected String getJSMainModuleName() {
            return "index";
        }

        @Override
        public boolean getUseDeveloperSupport() {
            return BuildConfig.DEBUG;
        }
    };


    @Override
    public ReactNativeHost getReactNativeHost() {
        return mReactNativeHost;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SoLoader.init(this, /* native exopackage */ false);
        UpdateUtil.init(this, "https://www-api2.sayahao.com/",
                "tx", "123456789");
    }

    /**
     * 获取下载模块
     */
    public static UpdateDownloadPackage getUpdateDownloadPackage() {
        return updateDownloadPackage;
    }
}
