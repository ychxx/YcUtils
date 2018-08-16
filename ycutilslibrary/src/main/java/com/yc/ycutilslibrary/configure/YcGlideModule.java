package com.yc.ycutilslibrary.configure;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.module.AppGlideModule;

/**
 * Glide的配置
 * 注：必须有@GlideModule注解
 */
@GlideModule
public class YcGlideModule extends AppGlideModule {

    //禁止解析Manifest文件,可以提升初始化速度，避免一些潜在错误
    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }

    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {

    }
}
