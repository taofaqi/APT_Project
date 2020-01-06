package com.ttp.http.converter;

import android.text.TextUtils;

import com.google.gson.TypeAdapter;
import com.ttp.http.config.BlueLog;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * @author faqi.tao
 * @time 2019/12/30
 */
public abstract class BaseResponseConverter<T> implements Converter<ResponseBody, T> {

    private final TypeAdapter<T> adapter;

    public BaseResponseConverter(TypeAdapter<T> adapter) {
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody responseBody) throws IOException {
        String response = responseBody.string();
        String result = null;

//        BlueLog.e("response", "response ##：" + response);
        try {
            result = disposeResponse(response);
        } catch (Exception e) {
            e.printStackTrace();
        }

        BlueLog.e("result", "result ##：" + result);
        if (TextUtils.isEmpty(result)) {
            throw new NullPointerException("服务单返回为空");
        }
        T t = adapter.fromJson(result);

        return t;
    }

    /**
     *
     * @param src   处理之前的返回数据
     * @return  处理之后的返回数据
     * @throws Exception
     */
    protected abstract String disposeResponse(String src) throws Exception;
}
