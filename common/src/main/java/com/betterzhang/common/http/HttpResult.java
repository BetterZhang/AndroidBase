package com.betterzhang.common.http;

import com.google.gson.Gson;

/**
 * Created by Android Studio.
 * Author : zhangzhongqiang
 * Email  : betterzhang.dev@gmail.com
 * Time   : 2017/08/03 下午 4:41
 * Desc   : 网络请求返回数据
 */

public class HttpResult<T> {

    public static final String SUCCESS_CODE = "0";

    // 响应数据头
    private Head head;
    // 返回的数据体
    private T body;

    public Head getHead() {
        return head;
    }

    public void setHead(Head head) {
        this.head = head;
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

    public boolean isSuccess() {
        if (getHead().getCode().equals(SUCCESS_CODE))
            return true;
        else
            return false;
    }

}
