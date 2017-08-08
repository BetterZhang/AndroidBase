package com.betterzhang.common.network;

import com.betterzhang.common.app.BaseApplication;
import com.betterzhang.common.network.interceptor.CacheInterceptor;
import com.betterzhang.common.util.FileUtil;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import okhttp3.Cache;
import okhttp3.ConnectionSpec;
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

    private static final int CONNECTTIMEOUT = 10000;
    private static final int READTIMEOUT = 10000;

    public OkHttpManager() {
        mOkHttpBuilder = new OkHttpClient.Builder()
                .connectTimeout(CONNECTTIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READTIMEOUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                // 明文Http与比较新的Https
                .connectionSpecs(Arrays.asList(ConnectionSpec.CLEARTEXT, ConnectionSpec.MODERN_TLS));
    }

    public OkHttpManager getInstance() {
        if (instance == null) {
            instance = new OkHttpManager();
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
