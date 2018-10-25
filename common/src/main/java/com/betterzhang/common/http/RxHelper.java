package com.betterzhang.common.http;

import org.reactivestreams.Publisher;
import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Android Studio.
 * Author : zhangzhongqiang
 * Email  : betterzhang.dev@gmail.com
 * Time   : 2017/08/04 下午 2:03
 * Desc   : Rx工具类
 */

public class RxHelper {

    /**
     * 统一RxJava线程处理
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> switchObservableSchedulers() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T> FlowableTransformer<T, T> switchFlowableSchedulers() {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T> SingleTransformer<T, T> switchSingleSchedulers() {
        return new SingleTransformer<T, T>() {
            @Override
            public SingleSource<T> apply(Single<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 统一结果处理
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<HttpResult<T>, T> handleResult() {
        return new ObservableTransformer<HttpResult<T>, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<HttpResult<T>> upstream) {
                return upstream.flatMap(new Function<HttpResult<T>, Observable<T>>() {
                    @Override
                    public Observable<T> apply(@NonNull HttpResult<T> result) {
                        if (result == null) {
                            return Observable.error(new NetworkConnectionException());
                        } else if (result.isSuccess()) {
                            return createData(result.getBody());
                        } else {
                            return Observable.error(new ApiException(result.getHead().getCode(), result.getHead().getMsg()));
                        }
                    }
                });
            }
        };
    }

    /**
     * 生成Observable
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Observable<T> createData(final T data) {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<T> emitter) {
                try {
                    emitter.onNext(data);
                    emitter.onComplete();
                } catch (Exception exception) {
                    emitter.onError(exception);
                }
            }
        });
    }

}
