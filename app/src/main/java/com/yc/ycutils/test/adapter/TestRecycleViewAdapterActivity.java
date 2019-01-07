package com.yc.ycutils.test.adapter;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.yc.yclibrary.base.YcAppCompatActivity;
import com.yc.ycutils.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;

/**
 *
 */

public class TestRecycleViewAdapterActivity extends YcAppCompatActivity {
    @BindView(R.id.testRv)
    RecyclerView testRv;
    private TestRecycleViewAdapter mTestRecycleViewAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.test_recycle_view_adapter;
    }

    @Override
    protected void initView(Bundle bundle) {
        testRv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mTestRecycleViewAdapter = new TestRecycleViewAdapter(getActivity());
        //回调参数只有position
//        mTestRecycleViewAdapter.setItemOnClick(new Consumer<Integer>() {
//            @Override
//            public void accept(Integer integer) throws Exception {
//                showToast(integer + "");
//                mTestRecycleViewAdapter.getItem(integer);
//            }
//        });
        //回调参数有position和item
        mTestRecycleViewAdapter.setItemOnClick(new BiFunction<Integer, Object, Object>() {
            @Override
            public Object apply(Integer integer, Object o) throws Exception {
                showToast(integer + " : " + ((String) o));
                return "";
            }
        });
        testRv.setAdapter(mTestRecycleViewAdapter);
    }

}
