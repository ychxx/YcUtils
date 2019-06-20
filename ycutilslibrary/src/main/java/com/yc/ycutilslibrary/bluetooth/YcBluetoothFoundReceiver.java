package com.yc.ycutilslibrary.bluetooth;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.yc.ycutilslibrary.bean.YcBluetoothFoundBean;
import com.yc.ycutilslibrary.common.YcLog;
import com.yc.ycutilslibrary.constant.YcBluetoothFoundStateEnum;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * 蓝牙扫描接收器
 */
public class YcBluetoothFoundReceiver extends BroadcastReceiver {
    private HashSet<BluetoothDevice> mDevice = new HashSet<>();//搜索到的蓝牙设备
    private Consumer<YcBluetoothFoundBean> mObserver;//搜索蓝牙设备回调
    private boolean mIsDestroy = false;
    private List<Disposable> mDisposable = new ArrayList<>();

    /**
     * 注册蓝牙广播
     */
    public YcBluetoothFoundReceiver registerReceiver(Activity activity) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
        mIsDestroy = false;
        activity.registerReceiver(this, intentFilter);
        return this;
    }

    /**
     * 设置搜索蓝牙设备回调
     */
    public YcBluetoothFoundReceiver setFoundCall(Consumer<YcBluetoothFoundBean> observer) {
        mObserver = observer;
        return this;
    }

    /**
     * 接收搜索到的蓝牙设备结果
     */
    @SuppressLint("CheckResult")
    @Override
    public void onReceive(Context context, Intent intent) {
        if (mIsDestroy)
            return;
        String action = intent.getAction();
        if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
            onSend(new YcBluetoothFoundBean(YcBluetoothFoundStateEnum.FOUND_START));
            YcLog.e("蓝牙设备名---------------- " + "开始搜索 ...");
        } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
            onSend(new YcBluetoothFoundBean(YcBluetoothFoundStateEnum.FOUND_FINISHED, mDevice));
            YcLog.e("蓝牙设备名---------------- " + "搜索结束");
        } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            if (device != null) {
                mDevice.add(device);
                onSend(new YcBluetoothFoundBean(YcBluetoothFoundStateEnum.FOUNDING, device));
                YcLog.e("蓝牙设备名---------------- " + device.getName());
            }
        }
    }

    /**
     * 将数据发射给设置好的回调
     */
    private void onSend(YcBluetoothFoundBean foundBean) {
        if (mObserver == null)
            return;
        Disposable d = Observable.just(foundBean).observeOn(AndroidSchedulers.mainThread()).subscribe(mObserver);
        mDisposable.add(d);
    }

    /**
     * 注销掉，在Activity的onDestroy方法里调用
     */
    public void onDestroy(Activity activity) {
        mIsDestroy = true;
        activity.unregisterReceiver(this);
        if (mDisposable != null) {
            for (Disposable d : mDisposable) {
                if (d != null) {
                    d.dispose();
                }
            }
            mDisposable.clear();
        }
    }
}
