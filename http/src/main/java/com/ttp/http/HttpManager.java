package com.ttp.http;

import com.ttp.http.Interceptor.HeaderBodyInterceptor;
import com.ttp.http.Interceptor.HttpDNS;
import com.ttp.http.Interceptor.LoggingInterceptor;
import com.ttp.http.Interceptor.NetworkInterceptor;
import com.ttp.http.adapter.HttpTaskCallAdapterFactory;
import com.ttp.http.converter.JsonConverterFactory;
import com.ttp.http.utils.HttpConfig;

import java.util.concurrent.TimeUnit;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * @author faqi.tao
 * @time 2019/12/27
 */
public class HttpManager {

    private HttpManager() {

    }

    public static <T> T getHttpService(Class<T> aClass) {
        return getInstance().initRetrofit(aClass);
    }

    private static HttpManager getInstance() {
        return HttpManagerImpl.instance;
    }

    private static class HttpManagerImpl {
        private static final HttpManager instance = new HttpManager();
    }

    private <T> T initRetrofit(Class<T> aClass) {
        Scheduler ioScheduler = Schedulers.io();
        RxJava2CallAdapterFactory rxJavaCallAdapterFactory = RxJava2CallAdapterFactory.createWithScheduler(ioScheduler);
        Retrofit retrofit = new Retrofit.Builder()
                .client(initOkHttpClient())
//                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(JsonConverterFactory.create())
                .addCallAdapterFactory(HttpTaskCallAdapterFactory.createWithScheduler(rxJavaCallAdapterFactory,ioScheduler))
//                .addCallAdapterFactory(rxJavaCallAdapterFactory)
                .baseUrl(HttpConfig.baseUrl)
                .build();
        return retrofit.create(aClass);
    }

    private OkHttpClient initOkHttpClient() {
        OkHttpClient build = new OkHttpClient.Builder()
                .addInterceptor(new LoggingInterceptor())
                .addNetworkInterceptor(new NetworkInterceptor())
                .addNetworkInterceptor(new HeaderBodyInterceptor())
                .callTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .dns(new HttpDNS())
                .build();
        return build;
    }
}
