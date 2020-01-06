package com.ttp.http.converter;

import com.google.gson.TypeAdapter;

/**
 * @author faqi.tao
 * @time 2019/12/30
 */
public class JsonNoSecretResponseConverter<T> extends BaseResponseConverter<T> {
    public JsonNoSecretResponseConverter(TypeAdapter<T> adapter) {
        super(adapter);
    }

    @Override
    protected String disposeResponse(String src) throws Exception {
        return src;
    }
}
