package com.xy.xydoctor.utils;

import com.azhon.appupdate.config.UpdateConfiguration;
import com.azhon.appupdate.listener.OnDownloadListener;
import com.azhon.appupdate.manager.DownloadManager;
import com.blankj.utilcode.util.Utils;
import com.xy.xydoctor.R;

/**
 * 描述: 更新工具类
 * 作者: LYD
 * 创建日期: 2019/10/11 16:23
 */
public class UpdateUtils {
    private UpdateUtils() {
    }

    /**
     * 下载并安装Apk
     *
     * @param updateUrl
     * @param onDownloadListener
     */
    public static void downloadAndInstall(String updateUrl, OnDownloadListener onDownloadListener) {
        UpdateConfiguration configuration = new UpdateConfiguration();
        configuration.setShowBgdToast(false);
        configuration.setOnDownloadListener(onDownloadListener);
        //下载配置
        DownloadManager manager = DownloadManager.getInstance(Utils.getApp());
        manager.setApkName("xy_doctor.apk")
                .setApkUrl(updateUrl)
                .setConfiguration(configuration)
                .setSmallIcon(R.mipmap.ic_launcher)
                .download();
    }
}
