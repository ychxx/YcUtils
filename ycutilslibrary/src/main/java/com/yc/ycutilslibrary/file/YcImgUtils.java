package com.yc.ycutilslibrary.file;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Base64;
import android.widget.ImageView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.yc.ycutilslibrary.R;
import com.yc.ycutilslibrary.common.YcEmpty;
import com.yc.ycutilslibrary.common.YcLog;
import com.yc.ycutilslibrary.configure.GlideApp;
//import com.yc.ycutilslibrary.configure.GlideApp;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 */

public class YcImgUtils {
    /**
     * 加载网络图片失败时显示的图片
     */
    public static int IMG_FAIL_ID_RES = R.drawable.img_loading;
    /**
     * 加载网络图片加载时显示的图片
     */
    public static int IMG_LOADING_ID_RES = R.drawable.img_loading;
    /**
     * 加载网络图片失败重新加载的次数
     */
    public static int IMG_FAIL_RELOAD_NUM = 3;

    public static void loadNetImg(Context activity, String imgUrl, ImageView imageView) {
        loadNetImg(activity, imgUrl, imageView, 0);
    }

    public static void loadNetImg(final Context activity, final String imgUrl, final ImageView imageView, final int loadNum) {
        if (activity == null || YcEmpty.isEmpty(imgUrl) || imageView == null) {
            return;
        }
        GlideApp.with(activity)
//                .asGif()//指定加载类型
//                .asBitmap()//指定加载类型   git图会变成第一帧静态图
//                .asDrawable()//指定加载类型
//                .asFile()//指定加载类型
                .load(imgUrl)
                .error(IMG_FAIL_ID_RES)//失败显示的图片
                .placeholder(IMG_LOADING_ID_RES)//加载中的图片
                /*DiskCacheStrategy.ALL 使用DATA和RESOURCE缓存远程数据，仅使用RESOURCE来缓存本地数据。
                  DiskCacheStrategy.NONE 不使用磁盘缓存
                  DiskCacheStrategy.DATA 在资源解码前就将原始数据写入磁盘缓存
                  DiskCacheStrategy.RESOURCE 在资源解码后将数据写入磁盘缓存，即经过缩放等转换后的图片资源。
                  DiskCacheStrategy.AUTOMATIC 根据原始图片数据和资源编码策略来自动选择磁盘缓存策略。*/
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                //Glide会自动读取ImageView的缩放类型，所以一般在布局文件指定scaleType即可。
//                .fitCenter()//图片样式-居中填充
                .listener(new RequestListener<Drawable>() {//添加失败重新加载监听
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        if (loadNum < IMG_FAIL_RELOAD_NUM)
                            loadNetImg(activity, imgUrl, imageView, loadNum + 1);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(imageView);
    }

    /**
     * 加载本地图片
     *
     * @param imgPath   图片路径
     * @param imageView
     */
    public static void loadLocalImg(Context context, String imgPath, ImageView imageView) {
        if (YcEmpty.isEmpty(imgPath)) {
            YcLog.e("图片加载失败!");
        }
        GlideApp.with(context)
                .load(new File(imgPath))
                .into(imageView);
//        try {
//            Bitmap bmp = null;
//            bmp = MediaStore.Images.Media.getBitmap(context.getContentResolver(), Uri.fromFile(new File(imgPath)));
//            imageView.setImageBitmap(bmp);
//        } catch (IOException e) {
//            YcLog.e("图片加载失败!");
//            e.printStackTrace();
//        }
    }

    /**
     * bitmap转成png格式保存
     *
     * @param bitmap      资源
     * @param imgPathSave 保存路径
     */
    public static boolean saveImg(Bitmap bitmap, String imgPathSave) {
        try {
            File file = YcFileUtils.createFile(imgPathSave);
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
            bos.flush();
            bos.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            YcLog.e("图片保存失败!");
            return false;
        }
    }

    public static Bitmap decodeSampledBitmapFromFilePath(String imagePath, int reqWidth, int reqHeight) {
        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, options);
        // 调用上面定义的方法计算inSampleSize值
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(imagePath, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // 源图片的高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            // 计算出实际宽高和目标宽高的比率
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高一定都会大于等于目标的宽和高。
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

}
