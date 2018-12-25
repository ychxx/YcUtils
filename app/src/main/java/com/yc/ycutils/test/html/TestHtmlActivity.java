package com.yc.ycutils.test.html;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yc.yclibrary.base.YcAppCompatActivity;
import com.yc.yclibrary.exception.ApiException;
import com.yc.yclibrary.net.BaseObserver;
import com.yc.yclibrary.net.NetTransformer;
import com.yc.yclibrary.net.RetrofitUtils;
import com.yc.ycutils.ApiService;
import com.yc.ycutils.R;
import com.yc.ycutilslibrary.html.YcHtmlImageGetter;

import butterknife.BindView;

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

    @Override
    protected void initView(Bundle bundle) {

        RetrofitUtils.Instance
                .getApiService(ApiService.class)
                .getHtml()
                .compose(NetTransformer.compose())
                .subscribe(new BaseObserver<String>() {
                    @Override
                    public void onSuccess(String s) {
                        Gson gson = new Gson();
                        GetHtmlJson getHtmlJson = gson.fromJson(s, GetHtmlJson.class);
                        testHtmlTv.setText(Html.fromHtml(getHtmlJson.getData().getText(), new YcHtmlImageGetter(testHtmlTv, getActivity()), null));
                    }

                    @Override
                    public void onFail(ApiException e) {

                    }
                });
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
