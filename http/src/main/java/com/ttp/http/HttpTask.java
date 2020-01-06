package com.ttp.http;

import androidx.annotation.Nullable;

import com.google.gson.JsonParseException;
import com.ttp.http.bean.BaseResult;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;

import java.net.ConnectException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import javax.net.ssl.SSLException;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import retrofit2.HttpException;

/**
 * @author faqi.tao
 * @time 2019/12/30
 */
public class HttpTask<T> implements ITask {

    private Single<BaseResult<T>> mSingle;
    private HttpListener<T> mHttpListener;
    private Disposable mDisposable;

    public HttpTask(Single<BaseResult<T>> single) {
        mSingle = single;
        if (mSingle == null)
            throw new NullPointerException("Single is null");
    }

    public Cancelable launch(@Nullable Object target, HttpListener<T> listener) {
        mHttpListener = listener;
        checkTaskState();

        //TODO      添加重试器


        final int targetCode = target == null ? -1 : target.hashCode();
        HttpTaskManager.getInstance().addHttpTask(targetCode, this);
        mDisposable = mSingle.observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseResult<T>>() {
                    @Override
                    public void accept(BaseResult<T> result) throws Exception {
                        HttpTaskManager.getInstance().removeHttpTask(targetCode, HttpTask.this);
                        if (isCancel()) {
                            return;
                        }
                        mSingle = null;  //保证任务执行后不可以再执行
                        if (mHttpListener != null) {
                            if (null != result && result.isSuccess()) {
                                mHttpListener.onSuccess(result.data);
                            } else {
                                mHttpListener.onError(result.errorMsg);
                            }
                            mHttpListener.onFinal();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        HttpTaskManager.getInstance().removeHttpTask(targetCode, HttpTask.this);
                        if (isCancel()) {
                            return;
                        }
                        mSingle = null;  //保证任务执行后不可以再执行
                        if (mHttpListener != null) {
                            String errorMessage = getErrorMessage(throwable);
                            mHttpListener.onError(errorMessage);
                            mHttpListener.onFinal();
                        }
                    }
                });
        return newCancelable(this);
    }


    public Cancelable newCancelable(final HttpTask httpTask) {
        return new Cancelable() {
            @Override
            public void cancel() {
                httpTask.cancel();
            }

            @Override
            public boolean isCancel() {
                return httpTask.isCancel();
            }
        };
    }

    /**
     * 检查任务状态，launch 后不能再做任何操作
     */
    private void checkTaskState() {
        if (mSingle == null || (mDisposable != null && !mDisposable.isDisposed()))
            throw new RuntimeException(this.getClass().getSimpleName() + " was already launch");
    }

    @Override
    public void cancel() {
        if (!isCancel()) {
            mDisposable.isDisposed();
        }
        mSingle = null;
        mHttpListener = null;
        mDisposable = null;
    }

    @Override
    public boolean isCancel() {
        return mDisposable == null || mDisposable.isDisposed();
    }

    private static String getErrorMessage(Throwable error) {
        String msg;
        if (error instanceof HttpException) {//自定义异常
            msg = error.getMessage();
        } else if (error instanceof UnknownHostException || error instanceof ProtocolException || error instanceof ConnectException) {
            msg = "网络异常，请检查网络环境！";
        } else if (error instanceof JsonParseException || error instanceof JSONException) {
            msg = "数据格式异常，请稍后再试！";
        } else if (error instanceof SSLException) {
            msg = "连接异常或者服务器异常，请稍后再试！";
        } else if (error instanceof SocketTimeoutException || error instanceof ConnectTimeoutException) {
            msg = "连接服务器超时，请检查网络环境";
        } else {
            msg = "请求失败，请稍后再试";
        }
        return msg;
    }
}
