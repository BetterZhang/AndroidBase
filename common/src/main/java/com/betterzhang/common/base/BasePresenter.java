package com.betterzhang.common.base;

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

    protected WeakReference<V> mView;

    CompositeDisposable mDisposables;

    public BasePresenter() {

    }

    public BasePresenter(V rootView) {
        this.mView = new WeakReference<>(rootView);
    }

    @Override
    public void attachView(V view) {
        if (mView == null) {
            mView = new WeakReference<>(view);
        }
    }

    @Override
    public void detachView() {
        mView.clear();
        mView = null;
        dispose();
    }

    public V getView() {
        if (mView != null) {
            return mView.get();
        }
        return null;
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
