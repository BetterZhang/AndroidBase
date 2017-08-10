package com.betterzhang.common.http.interceptor;

import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Android Studio.
 * Author : zhangzhongqiang
 * Email  : betterzhang.dev@gmail.com
 * Time   : 2017/08/08 下午 4:45
 * Desc   : User-Agent拦截器
 */

public class UserAgentInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        request = request.newBuilder()
                .removeHeader("User-Agent")
                .addHeader("User-Agent", "")
                .build();
        return chain.proceed(request);
    }

}
