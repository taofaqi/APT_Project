/*
 * Copyright (C) 2016 Jake Wharton
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ttp.http.adapter;


import com.ttp.http.HttpTask;
import com.ttp.http.ITask;

import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Response;

final class HttpTaskCallAdapter<R> implements CallAdapter<R, ITask> {
    private final Type responseType;
    Scheduler scheduler;
    private final boolean isBody;
    private Class returnType;

    HttpTaskCallAdapter(Type responseType, Scheduler scheduler, boolean isBody, Class returnType) {
        this.responseType = responseType;
        this.scheduler = scheduler;
        this.isBody = isBody;
        this.returnType = returnType;
    }

    @Override
    public Type responseType() {
        return responseType;
    }

    @Override
    public ITask adapt(Call<R> call) {
        Observable<Response<R>> responseObservable = new CallExecuteOnSubscribe<>(call);
        Observable<?> observable;
        if (isBody) {
            observable = new BodyObservable<>(responseObservable);
        } else {
            observable = responseObservable;
        }
        Single<?> single = observable.singleOrError();

        if (scheduler != null) {
            single = single.subscribeOn(scheduler);
        }

        return new HttpTask(single);
    }
}
