package com.yc.ycutilslibrary.common.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.classic.adapter.CommonRecyclerAdapter;

import java.util.List;

/**
 *
 */
public abstract class YcBaseRecycleViewAdapter<T> extends CommonRecyclerAdapter<T> {
    public YcBaseRecycleViewAdapter(@NonNull Context context, int layoutResId) {
        super(context, layoutResId);
    }

    public YcBaseRecycleViewAdapter(@NonNull Context context, int layoutResId, List<T> data) {
        super(context, layoutResId, data);
    }
}
