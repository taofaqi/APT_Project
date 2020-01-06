package com.ttp.http.bean;

import android.text.TextUtils;

/**
 * @author faqi.tao
 * @time 2019/12/27
 */
public class BaseResult<T> {
    public int errorCode;
    public String errorMsg;
    public T data;

    @Override
    public String toString() {
        return "BaseResult{" +
                "errorCode=" + errorCode +
                ", errorMsg='" + errorMsg + '\'' +
                ", data=" + data +
                '}';
    }

    public boolean isSuccess() {
        return errorCode == 0 && TextUtils.isEmpty(errorMsg);
    }
}
