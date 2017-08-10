package com.betterzhang.common.http;

import com.betterzhang.common.app.BaseApplication;
import com.betterzhang.common.http.interceptor.CacheInterceptor;
import com.betterzhang.common.util.FileUtil;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

/**
 * Created by Android Studio.
 * Author : zhangzhongqiang
 * Email  : betterzhang.dev@gmail.com
 * Time   : 2017/08/08 下午 4:52
 * Desc   : OkHttp管理类，可添加缓存，添加公共请求参数
 */

public class OkHttpManager {

    private static OkHttpManager instance;
    private final OkHttpClient.Builder mOkHttpBuilder;

    private static final int CONNECT_TIMEOUT = 30;
    private static final int WRITE_TIMEOUT = 20;
    private static final int READ_TIMEOUT = 20;

    public OkHttpManager(Interceptor headerInterceptor) {
//        mOkHttpBuilder = new OkHttpClient.Builder()
//                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
//                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
//                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
//                .retryOnConnectionFailure(true)
//                // 明文Http与比较新的Https
//                .connectionSpecs(Arrays.asList(ConnectionSpec.CLEARTEXT, ConnectionSpec.MODERN_TLS));
        mOkHttpBuilder = Connection.getUnsafeOkHttpClient(headerInterceptor);
    }

    public static OkHttpManager getInstance(Interceptor headerInterceptor) {
        if (instance == null) {
            instance = new OkHttpManager(headerInterceptor);
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
