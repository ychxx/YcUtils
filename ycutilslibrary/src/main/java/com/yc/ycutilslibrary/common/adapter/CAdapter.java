package com.yc.ycutilslibrary.common.adapter;

import android.support.annotation.NonNull;

import com.classic.adapter.interfaces.ImageLoad;

/**
 * 复制于CommonAdapter里的adapter
 * 包 名 称: com.classic.adapter
 * <p>
 * 类 描 述: Adapter全局配置
 * 创 建 人: 续写经典
 * 创建时间: 2016/11/27 17:54.
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public final class CAdapter {
    public static volatile CAdapter singleton = null;

    private ImageLoad mImageLoadImpl;

    private CAdapter(Builder builder) {
        mImageLoadImpl = builder.mImageLoadImpl;
    }

    public ImageLoad getImageLoad() {
        return mImageLoadImpl;
    }

    public static void config(@NonNull Builder builder) {
        if (singleton == null) {
            synchronized (CAdapter.class) {
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

        public CAdapter build() {
            return new CAdapter(this);
        }
    }
}
