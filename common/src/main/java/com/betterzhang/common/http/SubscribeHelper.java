package com.betterzhang.common.http;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;
import io.reactivex.annotations.NonNull;
import retrofit2.HttpException;

/**
 * Created by Android Studio.
 * Author : zhangzhongqiang
 * Email  : betterzhang.dev@gmail.com
 * Time   : 2017/08/04 下午 4:53
 * Desc   : Subscribe工具类
 */

public class SubscribeHelper<T> implements Subscriber<T> {

    @Override
    public void onSubscribe(Subscription s) {

    }

    @Override
    public void onNext(@NonNull T t) {

    }

    @Override
    public void onError(@NonNull Throwable e) {
        String msg = "";
        if (e instanceof ApiException) {
            msg = e.getMessage();
        } else if (e instanceof UnknownHostException) {
            msg = "网络异常，请检查您的网络状态";
        }
        else if (e instanceof ConnectException) {
            msg = "网络连接异常，请检查您的网络状态";
        } else if (e instanceof SocketTimeoutException) {
            msg = "网络连接超时，请检查您的网络状态，稍后重试";
        } else if (e instanceof HttpException) {
            msg = "请检查您的网络连接，稍后重试";
        } else if (e instanceof ParseException) {
            msg = "解析失败";
        }
        else {
            msg = "未知错误";
        }
    }

    @Override
    public void onComplete() {

    }

}
