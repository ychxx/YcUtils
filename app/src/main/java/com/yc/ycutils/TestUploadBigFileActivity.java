package com.yc.ycutils;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;

import com.yc.yclibrary.base.YcAppCompatActivity;
import com.yc.ycutilslibrary.common.YcLog;
import com.yc.ycutilslibrary.net.YcSocketServer;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 *
 */
public class TestUploadBigFileActivity extends YcAppCompatActivity {

    @BindView(R.id.testUploadBigFileResultTv)
    TextView testUploadBigFileResultTv;

    @Override
    protected int getLayoutId() {
        return R.layout.test_big_file;
    }

    @Override
    protected void initView(Bundle bundle) {

    }

    private String url = "http://120.35.11.49:8030/";
//    private String url = "http://10.1.3.4:8030/";
    private void uploadBigFile() {
//        String path2 = Environment.getExternalStorageDirectory() + "/YcUtils/" + "26053_test.mp4";//17.54MB
        String path2 = Environment.getExternalStorageDirectory() + "/YcUtils/" + "41786_test.mp4";//356.34Mb
//                mPresenter.uploadVideo(mCheckImgId, new File(path2));
        File file = new File(path2);
        RequestParams requestParams = new RequestParams(url + "api/inspection/uploadVideo");
        requestParams.setMultipart(true);
        requestParams.addBodyParameter("file", file, "text/plain; charset=utf-8", file.getName());
        requestParams.addBodyParameter("imgId", "88");
        requestParams.setConnectTimeout(100000);
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                YcLog.e("上传成功" + result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                YcLog.e("上传失败" + ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                YcLog.e("上传取消");
            }

            @Override
            public void onFinished() {
                YcLog.e("上传完成");
            }
        });
    }

    @OnClick({R.id.testUploadBigFileSendBtn, R.id.testUploadBigFileSend2Btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.testUploadBigFileSendBtn:
                uploadBigFile();
                break;
            case R.id.testUploadBigFileSend2Btn:
                break;
        }
    }
}
