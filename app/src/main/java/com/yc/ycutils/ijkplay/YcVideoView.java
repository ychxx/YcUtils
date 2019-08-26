//package com.yc.ycutils.ijkplay;
//
//import android.content.Context;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.util.AttributeSet;
//import android.view.Gravity;
//import android.view.SurfaceHolder;
//import android.view.SurfaceView;
//import android.widget.FrameLayout;
//
//import tv.danmaku.ijk.media.player.IMediaPlayer;
//import tv.danmaku.ijk.media.player.IjkMediaPlayer;
//
///**
// *
// */
//
//public class YcVideoView extends FrameLayout {
//    private Context mContext;
//    /**
//     * 由ijkplayer提供，用于播放视频，需要给他传入一个surfaceView
//     */
//    private IjkMediaPlayer mMediaPlayer = null;
//    private SurfaceView mSurfaceView;
//
//    public YcVideoView(@NonNull Context context) {
//        super(context);
//        initView(context);
//    }
//
//    public YcVideoView(@NonNull Context context, @Nullable AttributeSet attrs) {
//        super(context, attrs);
//        initView(context);
//    }
//
//    public YcVideoView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        initView(context);
//    }
//
//    public YcVideoView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//        initView(context);
//    }
//
//    private void initView(@NonNull Context context) {
//        mContext = context;
//        setFocusable(true);
//        IjkMediaPlayer.native_setLogLevel(IjkMediaPlayer.IJK_LOG_DEBUG);
//    }
//
//    private void createSurfaceView() {
//        //生成一个新的surface view
//        mSurfaceView = new SurfaceView(mContext);
//        mSurfaceView.getHolder().addCallback(new YcVideoSurfaceCallback(){
//
//            @Override
//            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
//                //surfaceview创建成功后，加载视频
////                if (init == false) {
////                    load();
////                    init = true;
////                }
//            }
//        });
//        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, Gravity.CENTER);
//        mSurfaceView.setLayoutParams(layoutParams);
//        this.addView(mSurfaceView);
//    }
//    private void createMediaPlay(){
//        if(mMediaPlayer!=null){
//            mMediaPlayer.stop();
//            mMediaPlayer.setDisplay(null);
//            mMediaPlayer.release();
//        }
//        mMediaPlayer = new IjkMediaPlayer();
//        mMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER,"mediacodec",1);//开启硬解码
//        mMediaPlayer.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
//            @Override
//            public void onPrepared(IMediaPlayer iMediaPlayer) {
//
//            }
//        });
////        mMediaPlayer.setOnInfoListener(listener);
////        mMediaPlayer.setOnSeekCompleteListener(listener);
////        mMediaPlayer.setOnBufferingUpdateListener(listener);
////        mMediaPlayer.setOnErrorListener(listener);
//    }
//}
