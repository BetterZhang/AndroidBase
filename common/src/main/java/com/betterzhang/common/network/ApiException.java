package com.betterzhang.common.network;

/**
 * Created by Android Studio.
 * Author : zhangzhongqiang
 * Email  : betterzhang.dev@gmail.com
 * Time   : 2017/08/03 下午 4:31
 * Desc   : 错误处理
 */

public class ApiException extends RuntimeException {

    public static final int USER_NOT_EXIST = 100;
    public static final int WRONG_PASSWORD = 101;

    public ApiException(String detailMessage) {
        super(detailMessage);
    }

    public ApiException(int resultCode) {
        this.getApiExceptionMessage(resultCode);
    }

    private String getApiExceptionMessage(int code) {
        String message = "";
        switch (code) {
            case USER_NOT_EXIST:
                message = "该用户不存在";
                break;
            case WRONG_PASSWORD:
                message = "密码错误";
                break;
            default:
                message = "未知错误";
                break;
        }
        return message;
    }

}
