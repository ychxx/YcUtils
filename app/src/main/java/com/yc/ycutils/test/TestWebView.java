package com.yc.ycutils.test;

import android.os.Bundle;

import com.yc.yclibrary.base.YcAppCompatActivity;
import com.yc.ycutils.R;
import com.yc.ycutils.YcWebView;
import com.yc.ycutilslibrary.common.YcLog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okio.Utf8;

/**
 *
 */

public class TestWebView extends YcAppCompatActivity {
    @BindView(R.id.testWebView)
    YcWebView testWebView;

    @Override
    protected int getLayoutId() {
        return R.layout.test_html_2_activity;
    }

    @Override
    protected void initView(Bundle bundle) {
//        testWebView.init();
    }

    String html = "";

    @OnClick(R.id.testWebViewBtn)
    public void onViewClicked() {
        YcLog.e("触发点击");
        StringBuffer data =new StringBuffer();
        try {
            InputStream is = getAssets().open("TestHtml2.html");
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line=br.readLine();
            while(line!=null){
                data.append(line+"\n");
                line=br.readLine();
            }
            YcLog.e(data.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        testWebView.load(data.toString());
    }
}
