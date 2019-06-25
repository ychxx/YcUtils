package com.yc.ycutils.test.blt;

import android.Manifest;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.classic.adapter.BaseAdapterHelper;
import com.classic.adapter.CommonRecyclerAdapter;
import com.yc.yclibrary.base.YcAppCompatActivity;
import com.yc.ycutils.R;
import com.yc.ycutilslibrary.bean.YcBluetoothDiscoveryBean;
import com.yc.ycutilslibrary.bluetooth.YcBluetoothDiscoveryReceiver;
import com.yc.ycutilslibrary.common.YcLog;
import com.yc.ycutilslibrary.constant.YcBluetoothDiscoveryStateEnum;
import com.yc.ycutilslibrary.exception.YcBltException;
import com.yc.ycutilslibrary.permissions.YcUtilPermission;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

/**
 * 蓝牙扫描蓝牙设备
 */
public class TestBltScanDeviceActivity extends YcAppCompatActivity {
    @BindView(R.id.testBlueToothDeviceRv)
    RecyclerView mDeviceRv;
    @BindView(R.id.testBluetoothStateTv)
    TextView mStateTv;
    private CommonRecyclerAdapter<BluetoothDevice> mAdapter;
    //    private YcBluetoothDiscoveryReceiver bluetoothFoundReceiver;
    private YcBluetoothDiscoveryReceiver mBtlDiscoveryReceiver;

    @Override
    protected int getLayoutId() {
        return R.layout.test_bluetooth_activity;
    }

    @Override
    protected void initView(Bundle bundle) {
        mAdapter = new CommonRecyclerAdapter<BluetoothDevice>(getActivity(), R.layout.test_item) {
            @Override
            public void onUpdate(BaseAdapterHelper helper, BluetoothDevice item, int position) {
                helper.setText(R.id.textView, item.getName() + "\n" + item.getAddress());
            }
        };
        mAdapter.setOnItemClickListener(new CommonRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder, View view, int position) {
                mBtlDiscoveryReceiver.onCancelDiscovery();
                TestBltCommunicationActivity.newInstance(getActivity(), mAdapter.getItem(position));
//                bluetoothFoundReceiver.connBluetoothDevice(mAdapter.getItem(position));
//                YcBluetoothConnGatt ycBluetoothGatt = new YcBluetoothConnGatt();
//                ycBluetoothGatt.conn(mAdapter.getItem(position), getActivity());

            }
        });
        mDeviceRv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mDeviceRv.setAdapter(mAdapter);
        initBluetoothReceiver();
    }

    /**
     * 注册蓝牙搜索设备广播接收器
     */
    private void initBluetoothReceiver() {
        mBtlDiscoveryReceiver = new YcBluetoothDiscoveryReceiver(getActivity());
        //注册蓝牙广播
        mBtlDiscoveryReceiver.registerReceiver(getActivity())
                .setDiscoveryCall(new Consumer<YcBluetoothDiscoveryBean>() {
                    @Override
                    public void accept(YcBluetoothDiscoveryBean ycBluetoothFoundBean) {
                        switch (ycBluetoothFoundBean.getState()) {
                            case YcBluetoothDiscoveryStateEnum.DISCOVERY_START://开始扫描
                                mStateTv.setText("开始扫描蓝牙设备..");
                                break;
                            case YcBluetoothDiscoveryStateEnum.DISCOVERING://扫描到其中一个蓝牙设备
                                mStateTv.setText("扫描蓝牙设备中..");
                                mAdapter.add(ycBluetoothFoundBean.getBluetoothDevice());
                                break;
                            case YcBluetoothDiscoveryStateEnum.DISCOVERY_FINISHED://扫描完成
                                mStateTv.setText("完成扫描蓝牙设备..");
//                        mAdapter.clear();
//                        mAdapter.addAll(new ArrayList<>(ycBluetoothFoundBean.getAllBluetoothDevices()));
                                break;
                        }
                    }
                });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBtlDiscoveryReceiver.onDestroy(getActivity());
    }

    @OnClick({R.id.testBluetoothStartBtn, R.id.testBluetoothTestCancelBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.testBluetoothStartBtn:
                try {
                    mBtlDiscoveryReceiver.startDiscoveryDevices();//开始扫描蓝牙设备
                } catch (YcBltException e) {
                    showToast(e.getMessage());
                }
                break;
            case R.id.testBluetoothTestCancelBtn:
                YcUtilPermission.newInstance(getActivity())
                        .addPermissions(new String[]{Manifest.permission.BLUETOOTH,
                                Manifest.permission.BLUETOOTH_ADMIN,
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION})
                        .setSuccessCall(new YcUtilPermission.SuccessCall() {
                            @Override
                            public void onCall() {
                                YcLog.e("有权限");
                            }
                        }).start();
//                mBtlDiscoveryReceiver.onCancelDiscovery();//取消扫描蓝牙设备
                break;
        }
    }
}
