package com.betterzhang.androidbase.service;

import com.betterzhang.androidbase.domain.PuerUserVo;
import com.betterzhang.common.network.HttpResult;
import com.betterzhang.common.network.RetrofitHelper;
import com.betterzhang.common.network.RxHelper;
import com.betterzhang.common.network.SubscribeHelper;
import java.io.IOException;
import java.util.HashMap;
import io.reactivex.annotations.NonNull;
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

    public void puerLogin(HashMap<String, String> params) {
        PuerTradeApi api = RetrofitHelper.getInstance().createApi(PuerTradeApi.class, PuerTradeApi.URL_PUER_TRADE, new PuerHeaderInterceptor(), false);
        api.puerLogin(params).compose(RxHelper.rxSchedulerHelper())
                .subscribe(new SubscribeHelper<HttpResult<PuerUserVo>>() {
                    @Override
                    public void onNext(@NonNull HttpResult<PuerUserVo> result) {
                        super.onNext(result);
                    }
                });
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

}
