package com.yc.ycutilslibrary.common.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;

/**
 *
 */

public abstract class YcMultipleRecycleViewAdapter extends RecyclerView.Adapter<CViewHolder> {
    protected Context mContext;
    private BiFunction<Integer, Object, Object> mBiFunction;
    private Consumer<Integer> mConsumer;

    //getItemViewType先于onCreateViewHolder
    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public CViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final CViewHolderHelper helper = CViewHolderHelper.get(mContext, null, parent, viewType);
        return new CViewHolder(helper);
    }

    public void onBindViewHolder(CViewHolder holder, int position, List<Object> payloads) {
        holder.itemView.setOnClickListener(v -> onItemOnClick(position));
        onBindViewHolder(holder, position);
    }
    /**
     * 单击Item回调
     * 若是回调参数有多种重写该方法，可以参考Observable.zip(source1,source2,source3....,BiFunction) 或者自定接口
     */
    @SuppressLint("CheckResult")
    public void onItemOnClick(int position) {
        if (getItem(position) == null) {
            Observable.just(position).subscribe(mConsumer);
        } else {
            Observable.zip(Observable.just(position), Observable.just(getItem(position)), mBiFunction).subscribe();
        }
    }
    /**
     * 单击Item回调
     *
     * @param consumer 回调的参数position
     */
    public void setItemOnClick(Consumer<Integer> consumer) {
        mConsumer = consumer;
    }

    /**
     * 单击Item回调(需在子类里重写getItem(int position))
     *
     * @param biFunction 回调的参数position和getItem(position)
     */
    public void setItemOnClick(BiFunction<Integer, Object, Object> biFunction) {
        mBiFunction = biFunction;
    }


    public Object getItem(int position) {
        return null;
    }
}
