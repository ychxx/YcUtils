package com.yc.ycutilslibrary.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

import com.yc.ycutilslibrary.bean.YcBluetoothFoundBean;

import io.reactivex.functions.Consumer;

/**
 * 蓝牙帮助类
 */
public class YcBluetoothHelper {
    private BluetoothAdapter mBtAdapter;
    private YcBluetoothFoundReceiver mFoundReceiver;

    public YcBluetoothHelper receiverFoundReceiver(Activity activity) {
        if (mFoundReceiver == null) {
            mFoundReceiver = new YcBluetoothFoundReceiver();
        }
        mFoundReceiver.registerReceiver(activity);
        return this;
    }

    /**
     * 设置搜索蓝牙设备回调
     */
    public YcBluetoothHelper setFoundCall(Consumer<YcBluetoothFoundBean> observer) {
        if (mFoundReceiver == null) {
            mFoundReceiver = new YcBluetoothFoundReceiver();
        }
        mFoundReceiver.setFoundCall(observer);
        return this;
    }

    /**
     * 开始搜索蓝牙设备
     */
    public void startFoundDevices() {
        if (mBtAdapter == null) {
            mBtAdapter = BluetoothAdapter.getDefaultAdapter();
        }
        if (mBtAdapter.isDiscovering()) {
            mBtAdapter.cancelDiscovery();
        }
        mBtAdapter.startDiscovery();
    }

    public void connBluetoothDevice(BluetoothDevice bluetoothDevice) {
        YcBluetoothThread thread = new YcBluetoothThread();
        thread.startConn(bluetoothDevice);
    }

    public void onDestroy(Activity activity) {
        // 取消蓝牙设备搜索
        if (mBtAdapter != null) {
            mBtAdapter.cancelDiscovery();
        }
        if (mFoundReceiver != null) {
            mFoundReceiver.onDestroy(activity);
        }
    }
}
