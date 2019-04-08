package com.funtsui.updatelib.constants;

import android.content.Context;
import android.support.annotation.NonNull;

import java.net.URL;

public class AppCache {

    /**
     * 全局上下文
     */
    public static Context CONTEXT;
    /**
     * 域名
     */
    public static String BASE_URL = "https://www-api2.xartn.com/";
    /**
     * 渠道
     */
    public static String CHANNEL = "";
    /**
     * 邀请码
     */
    public static String PROXY_CODE = "";


    /**
     * 获取全局context
     *
     * @return 全局context
     */
    @NonNull
    public static Context getContext() {
        if (CONTEXT == null) {
            throw new RuntimeException("请调用UpdateUtil的init() 初始化更新模块");
        }
        return CONTEXT;
    }
}
