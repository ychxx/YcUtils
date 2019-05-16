package com.yc.ycutils.test;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yc.yclibrary.base.YcAppCompatActivity;
import com.yc.ycutils.R;
import com.yc.ycutilslibrary.widget.dropdown.DropDownMenuView;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 */

public class TestDropDownMenuActivity extends YcAppCompatActivity {
    @BindView(R.id.dropDownMenu)
    DropDownMenuView dropDownMenu;

    @Override
    protected int getLayoutId() {
        return R.layout.test_drop_down_menu_activity;
    }

    @Override
    protected void initView(Bundle bundle) {
        dropDownMenu.setTabAllString(Arrays.asList("tab1","tab2","tab3"));
        dropDownMenu.setContainerAllView(Arrays.asList(createView("1"),createView("2"),createView("3")));
    }
    private View createView (String msg){
        TextView textView = new TextView(getActivity());
        textView.setText(msg);
        return textView;
    }
    String url = "http://10.1.3.189:8080/app/android/UploadUserImage.invoke";
    private void testUploadImg(){

    }
    @OnClick({R.id.but1, R.id.but2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.but1:
                dropDownMenu.showMenu(1);
                break;
            case R.id.but2:
                dropDownMenu.closeMenu();
                break;
        }
    }
}
