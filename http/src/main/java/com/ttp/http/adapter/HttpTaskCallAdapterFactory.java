package com.ttp.http.adapter;

import com.ttp.http.ITask;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import io.reactivex.Scheduler;
import retrofit2.CallAdapter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * FileName: HttpTaskCallAdapterFactory
 * Author: zhihao.wu@ttpai.cn
 * Date: 2019/6/19
 * Description: httpTask 适配器
 */
public class HttpTaskCallAdapterFactory extends CallAdapter.Factory {

    public static HttpTaskCallAdapterFactory createWithScheduler(RxJava2CallAdapterFactory factory, Scheduler scheduler) {
        if (factory == null) throw new NullPointerException("RxJavaCallAdapterFactory == null");
        if (scheduler == null) throw new NullPointerException("scheduler == null");
        return new HttpTaskCallAdapterFactory(factory, scheduler);
    }

    private final RxJava2CallAdapterFactory rxJavaFactory;
    private final Scheduler scheduler;

    private HttpTaskCallAdapterFactory(RxJava2CallAdapterFactory factory, Scheduler scheduler) {
        this.rxJavaFactory = factory;
        this.scheduler = scheduler;
    }

    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        Class<?> rawType = getRawType(returnType);
        if (!isITask(rawType)) {//不支持，使用rxJavaAdapter
            return rxJavaFactory.get(returnType, annotations, retrofit);
        }

        boolean isBody = false;
        Type responseType;
        if (!(returnType instanceof ParameterizedType)) {
            String name = "HttpTask";
            throw new IllegalStateException(name + " return type must be parameterized"
                    + " as " + name + "<Foo> or " + name + "<? extends Foo>");
        }

        Type observableType = getParameterUpperBound(0, (ParameterizedType) returnType);
        Class<?> rawObservableType = getRawType(observableType);
        if (rawObservableType == Response.class) {
            if (!(observableType instanceof ParameterizedType)) {
                throw new IllegalStateException("Response must be parameterized"
                        + " as Response<Foo> or Response<? extends Foo>");
            }
            responseType = getParameterUpperBound(0, (ParameterizedType) observableType);
        } else {
            responseType = observableType;
            isBody = true;
        }

        return new HttpTaskCallAdapter(responseType, scheduler, isBody, rawType);
    }

    private boolean isITask(Class<?> rawType) {
        Class<?>[] interfaces = rawType.getInterfaces();
        if (interfaces != null) {
            for (Class c : interfaces) {
                if (c == ITask.class)
                    return true;
            }
        }
        return false;
    }
}
