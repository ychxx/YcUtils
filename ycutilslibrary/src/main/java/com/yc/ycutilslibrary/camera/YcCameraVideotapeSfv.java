package com.yc.ycutilslibrary.camera;

import android.content.Context;
import android.graphics.ImageFormat;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.os.Environment;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.yc.ycutilslibrary.R;
import com.yc.ycutilslibrary.camera.YcCameraHelper;
import com.yc.ycutilslibrary.common.YcRandom;
import com.yc.ycutilslibrary.constant.CameraStateEnum;
import com.yc.ycutilslibrary.file.YcFileUtils;

import org.xutils.common.util.LogUtil;

/**
 * 用于手机录像
 */
public class YcCameraVideotapeSfv extends SurfaceView {
    private SurfaceHolder mSurfaceHolder;
    private Camera mCamera = null;
    @CameraStateEnum
    private int mCameraState;
    /**
     * 录制视频参数
     */
    private MediaRecorder mRecorder;

    public YcCameraVideotapeSfv(Context context) {
        this(context, null);
    }

    public YcCameraVideotapeSfv(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mSurfaceHolder = getHolder();
        YcCameraHelper.setSurfaceHolderConfigure(mSurfaceHolder, new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                mSurfaceHolder = holder;
                try {
                    LogUtil.d("surfaceCreated...");
//                    stopPreview();
                    startPreview();
                } catch (Exception e) {
                    Toast.makeText(getContext(), "调用照相机失败", Toast.LENGTH_SHORT).show();
                    LogUtil.e(e.getMessage());
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                LogUtil.d("surfaceChanged...");
                mSurfaceHolder = holder;
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                LogUtil.d("surfaceDestroyed...");
//                stopRecord();
//                unlockCamera();
            }
        });
    }

    /**
     * 开始预览
     */
    private void startPreview() {
        try {
            if (mCamera == null) {
                openCamera();
            }
            if (mRecorder == null) {
                mRecorder = new MediaRecorder();
            }
            YcCameraHelper.setCameraOfVideotape(mCamera, getContext());
            //设置预显示
            mCamera.setPreviewDisplay(mSurfaceHolder);
            //开启预览
            mCamera.startPreview();
        } catch (Exception e) {
            Log.e(">>>>>", "相机转换失败!" + e.getMessage());
        }
    }

    /**
     * 根据当前照相机状态(前置或后置)，打开对应相机
     */
    private void openCamera() {
        int mCameraId = YcCameraHelper.findCamera(true);
        if (mCameraId == -1) {
            mCameraId = 0;
        }
        try {
            mCamera = Camera.open(mCameraId);
        } catch (Exception ee) {
            mCamera = null;
        }
        if (mCamera == null) {
            Toast.makeText(getContext(), "打开摄像头失败", Toast.LENGTH_SHORT).show();
        }
    }

    public void startRecord() {
        try {
//            isRecording = true;
//            camera.unlock();
            mRecorder.reset();
            mRecorder.setCamera(mCamera);
            mRecorder.setOrientationHint(90);
            mRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
            mRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            //音频编码格式对应应为AAC
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            //视频编码格式对应应为H264
            mRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
            //相机参数配置类
            //CameraParams.PREVIEW_WIDTH, CameraParams.PREVIEW_HEIGHT
            mRecorder.setVideoSize(640, 480);
            mRecorder.setVideoEncodingBitRate(2 * 1024 * 1024);
            mRecorder.setVideoFrameRate(30);
            mRecorder.setPreviewDisplay(mSurfaceHolder.getSurface());
//            if(maxRecordTime!=0) {
            mRecorder.setMaxDuration(8000);
//            }
//            if(mOnErrorListener!=null) {
//                mRecorder.setOnErrorListener(mOnErrorListener);
//            }
            mRecorder.setOnErrorListener(new MediaRecorder.OnErrorListener() {
                @Override
                public void onError(MediaRecorder mr, int what, int extra) {

                }
            });
            mRecorder.setOnInfoListener(new MediaRecorder.OnInfoListener() {
                @Override
                public void onInfo(MediaRecorder mr, int what, int extra) {

                }
            });
            /**
             * 设置输出地址
             */
            String filePath = Environment.getExternalStorageDirectory() + "/YcUtils/" + YcRandom.getString(5) + "_test.mp4";
            YcFileUtils.createFile(filePath);
            if (!TextUtils.isEmpty(filePath)) {
                mRecorder.setOutputFile(filePath);
                mRecorder.setOrientationHint(90);
                mCamera.unlock();//开始录像时，必须解锁摄像头
                mRecorder.prepare();
                mRecorder.start();
//                if(onRecordTimeListener!=null){
//                    onRecordTimeListener.onStart(DateUtil.getDateFormateString());
//                }
            } else {
//                isRecording = false;
                Toast.makeText(this.getContext(), "路径为空", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            /**
             * 当用户拒绝录音权限会执行这里
             */
            Toast.makeText(this.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
//            stopRecord();
        }
    }

    public void stopRecord() {
        //当结束录制之后，就将当前的资源都释放
//        isRecording = false;
        try {
            if (mRecorder != null) {
                mRecorder.setOnErrorListener(null);
                mRecorder.setOnInfoListener(null);
                mRecorder.setPreviewDisplay(null);
                try {
                    mRecorder.stop();
                } catch (Exception e) {

                }
//                if(onRecordTimeListener!=null){
//                    onRecordTimeListener.onStop(DateUtil.getDateFormateString());
//                }
                mRecorder.reset();
                mCamera.lock();
            }
        } catch (Throwable e) {
//            if(onRecordTimeListener!=null){
//                onRecordTimeListener.onStop(DateUtil.getDateFormateString());
//            }
        }
    }
}
