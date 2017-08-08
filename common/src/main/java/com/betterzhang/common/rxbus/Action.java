package com.betterzhang.common.rxbus;

/**
 * Created by Android Studio.
 * Author : zhangzhongqiang
 * Email  : betterzhang.dev@gmail.com
 * Time   : 2017/08/08 下午 3:47
 * Desc   : rxbus封装对象
 */

public class Action {

    public int code;
    public Object data;

    public Action(int code, Object data) {
        this.code = code;
        this.data = data;
    }

}
