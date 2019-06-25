package com.yc.ycutils.test.blt;

/**
 * 蓝牙数据处理帮助类
 */
public class BltDataHelper {
    /**
     * 补码转原码
     * @param complementCode
     * @return
     */
    public byte complementCodeToTrueCode(byte complementCode) {
        return (byte) (complementCode & 0xFF);
    }
}
