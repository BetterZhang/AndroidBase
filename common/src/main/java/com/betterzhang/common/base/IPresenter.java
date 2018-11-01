package com.betterzhang.common.base;

import android.support.annotation.NonNull;
import android.support.annotation.UiThread;

/**
 * Created by Android Studio.
 * Author : zhangzhongqiang
 * Email  : betterzhang.dev@gmail.com
 * Time   : 2018/10/24 上午11:24
 * Desc   : IPresenter
 */
public interface IPresenter<V extends IView> {

    /**
     * 将 View 添加到当前 Presenter
     */
    @UiThread
    void attachView(@NonNull V view);

    /**
     * 将 View 从 Presenter 移除
     */
    @UiThread
    void detachView();

    /**
     * 销毁 V 实例
     */
    @UiThread
    void destroy();

}
