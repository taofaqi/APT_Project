package com.ttp.http.bean;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.io.IOException;

import retrofit2.Converter;

/**
 * <li>Package:consumer.ttpc.com.httpmodule.converterfactory</li>
 * <li>Author: yehaijian </li>
 * <li>Date: 2018/11/1</li>
 * <li>Description:   </li>
 */
public class JsonStringRequest<T> implements Converter<T, String> {
    protected final Gson gson;
    protected final TypeAdapter<T> adapter;
    @Override
    public String convert(T value) throws IOException {
        if(value instanceof String) {
            return (String) value;
        }
        else {
            return "";
        }
    }

    public JsonStringRequest(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }
}
