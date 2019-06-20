package com.yc.ycutilslibrary.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.yc.ycutilslibrary.common.Crc16;
import com.yc.ycutilslibrary.common.YcLog;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 */
public class YcBluetoothConnGatt {
    private BluetoothGatt mBluetoothGatt;
    private BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                // 连接成功后启动服务发现
                Log.e("AAAAAAAA", "启动服务发现:" + mBluetoothGatt.discoverServices());
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            //成功发现服务后可以调用相应方法得到该BLE设备的所有服务，并且打印每一个服务的UUID和每个服务下各个特征的UUID
            if (status == BluetoothGatt.GATT_SUCCESS) {
                List<BluetoothGattService> supportedGattServices = mBluetoothGatt.getServices();
                for (int i = 0; i < supportedGattServices.size(); i++) {
                    Log.e("AAAAA", "1:BluetoothGattService UUID=:" + supportedGattServices.get(i).getUuid());
                    List<BluetoothGattCharacteristic> listGattCharacteristic = supportedGattServices.get(i).getCharacteristics();
                    for (int j = 0; j < listGattCharacteristic.size(); j++) {
                        Log.e("a", "2:   BluetoothGattCharacteristic UUID=:" + listGattCharacteristic.get(j).getUuid());
                    }
                }
            } else {
                Log.e("AAAAA", "onservicesdiscovered收到: " + status);
            }
            BluetoothGattService service = mBluetoothGatt.getService(UUID.fromString("0000ffe0-0000-1000-8000-00805f9b34fb"));
            BluetoothGattCharacteristic characteristic = service.getCharacteristic(UUID.fromString("0000ffe1-0000-1000-8000-00805f9b34fb"));
//            ((BluetoothGattDescriptor)localObject).setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
//            mBluetoothGatt.readCharacteristic(characteristic);
            if (!gatt.setCharacteristicNotification(characteristic, true)) {
                YcLog.e("Couldn't set notifications for RX characteristic!");
            }
        }


        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            byte[] data = characteristic.getValue();
            String s = getHexString(data, 0, data.length);
            Log.e("AAAAA", "收到的数据: " + s);
//            Log.e("AAAAA", "收到的数据: " + new String(s, 0, s.length(), "GBK"));
        }
    };


    private int[] testData11 = {
            0xD7, 0xFA, 0x06, 0x00, 0x01, 0x19, 0x01, 0x00, 0x00, 0x07, 0xF5, 0x00, 0x00, 0x41, 0x02, 0x00, 0x03,
            0xE8, 0xCB, 0x00, 0x00, 0x11, 0xB6, 0x00, 0x00, 0x8E, 0xB3, 0x01, 0x31, 0x00, 0x00, 0x1D};

    private int[] testData12 = {0xFA, 0x06, 0x00, 0x01, 0x19, 0x01, 0x00, 0x00, 0x07, 0xF5, 0x00, 0x00, 0x41, 0x02, 0x00, 0x03,
            0xE8, 0xCB, 0x00, 0x00, 0x11, 0xB6, 0x00, 0x00, 0x8E, 0xB3, 0x01, 0x31, 0x00, 0x00, 0x1D, 0xB8};

    private int[] testData13 = {0xFA, 0x06, 0x00, 0x01, 0x19, 0x01, 0x00, 0x00, 0x07, 0xF5, 0x00, 0x00, 0x41, 0x02, 0x00, 0x03,
            0xE8, 0xCB, 0x00, 0x00, 0x11, 0xB6, 0x00, 0x00, 0x8E, 0xB3, 0x01, 0x31, 0x00, 0x00};


    private int[] testData31 = {0xB0, 0xFA, 0x06, 0x00, 0x01, 0x19, 0x01, 0x00, 0x00, 0x05, 0x7D, 0x00, 0x00, 0x3B, 0xB3, 0x00,
            0x03, 0xE8, 0xEB, 0x00, 0x00, 0x0C, 0x37, 0x00, 0x00, 0x83, 0x08, 0x01, 0x2C, 0x00, 0x00, 0x43};

    private int[] testData32 = {0xFA, 0x06, 0x00, 0x01, 0x19, 0x01, 0x00, 0x00, 0x05, 0x7D, 0x00, 0x00, 0x3B, 0xB3, 0x00, 0x03,
            0xE8, 0xEB, 0x00, 0x00, 0x0C, 0x37, 0x00, 0x00, 0x83, 0x08, 0x01, 0x2C, 0x00, 0x00, 0x43, 0xCE};

    private int[] testData33 = {0xFA, 0x06, 0x00, 0x01, 0x19, 0x01, 0x00, 0x00, 0x05, 0x7D, 0x00, 0x00, 0x3B, 0xB3, 0x00, 0x03,
            0xE8, 0xEB, 0x00, 0x00, 0x0C, 0x37, 0x00, 0x00, 0x83, 0x08, 0x01, 0x2C, 0x00, 0x00};

    private int[] testData21 = {0xCE, 0xFA, 0x06, 0x00, 0x01, 0x19, 0x01, 0x00, 0x00, 0x05, 0x81, 0x00, 0x00, 0x3B, 0xB6, 0x00,
            0x03, 0xE8, 0xF3, 0x00, 0x00, 0x0C, 0x40, 0x00, 0x00, 0x83, 0x0F, 0x01, 0x2D, 0x00, 0x00, 0xFE};

    private int[] testData22 = {0xFA, 0x06, 0x00, 0x01, 0x19, 0x01, 0x00, 0x00, 0x05, 0x81, 0x00, 0x00, 0x3B, 0xB6, 0x00, 0x03,
            0xE8, 0xF3, 0x00, 0x00, 0x0C, 0x40, 0x00, 0x00, 0x83, 0x0F, 0x01, 0x2D, 0x00, 0x00, 0xFE, 0x86};

    private int[] testData23 = {0xFA, 0x06, 0x00, 0x01, 0x19, 0x01, 0x00, 0x00, 0x05, 0x81, 0x00, 0x00, 0x3B, 0xB6, 0x00, 0x03,
            0xE8, 0xF3, 0x00, 0x00, 0x0C, 0x40, 0x00, 0x00, 0x83, 0x0F, 0x01, 0x2D, 0x00, 0x00};


    public void testCrc() {
//        YcLog.e("测试结果1" + Integer.toString(crc16(testData11), 16));
//        YcLog.e("测试结果2" + Integer.toString(crc16(testData12), 16));
//        YcLog.e("测试结果3" + Integer.toString(crc16(testData13), 16));
//
//        YcLog.e("测试结果221" + Integer.toString(crc16(testData21), 16));
//        YcLog.e("测试结果222" + Integer.toString(crc16(testData22), 16));
//        YcLog.e("测试结果223" + Integer.toString(crc16(testData23), 16));
//
//        YcLog.e("测试结果331" + Integer.toString(crc16(testData31), 16));
//        YcLog.e("测试结果332" + Integer.toString(crc16(testData32), 16));
//        YcLog.e("测试结果333" + Integer.toString(crc16(testData33), 16));
        Crc16 crc16 = new Crc16();
        YcLog.e("测试结果333" + crc16.crc16(testData13, new int[]{0x1D, 0xB8}));

    }

    public static String getHexString(byte[] paramArrayOfByte, int paramInt1, int paramInt2) {
        List<Integer> data = new ArrayList<>();
        StringBuffer localStringBuffer = new StringBuffer();
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

    public void conn(BluetoothDevice device, Context context) {
        mBluetoothGatt = device.connectGatt(context, false, mGattCallback);
    }
}
