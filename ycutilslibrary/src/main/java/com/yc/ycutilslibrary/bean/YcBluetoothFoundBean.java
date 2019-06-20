package com.yc.ycutilslibrary.bean;

import android.bluetooth.BluetoothDevice;

import com.yc.ycutilslibrary.constant.YcBluetoothFoundStateEnum;

import java.util.HashSet;

/**
 * 蓝牙搜索bean
 */
public class YcBluetoothFoundBean {
    private @YcBluetoothFoundStateEnum
    int mState;
    private HashSet<BluetoothDevice> mAllBluetoothDevices;
    private BluetoothDevice mBluetoothDevice;

    public YcBluetoothFoundBean(int state) {
        this.mState = state;
    }

    public YcBluetoothFoundBean(int state, HashSet<BluetoothDevice> bluetoothDevices) {
        this.mState = state;
        this.mAllBluetoothDevices = bluetoothDevices;
    }

    public YcBluetoothFoundBean(int state, BluetoothDevice bluetoothDevice) {
        this.mState = state;
        this.mBluetoothDevice = bluetoothDevice;
    }

    public @YcBluetoothFoundStateEnum int getState() {
        return mState;
    }
    public HashSet<BluetoothDevice> getAllBluetoothDevices() {
        return mAllBluetoothDevices;
    }

    public BluetoothDevice getBluetoothDevice() {
        return mBluetoothDevice;
    }
}
