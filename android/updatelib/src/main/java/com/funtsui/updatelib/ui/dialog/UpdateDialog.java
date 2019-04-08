package com.funtsui.updatelib.ui.dialog;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.funtsui.updatelib.R;
import com.funtsui.updatelib.constants.Constants;
import com.funtsui.updatelib.utils.HttpDownloadUtility;
import com.funtsui.updatelib.utils.InstallUtils;
import com.funtsui.updatelib.utils.ToastUtils;
import com.funtsui.updatelib.widget.DownloadView;
import com.funtsui.updatelib.widget.WaveImageView;

import java.io.IOException;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class UpdateDialog extends AppCompatActivity implements View.OnClickListener
        , EasyPermissions.PermissionCallbacks,
        EasyPermissions.RationaleCallbacks {

    private static final String TAG = "UpdateDialog";
    private static final String[] EXTERNAL_STORAGE =
            {Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static final int RC_STORAGE_PERM = 123;
    private String downloadUrl;
    private TextView mTvVersion;
    private TextView mTvContent;
    private DownloadView downloadView;
    private WaveImageView mWaveImageView;
    private volatile boolean mIsDownloading = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_update);
        //初始化版本
        mTvVersion = findViewById(R.id.tv_version);
        //初始化更新内容
        mTvContent = findViewById(R.id.tv_content);
        mWaveImageView = findViewById(R.id.iv_logo);

        this.downloadView = findViewById(R.id.btn_download);
        //监听事件
        findViewById(R.id.btn_download).setOnClickListener(this);
        downloadUrl = getIntent().getStringExtra(Constants.KEY_DOWNLOAD_URL);
        updateButtonDownload(false, "下载");


    }

    /**
     * 更新水波纹进度条
     */
    public void setWaveProgress(final int progress) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                WaveImageView wave = UpdateDialog.this.mWaveImageView;
                wave.setProgress(progress);
            }
        });

    }

    /**
     * 设置标题和文本
     */
    public void setUpdateInfo(String title, String content) {
        //初始化版本
        TextView tvVersion = this.mTvVersion;
        tvVersion.setText(title);
        //初始化更新内容
        TextView tvContent = this.mTvContent;
        tvContent.setText(content);
    }

    /**
     * 更新按钮状态
     *
     * @param enable 是否切换到下载中的小按钮
     */
    public void updateButtonDownload(final boolean enable, final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DownloadView downloadView = UpdateDialog.this.downloadView;
                if (enable) {
                    downloadView.start(DownloadView.Status.START, text);
                } else {
                    downloadView.start(DownloadView.Status.PAUSE, text);
                }
            }
        });
    }


    //屏蔽掉返回按钮事件
    @Override
    public void onBackPressed() {
    }

    private boolean hasStoragePermission() {
        return EasyPermissions.hasPermissions(this, EXTERNAL_STORAGE);
    }

    @Override
    public void onClick(View v) {
        if (hasStoragePermission()) {
            startDownload();
        } else {
            EasyPermissions.requestPermissions(
                    this,
                    getString(R.string.rationale_storage),
                    RC_STORAGE_PERM,
                    Manifest.permission.CAMERA);
        }
//        DLManager.getInstance(this).dlCancel(downloadUrl);
//        DLManager.getInstance(this)
//                .dlStart(downloadUrl, new SimpleDListener() {
//                    private int mFileLength;
//
//                    @Override
//                    public void onStart(String fileName, String realUrl, int fileLength) {
//                        Log.e(TAG, "fileLength: " + fileLength);
//                        mFileLength = fileLength;
//                        updateButtonDownload(true, "下载中");
//                    }
//
//                    @Override
//                    public void onProgress(int progress) {
//                        Log.e(TAG, "progress: " + progress);
//
//                        setWaveProgress((progress * 100 / mFileLength));
//                    }
//
//                    @Override
//                    public void onFinish(final File file) {
//                        setWaveProgress(100);
//                        updateButtonDownload(false, "安装");
//                        checkPermission(file);
//                    }
//
//                    @Override
//                    public void onError(int status, String error) {
//                        Log.e("UpdateDialog", "status: " + status + " ,error: " + error);
//                    }
//                });
    }

    private void startDownload() {
        if (mIsDownloading) return;
        mIsDownloading = true;
        updateButtonDownload(true, "下载中");
        new Thread() {
            @Override
            public void run() {
                try {
                    String saveDir = getExternalCacheDir() == null ?
                            getCacheDir().getAbsolutePath() :
                            getExternalCacheDir().getAbsolutePath();
                    HttpDownloadUtility
                            .downloadFile(downloadUrl, saveDir,
                                    new HttpDownloadUtility.ProgressListener() {
                                        @Override
                                        public void onStart() {

                                        }

                                        @Override
                                        public void onProgress(int current, int total) {
                                            setWaveProgress((current * 100 / total));
                                        }

                                        @Override
                                        public void onComplete(String saveFilePath) {
                                            setWaveProgress(100);
                                            updateButtonDownload(false, "安装");
                                            checkPermission(saveFilePath);
                                        }

                                        @Override
                                        public void onError(int responseCode) {
                                            Log.e(TAG, "网络请求失败");
                                            mIsDownloading = false;
                                            updateButtonDownload(false, "下载");
                                            ToastUtils.toast(UpdateDialog.this,"下载失败");
                                        }
                                    });
                } catch (IOException e) {
                    e.printStackTrace();
                    mIsDownloading = false;
                    updateButtonDownload(false, "下载");
                    ToastUtils.toast(UpdateDialog.this,"下载失败");
                }

            }
        }.start();
    }

    private void checkPermission(final String filePath) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                InstallUtils.checkInstallPermission(UpdateDialog.this,
                        new InstallUtils.InstallPermissionCallBack() {
                            @Override
                            public void onGranted() {
                                installApk(filePath);
                            }

                            @Override
                            public void onDenied() {
                                //弹出弹框提醒用户
                                showCheckInstallPermission(filePath);
                            }

                        });
            }
        });
    }

    private void showCheckInstallPermission(final String filePath) {
        AlertDialog alertDialog =
                new AlertDialog.Builder(UpdateDialog.this)
                        .setTitle("温馨提示")
                        .setMessage("必须授权才能安装APK，请设置允许安装")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                openInstallPermission(filePath);
                            }
                        }).create();
        alertDialog.show();
    }

    private void openInstallPermission(final String filePath) {
        InstallUtils.openInstallPermissionSetting(UpdateDialog.this,
                new InstallUtils.InstallPermissionCallBack() {
                    @Override
                    public void onGranted() {
                        installApk(filePath);
                    }

                    @Override
                    public void onDenied() {
                        ToastUtils.toast(UpdateDialog.this, "安装应用权限被拒绝");
                    }
                });
    }

    private void installApk(String path) {
        InstallUtils.installAPK(this, path, new InstallUtils.InstallCallBack() {
            @Override
            public void onSuccess() {
                ToastUtils.toast(UpdateDialog.this, "正在安装程序");
                android.os.Process.killProcess(android.os.Process.myPid());    //获取PID
                System.exit(0);
            }

            @Override
            public void onFail(Exception e) {
                ToastUtils.toast(UpdateDialog.this, "安装失败");
            }
        });
    }

    @AfterPermissionGranted(RC_STORAGE_PERM)
    public void storageTask() {
        if (hasStoragePermission()) {
            // Have permissions, do the thing!
            startDownload();
        } else {
            // Ask for both permissions
            EasyPermissions.requestPermissions(
                    this,
                    getString(R.string.rationale_storage),
                    RC_STORAGE_PERM,
                    Manifest.permission.CAMERA);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        Log.d(TAG, "onPermissionsGranted:" + requestCode + ":" + perms.size());
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Log.d(TAG, "onPermissionsDenied:" + requestCode + ":" + perms.size());

        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    public void onRationaleAccepted(int requestCode) {
        Log.d(TAG, "onRationaleAccepted:" + requestCode);
    }

    @Override
    public void onRationaleDenied(int requestCode) {
        Log.d(TAG, "onRationaleAccepted:" + requestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE && hasStoragePermission()) {
            ToastUtils.toast(this, "读写权限申请成功");
        }
    }

}
