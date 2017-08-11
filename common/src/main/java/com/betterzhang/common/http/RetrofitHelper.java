package com.betterzhang.common.http;

import okhttp3.Interceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Android Studio.
 * Author : zhangzhongqiang
 * Email  : betterzhang.dev@gmail.com
 * Time   : 2017/08/04 下午 1:16
 * Desc   : Retrofit工具类
 */

public class RetrofitHelper {

    private static RetrofitHelper instance = null;

    private RetrofitHelper() {

    }

    public static RetrofitHelper getInstance() {
        if (instance == null) {
            synchronized (RetrofitHelper.class) {
                if (instance == null) {
                    instance = new RetrofitHelper();
                }
            }
        }
        return instance;
    }

    public <T> T createApi(Class<T> clazz, String baseUrl, Interceptor headerInterceptor, boolean cacheFlag) {
        Retrofit.Builder builder = new Retrofit.Builder();
        if (cacheFlag)
            builder.client(OkHttpHelper.getInstance(headerInterceptor).getCacheOkHttp());
        else
            builder.client(OkHttpHelper.getInstance(headerInterceptor).getOkHttp());

        Retrofit retrofit = builder.baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(clazz);
    }

}

