package com.ttp.http;

/**
 * @author faqi.tao
 * @time 2019/12/29
 */
public interface HttpListener<T> {

    void onSuccess(T result);

    void onError(String errorResult);

    void onFinal();
}
