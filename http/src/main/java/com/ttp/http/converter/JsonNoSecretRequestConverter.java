package com.ttp.http.converter;

import com.google.gson.TypeAdapter;

/**
 * @author faqi.tao
 * @time 2019/12/30
 */
public class JsonNoSecretRequestConverter<T> extends BaseRequestConverter<T> {
    public JsonNoSecretRequestConverter(TypeAdapter<T> adapter) {
        super(adapter);
    }

    @Override
    public void disposeJson(String[] args) {

    }
}
