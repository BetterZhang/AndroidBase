package com.betterzhang.puer.service;

import com.betterzhang.common.http.HttpResult;
import com.betterzhang.puer.model.PuerUserVo;
import java.util.HashMap;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Android Studio.
 * Author : zhangzhongqiang
 * Email  : betterzhang.dev@gmail.com
 * Time   : 2017/08/09 下午 1:26
 * Desc   : 普洱交易API
 */

public interface PuerTradeApi {

    String URL_PUER_TRADE = "https://appclient.jme.com:8093";

    /**
     * 普洱登录接口
     * @param params
     * @return
     */
    @POST("/puerapi/v2/account/loginv2")
    Observable<HttpResult<PuerUserVo>> puerLogin(@Body HashMap<String, String> params);

}
