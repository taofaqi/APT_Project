package com.ttp.http.Interceptor;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author faqi.tao
 * @time 2019/12/29
 */
public class HeaderBodyInterceptor implements Interceptor {
    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        builder.header("Content-Type", "application/json;charSet=UTF-8");

        builder.removeHeader("Pragma");
        builder.header("Cache-Control", "public, only-if-cached, max-stale=60");
        Request request = builder.build();
        return chain.proceed(request);
    }
}
