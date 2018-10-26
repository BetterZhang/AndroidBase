package com.betterzhang.common.http;

/**
 * Created by Android Studio.
 * Author : zhangzhongqiang
 * Email  : betterzhang.dev@gmail.com
 * Time   : 2017/08/03 下午 4:31
 * Desc   : 错误处理
 */

public class ApiException extends Exception {

    private String code;

    public ApiException(String msg) {
        super(msg);
    }

    public ApiException(String code, String msg) {
        super(msg);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
