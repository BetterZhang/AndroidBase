package com.betterzhang.puer.service;

import com.betterzhang.common.http.HttpResult;
import com.betterzhang.common.http.RetrofitHelper;
import com.betterzhang.puer.model.PuerUserVo;
import java.io.IOException;
import java.util.HashMap;
import io.reactivex.Flowable;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Android Studio.
 * Author : zhangzhongqiang
 * Email  : betterzhang.dev@gmail.com
 * Time   : 2017/08/09 下午 1:29
 * Desc   : 普洱交易Service
 */

public class PuerTradeService {

    private static PuerTradeService instance;

    private PuerTradeService() {

    }

    public static PuerTradeService getInstance() {
        if (instance == null) {
            instance = new PuerTradeService();
        }
        return instance;
    }

    public Flowable<HttpResult<PuerUserVo>> puerLogin(HashMap<String, String> params) {
        return this.getApi().puerLogin(params);
    }

    /**
     * 请求header拦截器
     */
    public class PuerHeaderInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request().newBuilder()
                    .addHeader("appVer", "3.2")
                    .addHeader("appType", "android")
                    .addHeader("reqfrom", "dtappandroid")
                    .addHeader("reqTime", "" + System.currentTimeMillis())
                    .build();

            return chain.proceed(request);
        }
    }

    public PuerTradeApi getApi() {
        return RetrofitHelper.getInstance().createApi(PuerTradeApi.class, PuerTradeApi.URL_PUER_TRADE, new PuerHeaderInterceptor(), false);
    }

}
