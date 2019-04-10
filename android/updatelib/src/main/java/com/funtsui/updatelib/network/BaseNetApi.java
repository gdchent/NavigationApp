package com.funtsui.updatelib.network;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.funtsui.updatelib.bean.StatisticsBean;
import com.funtsui.updatelib.constants.ApiConstant;
import com.funtsui.updatelib.constants.AppCache;
import com.funtsui.updatelib.constants.Constants;
import com.funtsui.updatelib.log.KLog;
import com.funtsui.updatelib.ui.dialog.UpdateDialog;
import com.funtsui.updatelib.utils.EncryptUtils;

import org.json.JSONObject;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class BaseNetApi {

    private static final String TAG = "BaseNetApi";

    private static Charset UTF_8 = Charset.forName("UTF-8");

    private static RequestQueue mRequestQueue;

    public static void init(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
    }

    public static RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    public static void checkUpdate(final BaseNetApi.CallBack callBack) {
        String packageName = AppCache.getContext().getPackageName().replace(".", "_");
        Log.i("gdchent","packageName:"+packageName);
        String url =
                AppCache.BASE_URL +
                        ApiConstant.STATISTICS +
                        packageName +
                        "_" +
                        AppCache.CHANNEL +
                        AppCache.PROXY_CODE;

        String testUrl =
                AppCache.BASE_URL +
                        ApiConstant.STATISTICS +
                        "?channel_id=" +
                        "market";
        Log.i("gdchent","url:"+testUrl);
//        JsonObjectRequest request = new JsonObjectRequest(url, null,
        JsonObjectRequest request = new JsonObjectRequest(testUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("gdchent",":"+response.toString());
                        StatisticsBean bean = StatisticsBean.fromJson(response);
                        if (bean != null && bean.getData() != null && bean.getData().getStatus() == 1) {
                            StatisticsBean.DataBean data = bean.getData();
                            String iv = new StringBuilder(data.getRandom()).reverse().toString();
                            byte[] bytes = EncryptUtils.decryptBase64AES(
                                    data.getSign().getBytes(UTF_8),
                                    data.getRandom().getBytes(UTF_8), "AES/CBC/PKCS5Padding",
                                    iv.getBytes(UTF_8)
                            );
                            String url = new String(bytes, UTF_8);
                            callBack.callBackMethod(url);
//                            Intent intent = new Intent(AppCache.getContext(), UpdateDialog.class);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            intent.putExtra(Constants.KEY_DOWNLOAD_URL, url);
//                            AppCache.getContext().startActivity(intent);
                        } else {
                            KLog.i(TAG, "没有更新");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                String uuid=getUUid();
                Log.i("gdchent","uuid:"+uuid);
                headers.put("HTTP_UUID", getUUid());
                return headers;
            }
        };
        getRequestQueue().add(request);
    }

    public static String getUUid() {
        return Settings.Secure.getString(AppCache.getContext().getContentResolver(),
                Settings.Secure.ANDROID_ID) + Build.SERIAL;
    }

    public interface CallBack{
        void callBackMethod(String apkUrl);
    }
}