package com.yc.ycutilslibrary.call;

/**
 *  上传文件的回调
 */

public interface IUploadFileCall<T> {
    void onSuccess(T result);

    void onError(Throwable ex, boolean isOnCallback);

    void onCancelled(Exception ex);

    void onFinished();
}
