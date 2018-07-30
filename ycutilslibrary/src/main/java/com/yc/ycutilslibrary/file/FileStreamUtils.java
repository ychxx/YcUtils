package com.yc.ycutilslibrary.file;

import android.os.Environment;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 用于文件流的工具类
 */

public class FileStreamUtils {
    /**
     * SD卡根目录地址
     */
    public final static String SD_PATH = Environment.getExternalStorageDirectory().getPath();

    /**
     * 文件写入
     *
     * @param filePath     文件地址
     * @param writeContent 写入的内容
     */
    public static void writeFile(String filePath, String writeContent) {
        try {
            FileUtils.createFile(filePath);
            FileOutputStream fout = new FileOutputStream(filePath);
            byte[] bytes = writeContent.getBytes();
            fout.write(bytes);
            fout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param filePath
     * @return
     */
    public static String readFile(String filePath) {
        String res = "";
        try {
            FileInputStream fin = new FileInputStream(filePath);
            int length = fin.available();
            byte[] buffer = new byte[length];
            fin.read(buffer);
            res = new String(buffer, "UTF-8");
            fin.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
}
