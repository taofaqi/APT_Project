package com.ttp.http.converter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.internal.$Gson$Types;
import com.google.gson.reflect.TypeToken;
import com.ttp.http.bean.BaseResult;
import com.ttp.http.bean.JsonStringRequest;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * @author faqi.tao
 * @time 2019/12/29
 */
public class JsonConverterFactory extends Converter.Factory {
    private String aclass;
    private final Gson gson;

    public static JsonConverterFactory create() {
        return create(new Gson());
    }

    public static JsonConverterFactory create(String aclass) {
        return create(new Gson(), aclass);
    }

    public static JsonConverterFactory create(Gson gson) {
        return create(gson, null);
    }

    public static JsonConverterFactory create(Gson gson, String aclass) {
        return new JsonConverterFactory(gson, aclass);
    }

    private JsonConverterFactory(Gson gson) {
        this(gson, null);
    }

    private JsonConverterFactory(Gson gson, String aclass) {
        if (gson == null) {
            throw new NullPointerException("gson == null");
        }
        this.aclass = aclass;
        this.gson = gson;
    }

    /**
     * 返回数据转换器
     *
     * @param type        参数类型
     * @param annotations 注解
     * @param retrofit    retrofit对象
     * @return
     */
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        if ((type instanceof Class && !(type == BaseResult.class))
                || (type instanceof ParameterizedType && !(((ParameterizedType) type).getRawType() == BaseResult.class))) {
            type = $Gson$Types.newParameterizedTypeWithOwner(null, BaseResult.class, type, Object.class);
        }
        TypeAdapter<?> typeAdapter = gson.getAdapter(TypeToken.get(type));
        Converter<ResponseBody, ?> converter = null;
//        if (annotations != null && annotations.length > 0) {
//            for (Annotation annotation : annotations) {
//                if (annotation instanceof NOSECRET) {}
//            }
        converter = new JsonNoSecretResponseConverter<>(typeAdapter);

        return converter;
    }

    /**
     * 请求数据转换器
     *
     * @param type                 参数类型
     * @param parameterAnnotations 参数注解
     * @param methodAnnotations    方法注解
     * @param retrofit             retrofit对象
     * @return
     */
    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[]
            parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
//        int code=0;
//        if(methodAnnotations!=null&&methodAnnotations.length>0) {
//            for (Annotation annotation : methodAnnotations) {
//                if (annotation instanceof CODE) {
//                    code = ((CODE) annotation).value();
//                }
//                if (annotation instanceof NOSECRET) {
//                    return new JsonNoSecretRequestConverter(gson, adapter, code);
//                }else if(annotation instanceof BASE64){
//                    return new JsonBase64RequestConverter<>(gson, adapter, code);
//                }else if(annotation instanceof WEBVIEW){
//                    return new WebViewRequestConverter<>(gson, adapter, code);
//                }
//            }
//        }

        return new JsonNoSecretRequestConverter<>(adapter);
    }

    @Override
    public Converter<?, String> stringConverter(Type type, Annotation[] annotations, Retrofit
            retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new JsonStringRequest<>(gson, adapter);
    }

}