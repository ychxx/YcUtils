package com.yc.ycutilslibrary.camera;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.yc.ycutilslibrary.R;
import com.yc.ycutilslibrary.constant.YcCameraStateEnum;

/**
 * 用于自定义照相机
 */
public class YcCameraSfv extends SurfaceView {
    private @YcCameraStateEnum
    int mCameraState;//照相机状态
    private SurfaceHolder mSurfaceHolder;
    private boolean mOpenBackCamera = true;//是否开启后置摄像头

    public YcCameraSfv(Context context) {
        super(context);
        init(context);
    }

    public YcCameraSfv(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public YcCameraSfv(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.yc_camera_sfv, defStyleAttr, 0);
        mOpenBackCamera = a.getBoolean(R.styleable.yc_camera_sfv_is_back_Camera, true);//是否开启后置摄像头，默认开启
        init(context);
    }

    private void init(Context context) {
        mCameraState = YcCameraStateEnum.START;
        mSurfaceHolder = getHolder();
//        if()
    }
}
