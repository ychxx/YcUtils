package com.yc.ycutils.test.html;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.style.ImageSpan;
import android.util.Log;
import android.widget.TextView;

import com.yc.yclibrary.base.YcAppCompatActivity;
import com.yc.ycutils.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */

public class TestHtmlActivity extends YcAppCompatActivity {
    @BindView(R.id.testHtmlTv)
    TextView testHtmlTv;

    @Override
    protected int getLayoutId() {
        return R.layout.test_html_activity;
    }
    String html = "环境描述：描述建筑配套相关的说明，加强用户对环境因素的喜爱，善用修辞和修<div>所属地址：福建省福州市福能总院煤矿心理医院<br></div><div><br></div><div><img src=\"http://image.wowhua.com/b0708ad4-7f56-9435-dca6-bfa79444e7b8.jpeg\"></div><div><br></div><div><img src=\"http://image.wowhua.com/fc3d458f-01b6-eca3-6c1b-05e11ede5d6a.jpeg\"><br></div>结束了啦啦啦结束了啦啦啦结束了啦啦啦结束了啦啦啦结束了啦啦啦结束了啦啦啦结束了啦啦啦结束了啦啦啦结束了啦啦啦结束了啦啦啦结束了啦啦啦结束了啦啦啦结束了啦啦啦结束了啦啦啦结束了啦啦啦结束了啦啦啦结束了啦啦啦结束了啦啦啦结束了啦啦啦结束了啦啦啦结束了啦啦啦结束了啦啦啦结束了啦啦啦结束了啦啦啦结束了啦啦啦结束了啦啦啦结束了啦啦啦结束了啦啦啦结束了啦啦啦结束了啦啦啦结束了啦啦啦结束了啦啦啦结束了啦啦啦结束了啦啦啦结束了啦啦啦结束了啦啦啦";
    @Override
    protected void initView(Bundle bundle) {
        testHtmlTv.setText(Html.fromHtml(html, new MImageGetter(testHtmlTv, getApplicationContext()), null));
//        final Handler handler = new Handler() {
//            public void handleMessage(Message msg) {
//                int what = msg.what;
//                if (what == 200) {
//                    MessageSpan ms = (MessageSpan) msg.obj;
//                    Object[] spans = (Object[]) ms.getObj();
//                    final ArrayList<String> list = new ArrayList<>();
//                    for (Object span : spans) {
//                        if (span instanceof ImageSpan) {
//                            Log.i("picUrl==", ((ImageSpan) span).getSource());
//                            list.add(((ImageSpan) span).getSource());
//                        }
//                    }
//                }
//            }
//        };
//        testHtmlTv.setMovementMethod(LinkMovementMethodExt.getInstance(handler, ImageSpan.class));
    }
}
