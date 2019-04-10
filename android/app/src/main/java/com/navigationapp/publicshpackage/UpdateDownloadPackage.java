package com.navigationapp.publicshpackage;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;
import com.navigationapp.module.UpdateDownloadModule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 下载Package
 */
public class UpdateDownloadPackage implements ReactPackage {

    public static UpdateDownloadModule updateDownloadModule ;
    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
        List<NativeModule> modules=new ArrayList<>();
        updateDownloadModule=new UpdateDownloadModule(reactContext);
        modules.add(updateDownloadModule);
        return modules;
    }

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        return Collections.emptyList();
    }
}
