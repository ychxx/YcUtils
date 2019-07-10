package com.yc.ycutilslibrary.common.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */

public abstract class YcRecycleViewAdapter<T> extends RecyclerView.Adapter<CViewHolder> {
    private Context mContext;
    private OnItemClick<T> mOnItemClick;
    private List<T> mData = new ArrayList<>();
    @LayoutRes
    private int mLayoutRes = 0;

    public YcRecycleViewAdapter(Context mContext, @LayoutRes int layoutRes) {
        this.mContext = mContext;
        mLayoutRes = layoutRes;
    }

    @Override
    public int getItemViewType(int position) {
        return mLayoutRes;
    }

    @Override
    public CViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final CViewHolderHelper helper = CViewHolderHelper.get(mContext, null, parent, viewType);
        return new CViewHolder(helper);
    }

    @Override
    public void onBindViewHolder(CViewHolder holder, int position, List<Object> payloads) {
        holder.itemView.setOnClickListener(v -> {
            if (mOnItemClick != null) {
                mOnItemClick.itemClick(position, getItem(position));
            }
        });
        onUpdate(position, getItem(position));
        onBindViewHolder(holder, position);
    }

    public abstract void onUpdate(int position, T item);

    /**
     * 设置单击Item事件
     */
    public void setOnItemClick(OnItemClick<T> onItemClick) {
        this.mOnItemClick = onItemClick;
    }

    public void addData(T data) {
        if (mData == null) {
            mData = new ArrayList<>();
        }
        mData.add(data);
        notifyItemInserted(mData.size());
    }

    public void addAll(List<T> listData) {
        addAll(listData, false);
    }

    public void addAll(List<T> listData, boolean isClear) {
        if (mData == null) {
            mData = new ArrayList<>();
        }
        if (isClear) {
            mData.clear();
        }
        mData.addAll(listData);
        notifyItemRangeInserted(mData.size() - listData.size(), listData.size());
    }

    public void set(@NonNull T oldItem, @NonNull T newItem) {
        set(mData.indexOf(oldItem), newItem);
    }

    public void set(int index, @NonNull T item) {
        if (index >= 0 && index < getItemCount()) {
            mData.set(index, item);
            notifyItemChanged(index);
        }
    }

    public void remove(@NonNull T item) {
        remove(mData.indexOf(item));
    }

    public void remove(int index) {
        if (index >= 0 && index < getItemCount()) {
            mData.remove(index);
            notifyItemRemoved(index);
        }
    }

    public void replaceAll(@NonNull List<T> item) {
        replaceAll(item, true);
    }

    /**
     * 替换数据
     *
     * @param item                 新数据列表
     * @param notifyDataSetChanged 是否执行：notifyItem...方法
     */
    public void replaceAll(@NonNull List<T> item, boolean notifyDataSetChanged) {
        if (notifyDataSetChanged) {
            clear();
            addAll(item);
        } else {
            mData.clear();
            mData.addAll(item);
        }
    }

    public void clear() {
        final int size = mData.size();
        mData.clear();
        notifyItemRangeRemoved(0, size);
    }

    public T getItem(int position) {
        return mData.get(position);
    }

    public interface OnItemClick<T> {
        void itemClick(int position, T item);
    }
}
