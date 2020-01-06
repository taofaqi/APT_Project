package com.ttp.http.Interceptor;

import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author faqi.tao
 * @time 2019/12/27
 */
public class LoggingInterceptor implements Interceptor {

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();

        Request request = builder.build();
        URL url = request.url().url();
        Log.e("HttpManager", url.getPath() + "");

        Headers headers = request.headers();
        Log.e("HttpManager", headers.toString() + "");

        String method = request.method();
        Log.e("HttpManager", method + "");

        return chain.proceed(request);
    }
}
