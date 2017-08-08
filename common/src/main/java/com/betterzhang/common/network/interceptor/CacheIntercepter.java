package com.betterzhang.common.network.interceptor;

import android.content.Context;
import android.util.Log;
import com.betterzhang.common.util.NetworkUtil;
import java.io.IOException;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Android Studio.
 * Author : zhangzhongqiang
 * Email  : betterzhang.dev@gmail.com
 * Time   : 2017/08/08 下午 4:36
 * Desc   : 缓存拦截器
 */

public class CacheIntercepter implements Interceptor {

    private Context context;

    public CacheIntercepter(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (NetworkUtil.isNetworkAvailable(context)) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_NETWORK)
                    .build();
        } else {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
        }

        Response response = chain.proceed(request);
        if (NetworkUtil.isNetworkAvailable(context)) {
            response = response.newBuilder()
                    .removeHeader("Pragma")
                    .addHeader("Cache-Control", "public,max-age=10")
                    .build();
            Log.d("response", "有网");
        } else {
            long maxage = 60 * 60 * 24 *4;
            response = response.newBuilder()
                    .removeHeader("Pragma")
                    .addHeader("Cache-Control", "public,only-if-cache,max-stale=" + maxage)
                    .build();
            Log.d("response", "无网");
        }
        return response;
    }

}
