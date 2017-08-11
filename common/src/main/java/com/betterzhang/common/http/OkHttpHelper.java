package com.betterzhang.common.http;

import com.betterzhang.common.app.BaseApplication;
import com.betterzhang.common.http.interceptor.CacheInterceptor;
import com.betterzhang.common.util.AppUtils;
import com.betterzhang.common.util.FileUtil;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import okhttp3.Cache;
import okhttp3.ConnectionSpec;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by Android Studio.
 * Author : zhangzhongqiang
 * Email  : betterzhang.dev@gmail.com
 * Time   : 2017/08/08 下午 4:52
 * Desc   : OkHttp帮助类，可添加缓存，添加公共请求参数
 */

public class OkHttpHelper {

    private static OkHttpHelper instance;
    private final OkHttpClient.Builder mOkHttpBuilder;

    private static final int CONNECT_TIMEOUT = 30;
    private static final int WRITE_TIMEOUT = 20;
    private static final int READ_TIMEOUT = 20;

    private OkHttpHelper(Interceptor headerInterceptor) {
        mOkHttpBuilder = HttpsUtil.getUnsafeOkHttpClient();

        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        if (AppUtils.isDebug())
            logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        else
            logInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);

        mOkHttpBuilder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                // 明文Http与比较新的Https
                .connectionSpecs(Arrays.asList(ConnectionSpec.CLEARTEXT, ConnectionSpec.MODERN_TLS))
                .addInterceptor(logInterceptor)
                .addInterceptor(headerInterceptor);
    }

    public static OkHttpHelper getInstance(Interceptor headerInterceptor) {
        if (instance == null) {
            instance = new OkHttpHelper(headerInterceptor);
        }
        return instance;
    }

    /**
     * 不带缓存的OkHttp客户端
     *
     * @return
     */
    public OkHttpClient getOkHttp() {
        return mOkHttpBuilder.build();
    }

    /**
     * 带缓存的OkHttp客户端
     *
     * @return
     */
    public OkHttpClient getCacheOkHttp() {
        return mOkHttpBuilder
                .cache(new Cache(FileUtil.getCacheFile(BaseApplication.getContext(), "file_cache"), 1024 * 1024 * 100))
                .addInterceptor(new CacheInterceptor(BaseApplication.getContext()))
                .build();
    }

}
