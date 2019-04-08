package com.funtsui.updatelib.utils;

import android.content.Context;
import android.support.annotation.StringRes;
import android.widget.Toast;

public class ToastUtils {

    public static void toast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void toast(Context context, @StringRes int msgRes) {
        Toast.makeText(context, context.getString(msgRes), Toast.LENGTH_SHORT).show();
    }
}
