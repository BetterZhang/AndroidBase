package com.betterzhang.common.http;

/**
 * Created by Android Studio.
 * Author : zhangzhongqiang
 * Email  : betterzhang.dev@gmail.com
 * Time   : 2017/08/03 下午 4:31
 * Desc   : 错误处理
 */

public class ApiException extends RuntimeException {

    public static final String USER_NOT_EXIST = "1001";
    public static final String WRONG_PASSWORD = "1002";

    public ApiException(String detailMessage) {
        super(detailMessage);
    }

    public ApiException(String resultCode, String resultMsg) {
        this.getApiExceptionMessage(resultCode, resultMsg);
    }

    public ApiException(Throwable throwable) {
        super(throwable);
    }

    private String getApiExceptionMessage(String code, String msg) {
        String message = "";
        switch (code) {
            case USER_NOT_EXIST:
                message = "该用户不存在";
                break;
            case WRONG_PASSWORD:
                message = "密码错误";
                break;
            default:
                message = msg;
                break;
        }
        return message;
    }

}
