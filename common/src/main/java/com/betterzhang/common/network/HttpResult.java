package com.betterzhang.common.network;

import com.google.gson.Gson;

/**
 * Created by Android Studio.
 * Author : zhangzhongqiang
 * Email  : betterzhang.dev@gmail.com
 * Time   : 2017/08/03 下午 4:41
 * Desc   : 网络请求返回数据
 */

public class HttpResult<T> {

    // 返回的状态码
    private String code;
    // 返回的消息
    private String msg;
    // 返回的数据体
    private T body;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public String getBodyToString() {
        return new Gson().toJson(body);
    }

}
