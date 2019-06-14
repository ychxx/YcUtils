package com.yc.ycutilslibrary.action;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.yc.yclibrary.base.YcAppCompatActivity;
import com.yc.ycutilslibrary.R;
import com.yc.ycutilslibrary.R2;
import com.yc.ycutilslibrary.file.YcImgUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 *
 */

public class YcCropActivity extends YcAppCompatActivity {
    @BindView(R2.id.ycCropImageView)
    CropImageView mCropImageView;
    private static final String IMG_PATH_KEY = "img_path";
    private static final String IMG_PATH_SAVE_KEY = "img_path_save_key";
    private static final String REQUEST_CODE_KEY = "request_code_key";
    private String mImgPath;
    private String mImgPathSave;
    private int mRequestCode;
    private ProgressDialog mLoadDialog;

    public static void newInstance(Fragment fragment, String imgPath, String imgPathSave, int requestCode) {
        Intent intent = new Intent(fragment.getActivity(), YcCropActivity.class);
        intent.putExtra(IMG_PATH_KEY, imgPath);
        intent.putExtra(IMG_PATH_SAVE_KEY, imgPathSave);
        intent.putExtra(REQUEST_CODE_KEY, requestCode);
        fragment.startActivityForResult(intent, requestCode);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.yc_crop_activity;
    }

    @Override
    protected void initView(Bundle bundle) {
        mImgPath = getIntent().getStringExtra(IMG_PATH_KEY);
        mImgPathSave = getIntent().getStringExtra(IMG_PATH_SAVE_KEY);
        mRequestCode = getIntent().getIntExtra(REQUEST_CODE_KEY, 0);
        mLoadDialog = new ProgressDialog(getActivity());
        mLoadDialog.setCancelable(false);
        mLoadDialog.setCanceledOnTouchOutside(false);
//        mCropImageView.setImageBitmap(YcTransform.imgIdResToBitmap(getActivity(), R.drawable.img_loading));
//        mCropImageView.setImageBitmap(YcTransform.imgPathToBitmap(mImgPath));
//        YcImgUtils.loadLocalImg(getActivity(), mImgPath,mCropImageView.getImageView());

//        mCropImageView.setImageBitmap(YcImgUtils.decodeSampledBitmapFromFilePath(mImgPath,400,400));
    }

    @Override
    protected void onStart() {
        super.onStart();
        Uri cropImageUri = Uri.fromFile(new File(mImgPath));
        if (CropImage.isReadExternalStoragePermissionsRequired(this, cropImageUri)) {
            // request permissions and handle the result in onRequestPermissionsResult()
            showToast("没有权限");
//            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE);
        } else {
            // no permissions required or already grunted, can start crop image activity
            mCropImageView.setImageUriAsync(cropImageUri);
        }
        mCropImageView.setOnCropImageCompleteListener(new CropImageView.OnCropImageCompleteListener() {
            @SuppressLint("CheckResult")
            @Override
            public void onCropImageComplete(CropImageView view, CropImageView.CropResult result) {
                Observable.just(view.getCroppedImage())
                        .observeOn(AndroidSchedulers.mainThread())
                        .flatMap(new Function<Bitmap, ObservableSource<Bitmap>>() {
                            @Override
                            public ObservableSource<Bitmap> apply(Bitmap bitmap) throws Exception {
                                mLoadDialog.setMessage("正在生产裁剪图片...");
                                mLoadDialog.show();
                                return Observable.just(bitmap);
                            }
                        }).observeOn(Schedulers.newThread())
                        .flatMap(new Function<Bitmap, ObservableSource<Boolean>>() {
                            @Override
                            public ObservableSource<Boolean> apply(Bitmap bitmap) throws Exception {
                                return Observable.just(YcImgUtils.saveImg(bitmap, mImgPathSave));
                            }
                        }).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(aBoolean -> {
                            mLoadDialog.dismiss();
                            if (aBoolean) {
                                showToast("保存截图成功!");
                                setResult(Activity.RESULT_OK);
                                finish();
                            } else {
                                showToast("保存截图失败!");
                            }
                        });
            }
        });
        mCropImageView.setOnSetImageUriCompleteListener(new CropImageView.OnSetImageUriCompleteListener() {
            @Override
            public void onSetImageUriComplete(CropImageView view, Uri uri, Exception error) {
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (mLoadDialog != null) {
            mLoadDialog.dismiss();
        }
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mCropImageView.setOnSetImageUriCompleteListener(null);
        mCropImageView.setOnCropImageCompleteListener(null);
    }

    @OnClick({R2.id.ycCropBreakIv, R2.id.ycCropOkIv})
    void onClick(View v) {
        int i = v.getId();
        if (i == R.id.ycCropBreakIv) {
            finish();
        } else if (i == R.id.ycCropOkIv) {
            mCropImageView.saveCroppedImageAsync(Uri.fromFile(new File(mImgPathSave)));
            mCropImageView.getCroppedImageAsync();
        }
    }
}
