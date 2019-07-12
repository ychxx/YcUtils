package com.yc.ycutilslibrary.camera;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.ImageFormat;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.view.SurfaceHolder;

import java.util.List;

/**
 * 自定义照相机帮助类
 */
public class YcCameraHelper {
    /**
     * 设置SurfaceHolder配置
     */
    public static void setSurfaceHolderConfigure(SurfaceHolder surfaceHolder, SurfaceHolder.Callback callback) {
        surfaceHolder.setKeepScreenOn(true);//屏幕常亮
        surfaceHolder.setFormat(PixelFormat.TRANSPARENT);//translucent半透明 transparent透明
        surfaceHolder.addCallback(callback);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public static void setCameraOfTakePhoto() {

    }

    public static void setCameraOfVideotape(Camera camera, Context context) {
        Camera.Parameters parameters = camera.getParameters();
//        setPictureSize(parameters);

        parameters.setPictureFormat(ImageFormat.JPEG); //设置图片格式
        parameters.setJpegQuality(100); //设置图片质量
        //TODO 先用着
        parameters.setPictureSize(640, 480);
        parameters.setPreviewSize(640, 480);
        //自动聚焦模式
        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        parameters.setWhiteBalance(Camera.Parameters.WHITE_BALANCE_AUTO);
        parameters.setSceneMode(Camera.Parameters.SCENE_MODE_AUTO);
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        List<String> supportedFocusModes = parameters.getSupportedFocusModes();
        if (supportedFocusModes != null && !supportedFocusModes.isEmpty()) {
            for (String mode : supportedFocusModes) {
                if (mode.contains("continuous-video")) {
                    parameters.setFocusMode("continuous-video");
                }
            }
        }
        if (parameters.getSupportedSceneModes().contains(Camera.Parameters.SCENE_MODE_HDR)) {//开启HDR
            parameters.setSceneMode(Camera.Parameters.SCENE_MODE_HDR);
        }
        if (parameters.isVideoStabilizationSupported()) { //开启防抖动
            parameters.setVideoStabilization(true);
        }
        if (getOrientation(context) != Configuration.ORIENTATION_LANDSCAPE) {
            camera.getParameters().set("orientation", "portrait");
            camera.setDisplayOrientation(90);
            parameters.setRotation(90);
        } else {
            camera.getParameters().set("orientation", "landscape");
            camera.setDisplayOrientation(0);
            parameters.setRotation(0);
        }
        camera.setParameters(parameters);
    }

    /**
     * 获取屏幕方向
     *
     * @return 横屏2、竖屏1
     */
    public static int getOrientation(Context context) {
        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return Configuration.ORIENTATION_LANDSCAPE;
        } else {
            return Configuration.ORIENTATION_PORTRAIT;
        }
    }

    public static int findCamera(boolean openBackCamera) {
        int cameraCount;
        try {
            Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
            cameraCount = Camera.getNumberOfCameras();
            for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
                Camera.getCameraInfo(camIdx, cameraInfo);
                int facing = openBackCamera ? 0 : 1;
                if (cameraInfo.facing == facing) {
                    return camIdx;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
}
