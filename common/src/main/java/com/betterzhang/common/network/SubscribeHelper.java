package com.betterzhang.common.network;


import java.net.ConnectException;
import java.net.SocketException;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * Created by Android Studio.
 * Author : zhangzhongqiang
 * Email  : betterzhang.dev@gmail.com
 * Time   : 2017/08/04 下午 4:53
 * Desc   : Subscribe工具类
 */

public class SubscribeHelper<T> implements Observer<T> {

    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onNext(@NonNull T t) {

    }

    @Override
    public void onError(@NonNull Throwable e) {
        String msg = "";
        if (e instanceof ApiException) {
            msg = "服务器异常";
        } else if (e instanceof ConnectException) {
            msg = "连接超时";
        } else if (e instanceof SocketException) {
            msg = "连接超时";
        } else if (e instanceof HttpException) {
            msg = "请检查您的网络连接，稍后重试";
        }
    }

    @Override
    public void onComplete() {

    }

}
