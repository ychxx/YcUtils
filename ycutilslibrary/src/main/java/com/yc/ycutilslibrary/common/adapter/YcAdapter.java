package com.yc.ycutilslibrary.common.adapter;

import android.support.annotation.NonNull;

import com.classic.adapter.interfaces.ImageLoad;

/**
 * 项目名称: CommonAdapter
 * 包 名 称: com.classic.adapter
 * <p>
 * 类 描 述: Adapter全局配置
 * 创 建 人: 续写经典
 * 创建时间: 2016/11/27 17:54.
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public final class YcAdapter {
    public static volatile YcAdapter singleton = null;

    private ImageLoad mImageLoadImpl;

    private YcAdapter(Builder builder) {
        mImageLoadImpl = builder.mImageLoadImpl;
    }

    public ImageLoad getImageLoad() {
        return mImageLoadImpl;
    }

    public static void config(@NonNull Builder builder) {
        if (singleton == null) {
            synchronized (YcAdapter.class) {
                if (singleton == null) {
                    singleton = builder.build();
                }
            }
        }
    }

    public static final class Builder {
        private ImageLoad mImageLoadImpl;

        public Builder() {
        }

        public Builder setImageLoad(ImageLoad imageLoad) {
            mImageLoadImpl = imageLoad;
            return this;
        }

        public YcAdapter build() {
            return new YcAdapter(this);
        }
    }
}
