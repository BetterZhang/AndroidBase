package com.betterzhang.common.http;

import android.text.TextUtils;
import com.betterzhang.common.utils.ToastUtils;
import com.google.gson.JsonParseException;
import org.json.JSONException;
import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.text.ParseException;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * Created by Android Studio.
 * Author : zhangzhongqiang
 * Email  : betterzhang.dev@gmail.com
 * Time   : 2019/10/23 16:51
 * Desc   : description
 */
public abstract class DefaultObserver<T extends HttpResult> implements Observer<T> {

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T t) {
        if (t.isSuccess())
            onSuccess(t);
        else
            onFail(t);

    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof HttpException) {   // HTTP错误
            onException(ExceptionReason.BAD_NETWORK);
        } else if (e instanceof ConnectException
                || e instanceof UnknownHostException) { // 连接错误
            onException(ExceptionReason.CONNECTION_ERROR);
        } else if (e instanceof InterruptedIOException) {   // 连接超时
            onException(ExceptionReason.CONNECTION_TIMEOUT);
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {   // 解析错误
            onException(ExceptionReason.PARSE_ERROR);
        } else {    // 未知错误
            onException(ExceptionReason.UNKNOWN_ERROR);
        }
    }

    @Override
    public void onComplete() {

    }

    abstract public void onSuccess(T response);

    private void onFail(T response) {
        String msg = response.getHead().getMsg();
        if (TextUtils.isEmpty(msg)) {
            ToastUtils.show("服务器返回错误");
        } else {
            ToastUtils.show(msg);
        }
    }

    private void onException(ExceptionReason reason) {
        switch (reason) {
            case PARSE_ERROR:
                ToastUtils.show("解析数据错误");
                break;
            case BAD_NETWORK:
                ToastUtils.show("网络错误");
                break;
            case CONNECTION_ERROR:
                ToastUtils.show("连接错误");
                break;
            case CONNECTION_TIMEOUT:
                ToastUtils.show("连接超时");
                break;
            case UNKNOWN_ERROR:
                ToastUtils.show("未知错误");
                break;
        }
    }

    public enum ExceptionReason {
        /**
         * 解析数据失败
         */
        PARSE_ERROR,
        /**
         * 网络问题
         */
        BAD_NETWORK,
        /**
         * 连接错误
         */
        CONNECTION_ERROR,
        /**
         * 连接超时
         */
        CONNECTION_TIMEOUT,
        /**
         * 未知错误
         */
        UNKNOWN_ERROR,
    }
}
