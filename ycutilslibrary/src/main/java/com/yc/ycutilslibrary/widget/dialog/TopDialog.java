package com.yc.ycutilslibrary.widget.dialog;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;

import com.yc.ycutilslibrary.R;

import java.util.Random;

/**
 *
 */
public class TopDialog {
    private Activity mActivity;
    private View rootView;
    private boolean isShowing;
    private static final int VIEW_HEIGHT = 64;//px

    public TopDialog(Activity activity) {
        mActivity = activity;
        rootView = LayoutInflater.from(activity).inflate(R.layout.yc_dialog_test, null);
    }

    public void show() {
        if (mActivity == null) {
            return;
        }
        if (isShowing) {
            return;
        }
        ViewGroup decorView = (ViewGroup) mActivity.getWindow().getDecorView();

        decorView.addView(rootView);
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

//        RotateAnimation rotateAnimation = new RotateAnimation(0, 720f, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
//        rotateAnimation.setDuration(2000);
//        contentTextView.startAnimation(rotateAnimation);
        isShowing = true;
    }

    public void dismiss() {
        if (!isShowing) {
            return;
        }
        isShowing = false;
        if (rootView.getParent() == null) {
            return;
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        parent.removeView(rootView);

    }

    public int getRandomColor() {
        Random random = new Random();
        return Color.argb(random.nextInt(200), random.nextInt(240), random.nextInt(240), random.nextInt(240));
    }

    public boolean isShowing() {
        return isShowing;
    }
}
