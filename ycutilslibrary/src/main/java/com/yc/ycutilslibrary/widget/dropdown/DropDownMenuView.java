package com.yc.ycutilslibrary.widget.dropdown;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yc.ycutilslibrary.R;
import com.yc.ycutilslibrary.common.YcUI;
import com.yc.ycutilslibrary.widget.pathview.PathsDrawable;

import java.util.List;

/**
 * 下拉刷选框
 */

public class DropDownMenuView extends LinearLayout implements IDropDownMenu {
    private Context context;
    private LinearLayout mTabMenuLl;//tab栏
    private FrameLayout mContainerFl; //底部容器，包含下拉列表和遮罩
    private FrameLayout mPopupMenuFl; //弹出菜单父布局
    private View mMaskView; //遮罩半透明View，点击可关闭DropDownMenu
    private boolean mIsShowMenu = false;//是否显示下拉框
    private int mCurrentShowIndex = -1;//当前显示的下拉框下标
    private int mDropDownNum = -1;//下拉框的数量
    private boolean mIsHideByClickMask = true;//点击遮罩部分是否隐藏下拉框

    private int textSelectedColor = 0xff890c85; //tab选中颜色
    private int textUnselectedColor = 0xff111111;   //tab未选中颜色

    private int menuTextSize = 14;   //tab字体大小



    public DropDownMenuView(Context context) {
        super(context);
        initView(context);
    }

    public DropDownMenuView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public DropDownMenuView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        setOrientation(VERTICAL);

        //添加tab栏
        mTabMenuLl = new LinearLayout(context);
        mTabMenuLl.setGravity(HORIZONTAL);
        mTabMenuLl.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mTabMenuLl.setBackgroundColor(0xffffffff);
        addView(mTabMenuLl, 0);
        //添加分割线
        View line = new View(context);
        line.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, YcUI.pxToDp(1)));
        line.setBackgroundColor(0xffcccccc);
        addView(line, 1);
        //添加底部容器
        mContainerFl = new FrameLayout(context);
        mContainerFl.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));

        //底部容器里添加遮罩
        mMaskView = new View(getContext());
        mMaskView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT));
        mMaskView.setBackgroundColor(0x88888888);
        mMaskView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIsHideByClickMask) {
                    closeMenu();
                }
            }
        });
        mMaskView.setVisibility(GONE);
        mContainerFl.addView(mMaskView, 0);

        //底部容器里添加下拉框
        mPopupMenuFl = new FrameLayout(getContext());
        mPopupMenuFl.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mPopupMenuFl.setVisibility(GONE);
        mContainerFl.addView(mPopupMenuFl,1);
        addView(mContainerFl, 2);
    }

    /**
     * 点击遮罩部分是否隐藏下拉框
     *
     * @param isHide
     */
    public void setHideByClickMask(boolean isHide) {
        mIsHideByClickMask = isHide;
    }

//    /**
//     * 设置tab栏里视图
//     */
//    public void setTabAllView(List<View> viewList) {
//        for (int i = 0; i < viewList.size(); i++) {
//            mTabMenuLl.addView(viewList.get(i), i);
//        }
//    }

    /**
     * 设置tab栏里视图
     */
    public void setTabAllString(List<String> tabAllView) {
        for (int i = 0; i < tabAllView.size(); i++) {
            mTabMenuLl.addView(createTabView(tabAllView.get(i)), i);
            mTabMenuLl.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
            mTabMenuLl.setDividerDrawable(getResources().getDrawable(R.drawable.divider_line));
        }
    }

    private View createTabView(String content) {
        final TextView tab = new TextView(getContext());
        tab.setSingleLine();
        tab.setEllipsize(TextUtils.TruncateAt.END);
        tab.setGravity(Gravity.CENTER);
        tab.setTextSize(TypedValue.COMPLEX_UNIT_PX, menuTextSize);
        tab.setLayoutParams(new LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
        tab.setTextColor(textUnselectedColor);
        tab.setCompoundDrawablesWithIntrinsicBounds(null, null, getArrowDrawable(0), null);
        tab.setText(content);
        tab.setPadding(YcUI.pxToDp(5), YcUI.pxToDp(12), YcUI.pxToDp(5), YcUI.pxToDp(12));
        return tab;
    }

    /**
     * 设置下拉框里视图
     */
    public void setContainerAllView(List<View> viewList) {
        for (int i = 0; i < viewList.size(); i++) {
            mPopupMenuFl.addView(viewList.get(i), i);
        }
    }

    public void setContainerView(View view, int index) {
        mPopupMenuFl.addView(view, index);
    }

    @Override
    public void closeMenu() {
        if(mIsShowMenu){
            TextView tab = (TextView) mTabMenuLl.getChildAt(mCurrentShowIndex);
//            tab.setCompoundDrawablesWithIntrinsicBounds(null, null, getArrowDrawable(0), null);
            mPopupMenuFl.setVisibility(View.GONE);
            mPopupMenuFl.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.dd_menu_out));
            mMaskView.setVisibility(GONE);
            mMaskView.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.dd_mask_out));
            mIsShowMenu = false;
            mCurrentShowIndex = -1;
        }
    }

    @Override
    public void showMenu(int index) {
        if(mCurrentShowIndex==index){
            closeMenu();
        }else {
            mPopupMenuFl.setVisibility(View.VISIBLE);
            mPopupMenuFl.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.dd_menu_in));
            mMaskView.setVisibility(VISIBLE);
            mMaskView.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.dd_mask_in));
            mPopupMenuFl.getChildAt(index).setVisibility(View.VISIBLE);
            mCurrentShowIndex = index;
            mIsShowMenu = true;
        }
    }

    /**
     * 生成箭头
     * @param rotation
     * @return
     */
    private Drawable getArrowDrawable(int rotation){
        PathsDrawable arrowDrawable=new PathsDrawable();
        arrowDrawable.parserPaths("M20,12l-1.41,-1.41L13,16.17V4h-2v12.17l-5.58,-5.59L4,12l8,8 8,-8z");
        return arrowDrawable;
    }
}
