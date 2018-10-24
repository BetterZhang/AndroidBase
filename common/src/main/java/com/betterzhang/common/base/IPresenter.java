package com.betterzhang.common.base;

/**
 * Created by Android Studio.
 * Author : zhangzhongqiang
 * Email  : betterzhang.dev@gmail.com
 * Time   : 2018/10/24 上午11:24
 * Desc   : IPresenter
 */
public interface IPresenter<V extends IView> {

    void attachView(V view);

    void detachView();

}
