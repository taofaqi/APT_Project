package com.ttp.http.Interceptor;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author faqi.tao
 * @time 2019/12/27
 */
public class NetworkInterceptor implements Interceptor {
    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();

        Request request = builder.build();
        return chain.proceed(request);
    }
}
