package com.yc.ycutilslibrary.action;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

/**
 * 跳转到其他应用
 */

public class YcActionUtils {
    /**
     * 打开文件管理器
     *
     * @param fragment 页面
     * @param fileType 限制打开的文件类型
     * @param request  请求码
     */
    public static void openFileManager(Fragment fragment, String fileType, int request) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType(fileType);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        fragment.startActivityForResult(Intent.createChooser(intent, "打开文件的方式"), request);
    }

    /**
     * 拨打电话
     *
     * @param fragment 页面
     * @param telNum   电话号码
     */
    public static void openTelephone(Fragment fragment, String telNum, int request) {
        Uri uri = Uri.parse("tel:" + telNum.trim());//去掉空格
        Intent intent = new Intent(Intent.ACTION_DIAL, uri);
        fragment.startActivityForResult(intent, request);
    }

    /**
     * 打开网页
     *
     * @param fragment 页面
     * @param webUrl   网页地址
     */
    public static void openWeb(Fragment fragment, String webUrl, int request) {
        Uri uri = Uri.parse(webUrl);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        fragment.startActivityForResult(Intent.createChooser(intent, "打开网页的方式"), request);
    }

    /**
     * 跳转到系统设置页面
     *
     * @param fragment 页面
     */
    public static void openSetting(Fragment fragment, int request) {
        Intent intent = new Intent(Settings.ACTION_SETTINGS);
        fragment.startActivityForResult(intent, request);
    }

    /**
     * 跳转到App信息页面
     *
     * @param fragment 页面
     */
    public static void openAppInfo(Fragment fragment, int request) {
        Uri packageURI = Uri.parse("package:" + fragment.getActivity().getPackageName());
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
        fragment.startActivityForResult(intent, request);
    }

    public static void openSms(Fragment activity, String savePath, int request) {

    }

    public static void openCamera(Fragment activity, String savePath, int request) {

    }

    public static void openCrop(Fragment activity, String savePath, int request) {

    }
}
