package com.betterzhang.common.network.interceptor;

import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Android Studio.
 * Author : zhangzhongqiang
 * Email  : betterzhang.dev@gmail.com
 * Time   : 2017/08/08 下午 4:48
 * Desc   : 公共请求头拦截器
 */

public class HttpHeaderInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        request = request.newBuilder()
                .header("User-Agent", "Android, xxx")
                .header("Accept", "application/json")
                .header("Content-type", "application/json")
                .method(request.method(), request.body())
                .build();
        return chain.proceed(request);
    }

}
