package com.yc.ycutilslibrary.actionnew;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import com.yc.ycutilslibrary.action.YcCropActivity;
import com.yc.ycutilslibrary.common.YcLog;
import com.yc.ycutilslibrary.file.YcFileUtils;
import com.yc.ycutilslibrary.phone.YcUtilVersion;
import com.yc.ycutilslibrary.toactivity.YcForResult;
import com.yc.ycutilslibrary.toactivity.YcForResultBean;
import com.yc.ycutilslibrary.toactivity.YcForResultFragment;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;

/**
 *
 */

public class YcAction {
    private YcForResultFragment mYcForResultFragment = null;
    public final static int CODE_RESULT_FAIL = -233;
    public final static int CODE_RESULT_SUCCESS = 233;
    private Activity mActivity;
    public YcAction(Activity activity) {
        mActivity = activity;
        mYcForResultFragment = YcForResultFragment.getFragment(activity);
    }

    public Observable<YcForResultBean> startCamera(String imgSavePath) {
        File saveImgFile = YcFileUtils.createFile(imgSavePath);
        Uri outPutUri;
        if (Build.VERSION.SDK_INT >= 23) {
            outPutUri = FileProvider.getUriForFile(mYcForResultFragment.getContext(), YcUtilVersion.getPackageName(mYcForResultFragment.getContext()) + ".fileprovider", saveImgFile);
        } else {
            outPutUri = Uri.fromFile(saveImgFile);
        }
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);//设置Action为拍照
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outPutUri);//将拍取的照片保存到指定URI

        List result = mYcForResultFragment.getActivity().getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_ALL);
        if (result.isEmpty()) {
            YcLog.e("没有照相机");
        } else {
            return mYcForResultFragment.put(intent);
        }
        return resultFail();
    }
    public Observable<YcForResultBean> startCrop(String imgPath,String imgSavePath) {
        YcForResult ycForResult = new YcForResult(mActivity);
        Intent intent = new Intent(mActivity, YcCropActivity.class);
        intent.putExtra("img_path", imgPath);
        intent.putExtra( "img_path_save_key", imgSavePath);
        return ycForResult.start(intent);
//        YcCropActivity.newInstance(mYcForResultFragment,imgPath,imgPathSave,request);
    }
    /**
     * 失败结果
     */
    private Observable<YcForResultBean> resultFail() {
        return Observable.just(new YcForResultBean(CODE_RESULT_FAIL, null,false));
    }
}
