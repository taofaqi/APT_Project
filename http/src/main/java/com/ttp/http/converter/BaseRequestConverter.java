package com.ttp.http.converter;

import com.google.gson.TypeAdapter;
import com.ttp.http.config.BlueLog;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.RequestBody;
import retrofit2.Converter;

/**
 * @author faqi.tao
 * @time 2019/12/30
 */
public abstract class BaseRequestConverter<T> implements Converter<T, RequestBody> {

    private final TypeAdapter<T> adapter;

    public BaseRequestConverter(TypeAdapter<T> adapter) {
        this.adapter = adapter;
    }

    @Override
    public RequestBody convert(T value) throws IOException {
        FormBody.Builder builder = new FormBody.Builder();


        BlueLog.e("request", "value：" + value.toString());

        //TODO   修改请求的  Header  Body
        return builder.build();
    }

    public abstract void disposeJson(String[] args);


}
