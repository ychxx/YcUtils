package com.yc.ycutils.test.blt;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.yc.yclibrary.base.YcAppCompatActivity;
import com.yc.ycutils.R;
import com.yc.ycutilslibrary.bluetooth.YcBluetoothConnGatt;
import com.yc.ycutilslibrary.common.Crc16;
import com.yc.ycutilslibrary.common.YcLog;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.BaseStream;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * 蓝牙通信
 */
public class TestBltCommunicationActivity extends YcAppCompatActivity {
    @BindView(R.id.textView1)
    TextView mState;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.textView3)
    TextView textView3;
    @BindView(R.id.textView4)
    TextView textView4;
    @BindView(R.id.textView5)
    TextView textView5;
    @BindView(R.id.textView6)
    TextView textView6;
    @BindView(R.id.textView7)
    TextView textView7;
    @BindView(R.id.textView8)
    TextView textView8;
    @BindView(R.id.textView9)
    TextView textView9;
    private BluetoothDevice mBltDevice;
    private List<Integer> mBltInputData = new ArrayList<>();
    private List<Integer> mBltInputDataTemp = new ArrayList<>();
    private static final String KEY_BLE_DEVICE = "KEY_BLE_DEVICE";

    public static void newInstance(Activity activity, BluetoothDevice bleDevice) {
        Intent intent = new Intent(activity, TestBltCommunicationActivity.class);
        intent.putExtra(KEY_BLE_DEVICE, bleDevice);
        activity.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.test_blt_communication_activity;
    }

    private int BLT_START_MAKE = 0xFA;

    @Override
    protected void initView(Bundle bundle) {
        mBltDevice = getIntent().getParcelableExtra(KEY_BLE_DEVICE);
        YcBluetoothConnGatt bluetoothGatt = new YcBluetoothConnGatt();
        bluetoothGatt.setInputCall(new Consumer<byte[]>() {
            @SuppressLint({"NewApi", "CheckResult"})
            @Override
            public void accept(byte[] bytes) throws Exception {
                StringBuffer s = new StringBuffer();
                List<Integer> real = getHexInt(bytes);
                for (int i = 0; i < real.size(); i++) {
                    s.append(Integer.toHexString(real.get(i))).append(" ");
                }
                Log.e("AAAAA", "收到的数据: " + s);
                for (int i = 0; i < real.size(); i++) {
                    mBltInputDataTemp.add(real.get(i));
                }
                if (mBltInputDataTemp.size() > 32) {
                    mBltInputData.clear();
                    boolean isStart = false;
                    List<Integer> temp = new ArrayList<>();
                    for (int j = 0; j < mBltInputDataTemp.size(); j++) {
                        if (isStart) {
                            if (mBltInputData.size() < 32) {
                                mBltInputData.add(mBltInputDataTemp.get(j));
                            } else {
                                Observable.just(mBltInputData)
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new Consumer<List<Integer>>() {
                                            @Override
                                            public void accept(List<Integer> integers) throws Exception {
                                                if (Crc16.crc16(integers, integers.get(31) << 8 ^ integers.get(30), true)) {

                                                }
                                                StringBuffer logs = new StringBuffer();
                                                for (int i = 0; i < integers.size(); i++) {
                                                    logs.append(Integer.toHexString(integers.get(i))).append(" ");
                                                }
                                                YcLog.e("截取到的数据：", logs.toString());
                                                int x = getRealData(integers.get(18), integers.get(19), integers.get(20), integers.get(21));
                                                textView2.setText("X轴倾角数据：" + x / 10000.0);
                                                int y = getRealData(integers.get(22), integers.get(23), integers.get(24), integers.get(25));
                                                textView3.setText("y轴倾角数据：" + y / 10000.0);
                                            }
                                        });
                                temp.add(mBltInputDataTemp.get(j));
                            }
                        } else {
                            isStart = mBltInputDataTemp.get(j) == 0xFA;
                            if (isStart)
                                mBltInputData.add(mBltInputDataTemp.get(j));
                        }
                    }
                    mBltInputDataTemp.clear();
                    mBltInputDataTemp.addAll(temp);
                }
            }
        });
        bluetoothGatt.conn(mBltDevice, getActivity());
    }

    private int getRealData(int data1, int data2, int data3, int data4) {
        YcLog.e("转换0:" + Integer.toHexString(data1) + " " + Integer.toHexString(data2) + " " + Integer.toHexString(data3) + " " + Integer.toHexString(data4));
//        YcLog.e("转换1:" +Integer.toHexString(data1<<));
        int x = (data1 << 32) ^ (data2 << 16) ^ (data3 << 8) ^ data4;
        YcLog.e("转换2:" + Integer.toHexString(x));
        if (data1 > 128) {//8位，大于128就是负数，而负数是以补码的形式
            x--;
            YcLog.e("转换3:" + Integer.toHexString(x));
            x = ~x;
            YcLog.e("转换4:" + Integer.toHexString(x));
            x *= -1;
            YcLog.e("转换5:" + Integer.toHexString(x));
        }
        return x;
    }

    public List<Integer> getHexInt(byte[] data) {
        List<Integer> dataHex = new ArrayList<>();
        for (byte item : data) {
            dataHex.add(item & 0xFF);//补码转成原码
        }
        return dataHex;
    }

    public static String getHexString(byte[] paramArrayOfByte, int paramInt1, int paramInt2) {
        StringBuilder localStringBuffer = new StringBuilder();
        int i = paramInt1;
        while (i < paramInt1 + paramInt2) {
            int j = paramArrayOfByte[i] & 0xFF;
            localStringBuffer.append(HEX_CHAR_TABLE[(j >>> 4)]);
            localStringBuffer.append(HEX_CHAR_TABLE[(j & 0xF)]);
            localStringBuffer.append(" ");
            i += 1;
        }
        return localStringBuffer.toString();
    }

    static final char[] HEX_CHAR_TABLE = {48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70};

}
