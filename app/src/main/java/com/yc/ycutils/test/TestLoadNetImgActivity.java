package com.yc.ycutils.test;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.yc.yclibrary.base.YcAppCompatActivity;
import com.yc.ycutils.R;
import com.yc.ycutilslibrary.file.YcImgUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 加载网络图片
 */

public class TestLoadNetImgActivity extends YcAppCompatActivity {
    @BindView(R.id.netImg1Iv)
    ImageView netImg1Iv;
    @BindView(R.id.netImg2Iv)
    ImageView netImg2Iv;

    @Override
    protected int getLayoutId() {
        return R.layout.test_load_net_img;
    }

    @Override
    protected void initView(Bundle bundle) {

    }

    private final String imgUrl1 = "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=727740623,2396602407&fm=26&gp=0.jpg";
    private final String imgUrl2 = "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1102186530,2413674562&fm=26&gp=0.jpg";

    private final String imgUrlFail1 = "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1103386530,2413674562&fm=26&gp=1.jpg";

    @OnClick({R.id.button1, R.id.button2, R.id.button3, R.id.button4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button1:
                YcImgUtils.loadNetImg(getActivity(), imgUrl1, netImg1Iv);
                YcImgUtils.loadNetImg(getActivity(), imgUrl2, netImg2Iv);
                break;
            case R.id.button2:
                YcImgUtils.loadNetImg(getActivity(), imgUrlFail1, netImg1Iv);
                break;
            case R.id.button3:
                break;
            case R.id.button4:
                break;
        }
    }
}
