package com.betterzhang.common.base;

import android.support.annotation.UiThread;
import java.lang.ref.WeakReference;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Android Studio.
 * Author : zhangzhongqiang
 * Email  : betterzhang.dev@gmail.com
 * Time   : 2018/10/24 上午11:44
 * Desc   : BasePresenter
 */
public class BasePresenter<V extends IView> implements IPresenter<V> {

    protected WeakReference<V> mViewRef;

    protected CompositeDisposable mDisposables;

    @UiThread
    @Override
    public void attachView(V view) {
        mViewRef = new WeakReference<>(view);
    }

    @Override
    public void detachView() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
            dispose();
        }
    }

    @Override
    public void destroy() {

    }

    @UiThread
    public V getView() {
        return mViewRef == null ? null : mViewRef.get();
    }

    @UiThread
    public boolean isViewAttached() {
        return mViewRef != null && mViewRef.get() != null;
    }

    public void addSubscription(Disposable disposable) {
        if (disposable == null)
            return;
        if (mDisposables == null) {
            mDisposables = new CompositeDisposable();
        }
        mDisposables.add(disposable);
    }

    public void dispose() {
        if (mDisposables != null) {
            mDisposables.clear();
        }
    }

}
