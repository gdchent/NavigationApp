package com.funtsui.updatelib;

import android.content.Context;
import android.text.TextUtils;

import com.funtsui.updatelib.constants.AppCache;
import com.funtsui.updatelib.log.KLog;
import com.funtsui.updatelib.network.BaseNetApi;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateUtil {

    private static boolean debug = true;

    static {
        KLog.init(debug);
    }

    /**
     * 初始化
     *
     * @param baseUrl   域名
     * @param channel   渠道
     * @param proxyCode 代理码
     */
    public static void init(Context context, String baseUrl, String channel, String proxyCode) {
        AppCache.CONTEXT = context;
        BaseNetApi.init(context);
        String url = TextUtils.isEmpty(baseUrl) ? AppCache.BASE_URL : baseUrl;
        String pattern = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        boolean urlLegal = Pattern.matches(pattern, url);
        if (!urlLegal) {
            throw new RuntimeException("url不合法");
        }
        AppCache.BASE_URL = url;
        AppCache.CHANNEL = channel;
        AppCache.PROXY_CODE = proxyCode;
    }

    /**
     * 在主界面，进行检查是否有更新
     */
    public static void checkUpdate(BaseNetApi.CallBack callBack) {
        BaseNetApi.checkUpdate(callBack);
    }
}
