package com.yc.ycutilslibrary.common;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.classic.adapter.BaseAdapterHelper;
import com.yc.ycutilslibrary.toactivity.YcForResultBean;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

/**
 *
 */

public abstract class YcRecycleViewAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    protected Context mContext;
    private BiFunction<Integer, Object, Object> mBiFunction;
    private Consumer<Integer> mConsumer;

    //getItemViewType先于onCreateViewHolder
    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final BaseViewHolderHelper helper = BaseViewHolderHelper.get(mContext, null, parent, viewType);
        return new BaseViewHolder(helper);
    }

    public void onBindViewHolder(BaseViewHolder holder, int position, List<Object> payloads) {
        holder.itemView.setOnClickListener(v -> onItemOnClick(position));
        onBindViewHolder(holder, position);
    }
    /**
     * 单击Item回调
     * 若是回调参数有多种重写该方法，可以参考Observable.zip(source1,source2,source3....,BiFunction) 或者自定接口
     */
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
