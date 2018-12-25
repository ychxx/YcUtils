package com.yc.ycutils.test.html;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.text.Html;
import android.widget.TextView;

import com.yc.ycutilslibrary.file.YcImgUtils;

/**
 *
 */

public class MImageGetter implements Html.ImageGetter {
    private Context mContext;
    private TextView textView;

    public MImageGetter(TextView text, Context context) {
        this.mContext = context;
        this.textView = text;
    }

    public Drawable getDrawable(String source) {
        final LevelListDrawable drawable = new LevelListDrawable();
        YcImgUtils.loadNetImg(mContext, source, new YcImgUtils.ImgLoadCall() {
            @Override
            public void call(Bitmap resource) {
                drawable.addLevel(1, 1, new BitmapDrawable(resource));
                drawable.setBounds(0, 0, resource.getWidth(), resource.getHeight());
                drawable.setLevel(1);
                textView.invalidate();
                textView.setText(textView.getText());
            }
        });
        return drawable;
    }

}
