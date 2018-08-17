package com.yc.ycutilslibrary.action;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.content.FileProvider;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImageView;
import com.yc.ycutilslibrary.R;
import com.yc.ycutilslibrary.common.YcLog;
import com.yc.ycutilslibrary.common.YcTransform;
import com.yc.ycutilslibrary.file.YcFileUtils;
import com.yc.ycutilslibrary.phone.YcUtilPhoneInfo;
import com.yc.ycutilslibrary.phone.YcUtilVersion;

import java.io.File;
import java.util.List;

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

    public static void openSms(Fragment fragment, String savePath, int request) {

    }

    /**
     * 启动系统照相机
     *
     * @param fragment
     * @param saveImgFile 照片保存
     * @param request     请求码
     */
    public static void openCamera(Fragment fragment, File saveImgFile, int request) {
        Uri outPutUri;
        if (Build.VERSION.SDK_INT >= 23) {
            outPutUri = FileProvider.getUriForFile(fragment.getContext(), YcUtilVersion.getPackageName(fragment.getContext()) + ".fileprovider", saveImgFile);
        } else {
            outPutUri = Uri.fromFile(saveImgFile);
        }
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);//设置Action为拍照
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outPutUri);//将拍取的照片保存到指定URI

        List result = fragment.getActivity().getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_ALL);
        if (result.isEmpty()) {
            Toast.makeText(fragment.getActivity(), "没有照相机", Toast.LENGTH_SHORT).show();
            YcLog.e("没有照相机");
        } else {
            fragment.startActivityForResult(intent, request);
        }
    }

    public static void openCrop(Fragment fragment, String imgPath, int request) {
        YcCropActivity.newInstance(fragment,imgPath,request);
//        Activity activity = fragment.getActivity();
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
//        LinearLayout linearLayout = new LinearLayout(activity);
//        linearLayout.setOrientation(LinearLayout.VERTICAL);
//        RelativeLayout relativeLayout = new RelativeLayout(activity);
//        CropImageView cropImageView = new CropImageView(activity);
//        cropImageView.setImageBitmap(YcTransform.imgPathToBitmap(imgPath));
//        relativeLayout.addView(cropImageView);
//        activity.addContentView(relativeLayout, layoutParams);
    }
}
