package com.yc.ycutilslibrary.exception;

/**
 *  基础异常类
 */

public class YcException extends Exception {
    private String msg = "";

    public YcException() {
        this("");
    }

    public YcException(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
