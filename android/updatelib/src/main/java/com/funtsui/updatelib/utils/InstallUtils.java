package com.funtsui.updatelib.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.FileProvider;
import android.util.Log;

import java.io.File;
import java.io.IOException;

public class InstallUtils {

    private static final String TAG = "InstallUtils";

    /**
     * 安装回调监听
     */
    public interface InstallCallBack {
        void onSuccess();

        void onFail(Exception e);
    }

    /**
     * 安装APK工具类
     *
     * @param context  上下文
     * @param filePath 文件路径
     * @param callBack 安装界面成功调起的回调
     */
    public static void installAPK(FragmentActivity context, String filePath, final InstallCallBack callBack) {
        try {
            changeApkFileMode(new File(filePath));
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            File apkFile = new File(filePath);
            Uri apkUri;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                // 授予目录临时共享权限
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                String authority = context.getPackageName() + ".fileProvider";
                apkUri = FileProvider.getUriForFile(context, authority, apkFile);
            } else {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                apkUri = Uri.fromFile(apkFile);
            }
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
            new ActResultRequest(context).startForResult(intent, new ActForResultCallback() {
                @Override
                public void onActivityResult(int resultCode, Intent data) {
                    Log.i(TAG, "onActivityResult:" + resultCode);
                    //调起了系统安装页面
                    if (callBack != null) {
                        callBack.onSuccess();
                    }
                }
            });

        } catch (Exception e) {
            if (callBack != null) {
                callBack.onFail(e);
            }
        }
    }

    /**
     * 通过浏览器下载APK更新安装
     *
     * @param context    上下文
     * @param httpUrlApk APK下载地址
     */
    public static void installAPKWithBrower(Context context, String httpUrlApk) {
        Uri uri = Uri.parse(httpUrlApk);
        Intent viewIntent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(viewIntent);
    }

    /**
     * 8.0权限检查回调监听
     */
    public interface InstallPermissionCallBack {
        void onGranted();

        void onDenied();
    }


    /**
     * 检查有没有安装权限
     *
     * @param activity
     * @param installPermissionCallBack
     */
    public static void checkInstallPermission(FragmentActivity activity, InstallPermissionCallBack installPermissionCallBack) {
        if (hasInstallPermission(activity)) {
            if (installPermissionCallBack != null) {
                installPermissionCallBack.onGranted();
            }
        } else {
            openInstallPermissionSetting(activity, installPermissionCallBack);
        }
    }

    /**
     * 判断有没有安装权限
     *
     * @param context
     * @return
     */
    public static boolean hasInstallPermission(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //先获取是否有安装未知来源应用的权限
            return context.getPackageManager().canRequestPackageInstalls();
        }
        return true;
    }

    /**
     * 去打开安装权限的页面
     *
     * @param activity
     * @param installPermissionCallBack
     */
    public static void openInstallPermissionSetting(FragmentActivity activity, final InstallPermissionCallBack installPermissionCallBack) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Uri packageURI = Uri.parse("package:" + activity.getPackageName());
            Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
            new ActResultRequest(activity).startForResult(intent, new ActForResultCallback() {
                @Override
                public void onActivityResult(int resultCode, Intent data) {
                    Log.i(TAG, "onActivityResult:" + resultCode);
                    if (resultCode == Activity.RESULT_OK) {
                        //用户授权了
                        if (installPermissionCallBack != null) {
                            installPermissionCallBack.onGranted();
                        }
                    } else {
                        //用户没有授权
                        if (installPermissionCallBack != null) {
                            installPermissionCallBack.onDenied();
                        }
                    }
                }
            });
        } else {
            //用户授权了
            if (installPermissionCallBack != null) {
                installPermissionCallBack.onGranted();
            }
        }

    }

    public static void changeApkFileMode(File file) {
        try {
            //apk放在缓存目录时，低版本安装提示权限错误，需要对父级目录和apk文件添加权限
            String cmd1 = "chmod 777 " + file.getParent();
            Runtime.getRuntime().exec(cmd1);

            String cmd = "chmod 777 " + file.getAbsolutePath();
            Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
