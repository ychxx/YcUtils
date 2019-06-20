package com.yc.ycutils;

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
import com.yc.ycutilslibrary.bean.YcBluetoothFoundBean;
import com.yc.ycutilslibrary.bluetooth.YcBluetoothConnGatt;
import com.yc.ycutilslibrary.bluetooth.YcBluetoothHelper;
import com.yc.ycutilslibrary.common.YcLog;
import com.yc.ycutilslibrary.constant.YcBluetoothFoundStateEnum;
import com.yc.ycutilslibrary.permissions.YcUtilPermission;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

/**
 *
 */
public class TestBluetoothActivity extends YcAppCompatActivity {
    @BindView(R.id.testBlueToothDeviceRv)
    RecyclerView mDeviceRv;
    @BindView(R.id.testBluetoothStateTv)
    TextView mStateTv;
    private CommonRecyclerAdapter<BluetoothDevice> mAdapter;
    private YcBluetoothHelper bluetoothFoundReceiver;

    @Override
    protected int getLayoutId() {
        return R.layout.test_bluetooth_activity;
    }

    @Override
    protected void initView(Bundle bundle) {
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
        mAdapter = new CommonRecyclerAdapter<BluetoothDevice>(getActivity(), R.layout.test_item) {
            @Override
            public void onUpdate(BaseAdapterHelper helper, BluetoothDevice item, int position) {
                helper.setText(R.id.textView, item.getName() + "\n" + item.getAddress());
            }
        };
        mAdapter.setOnItemClickListener(new CommonRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder, View view, int position) {
                bluetoothFoundReceiver.connBluetoothDevice(mAdapter.getItem(position));
//                YcBluetoothConnGatt ycBluetoothGatt = new YcBluetoothConnGatt();
//                ycBluetoothGatt.conn(mAdapter.getItem(position), getActivity());

            }
        });
        mDeviceRv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mDeviceRv.setAdapter(mAdapter);
        bluetoothFoundReceiver = new YcBluetoothHelper();
        registerBluetoothReceiver();
    }

    /**
     * 注册蓝牙搜索设备广播接收器
     */
    private void registerBluetoothReceiver() {
        //注册蓝牙广播
        bluetoothFoundReceiver.receiverFoundReceiver(getActivity())
                .setFoundCall(new Consumer<YcBluetoothFoundBean>() {
                    @Override
                    public void accept(YcBluetoothFoundBean ycBluetoothFoundBean) {
                        switch (ycBluetoothFoundBean.getState()) {
                            case YcBluetoothFoundStateEnum.FOUND_START://开始扫描
                                break;
                            case YcBluetoothFoundStateEnum.FOUNDING://扫描到其中一个蓝牙设备
                                mAdapter.add(ycBluetoothFoundBean.getBluetoothDevice());
                                break;
                            case YcBluetoothFoundStateEnum.FOUND_FINISHED://扫描完成
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
        bluetoothFoundReceiver.onDestroy(getActivity());
    }

    @OnClick({R.id.testBluetoothStartBtn, R.id.testBluetoothTestCrcBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.testBluetoothStartBtn:
                bluetoothFoundReceiver.startFoundDevices();
                break;
            case R.id.testBluetoothTestCrcBtn:
                new YcBluetoothConnGatt().testCrc();
                break;
        }


    }
}
