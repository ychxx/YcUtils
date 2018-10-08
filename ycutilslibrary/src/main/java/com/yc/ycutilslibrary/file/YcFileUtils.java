package com.yc.ycutilslibrary.file;

import android.app.ProgressDialog;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.yc.ycutilslibrary.common.YcEmpty;
import com.yc.ycutilslibrary.common.YcLog;
import com.yc.ycutilslibrary.common.YcRandom;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * 文件工具类
 * 处理文件相关
 */

public class YcFileUtils {
    /**
     * 创建一个文件
     *
     * @param filePath 文件地址（包含后缀）
     * @return 返回是否创建成功
     */
    public static File createFile(String filePath) {
        File newFile = null;
        try {
            newFile = new File(filePath);
            //判断文件是否不存在，不存在则创建
            if (!newFile.getParentFile().exists()) {
                if (!newFile.getParentFile().mkdirs()) {
                    Log.e("YcUtils", "文件夹创建失败" + filePath);
                }
            }
            if (!newFile.exists()) {
                if (newFile.createNewFile()) {
                    Log.e("YcUtils", "文件创建成功" + filePath);
                } else {
                    Log.e("YcUtils", "文件创建失败");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("YcUtils", "文件创建失败异常:" + e.getMessage());
        }
        return newFile;
    }

    /**
     * 删除文件
     *
     * @param filePath 文件路径（包含后缀）
     * @return 是否删除成功
     */
    public static boolean delFiel(String filePath) {
        if (YcEmpty.isEmpty(filePath))
            return true;
        try {
            File file = new File(filePath);
            if (file.exists())
                return file.delete();
        } catch (Exception e) {
            Log.e("YcUtils", e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 检查文件是否存在
     *
     * @param fileUrl
     * @return 是否存在
     */
    public static boolean checkFileExists(String fileUrl) {
        if (!YcEmpty.isEmpty(fileUrl)) {
            File newPath = new File(fileUrl);
            return newPath.exists();
        } else {
            return false;
        }
    }

    /**
     * 获取后缀（根据文件地址）
     *
     * @param filePath 文件地址(含后缀)
     * @return 后缀
     */
    public static String filePathToSuffix(String filePath) {
        if (YcEmpty.isEmpty(filePath))
            return null;
        return fileNameToSuffix(new File(filePath).getName());
    }

    /**
     * 获取后缀（根据文件名）
     *
     * @param fileName 文件名(含后缀)
     * @return 后缀
     */
    public static String fileNameToSuffix(String fileName) {
        if (YcEmpty.isEmpty(fileName))
            return null;
        return fileName.substring(1, fileName.lastIndexOf("."));
    }

    /**
     * 将文件转为byte[]
     *
     * @param filePath 文件路径（包含后缀）
     * @return
     */
    public static byte[] fileToBytes(String filePath) {
        if (YcEmpty.isEmpty(filePath))
            return null;
        byte[] s = null;
        try {
            File file = new File(filePath);
            FileInputStream in = new FileInputStream(file);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = in.read(b)) != -1) {
                out.write(b, 0, n);
            }
            out.close();
            in.close();
            s = out.toByteArray();
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }


    /**
     * 获取指定文件大小
     *
     * @param filePath
     * @return
     * @throws Exception
     */
    public static long getFileSize(String filePath) {
        long size = 0;
        try {
            File file = new File(filePath);
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                size = fis.available();
            } else {
                Log.e("YcUtils", "获取文件大小失败！原因：文件不存在!");
            }
        } catch (Exception e) {
            Log.e("YcUtils", "获取文件大小失败！原因：" + e.getMessage());
        }
        return size;
    }
    public static String getImgFileName(){
        return Environment.getExternalStorageDirectory() + "/YcUtils/" + YcRandom.getNameImgOfPNG();
    }
    public static void downloadFile(String url, String savePath, Callback.ProgressCallback<File> callback) {
        if (YcEmpty.isEmpty(url)) {
            YcLog.e("下载地址为空");
            return;
        }
        RequestParams requestParams = new RequestParams(url);
        requestParams.setSaveFilePath(savePath);
        YcLog.e("下载地址" + url);
        x.http().get(requestParams, callback);
    }

}