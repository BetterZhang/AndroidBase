package com.betterzhang.common.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.MenuRes;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Android Studio.
 * Author : zhangzhongqiang
 * Email  : betterzhang.dev@gmail.com
 * Time   : 2017/07/26 下午 2:17
 * Desc   : Fragment基类
 */

public abstract class BaseFragment<T extends IPresenter> extends Fragment implements IView {

    protected Context mContext;
    protected AppCompatActivity mActivity;
    protected Toast mToast;

    protected ToolbarHelper mToolbarHelper;

    protected T mPresenter;
    private Unbinder mUnbinder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (AppCompatActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(getLayoutResId(), null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);

        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }

        mUnbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        loadData(savedInstanceState);
        addListener();
    }

    /**
     * 设置页面布局layout
     * @return
     */
    protected abstract int getLayoutResId();

    /**
     * 创建Presenter实例
     * @return
     */
    protected abstract T createPresenter();

    /**
     * 绑定页面UI
     */
    protected void initView() {

    }

    /**
     * 加载数据
     */
    protected void loadData(@Nullable Bundle savedInstanceState) {

    }

    /**
     * 添加UI事件监听
     */
    protected void addListener() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
    }

    // ************** Toolbar Setting start **************

    public void initToolbar(View view, boolean hasBack) {
        mToolbarHelper = new ToolbarHelper(mActivity, getView());

        mToolbarHelper.initToolbar(view);
        setBackNavigation(hasBack);
    }

    public void initToolbar(String title, boolean hasBack) {
        mToolbarHelper = new ToolbarHelper(mActivity, getView());

        mToolbarHelper.initToolbar(title);
        setBackNavigation(hasBack);
    }

    public void initToolbar(String title, boolean hasBack, @ColorInt int resId) {
        mToolbarHelper = new ToolbarHelper(mActivity, getView());

        mToolbarHelper.initToolbar(title, resId);
        setBackNavigation(hasBack);
    }

    public void initToolbar(int resId, boolean hasBack) {
        initToolbar(getString(resId), hasBack);
    }

    public void initToolbar(int resId, boolean hasBack, @ColorInt int colorResID) {
        initToolbar(getString(resId), hasBack, colorResID);
    }

    public void setTitle(int resId) {
        if (resId != 0) {
            setTitle(getString(resId));
        }
    }

    public void setTitle(String title) {
        if (mToolbarHelper != null)
            mToolbarHelper.setTitle(title);
    }

    public void setBackNavigation(boolean hasBack) {
        if (mToolbarHelper != null)
            mToolbarHelper.setBackNavigation(hasBack, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mActivity.onBackPressed();
                }
            });
    }

    public void setRightNavigation(String str, @DrawableRes int resId, ToolbarHelper.OnSingleMenuItemClickListener listener) {
        if (mToolbarHelper != null)
            mToolbarHelper.setSingleMenu(str, resId, listener);
    }

    public void setRightNavigation(String str, @DrawableRes int resId, @StyleRes int _resId, ToolbarHelper.OnSingleMenuItemClickListener listener) {
        if (mToolbarHelper != null)
            mToolbarHelper.setSingleMenu(str, resId, _resId, listener);
    }

    public void setRightMultiNavigation(@MenuRes int resId, final Toolbar.OnMenuItemClickListener listener) {
        if (mToolbarHelper != null)
            mToolbarHelper.setMenu(resId, listener);
    }

    public void setRightMultiNavigation(@MenuRes int resId, @StyleRes int _resId, final Toolbar.OnMenuItemClickListener listener) {
        if (mToolbarHelper != null)
            mToolbarHelper.setMenu(resId, _resId, listener);
    }

    public void clearRightNavigation() {
        if (mToolbarHelper != null)
            mToolbarHelper.clearMenu();
    }

    // ************** Toolbar Setting end **************

    /**
     * 短暂显示Toast提示(来自res)
     *
     * @param resId
     */
    protected void showShortToast(final int resId) {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mToast == null) {
                    mToast = Toast.makeText(mContext, getResources().getString(resId), Toast.LENGTH_SHORT);
                } else {
                    mToast.setText(resId);
                }
                mToast.show();
            }
        });
    }

    /**
     * 短暂显示Toast提示(来自String)
     *
     * @param text
     */
    protected void showShortToast(final String text) {
        if (!TextUtils.isEmpty(text)) {
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mToast == null) {
                        mToast = Toast.makeText(mContext, text, Toast.LENGTH_SHORT);
                    } else {
                        mToast.setText(text);
                    }
                    mToast.show();
                }
            });
        }
    }

    /**
     * 长时间显示Toast提示(来自res)
     *
     * @param resId
     */
    protected void showLongToast(final int resId) {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mToast == null) {
                    mToast = Toast.makeText(mContext, getResources().getString(resId), Toast.LENGTH_LONG);
                } else {
                    mToast.setText(resId);
                }
                mToast.show();
            }
        });
    }

    /**
     * 长时间显示Toast提示(来自String)
     *
     * @param text
     */
    protected void showLongToast(final String text) {
        if (!TextUtils.isEmpty(text)) {
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mToast == null) {
                        mToast = Toast.makeText(mContext, text, Toast.LENGTH_LONG);
                    } else {
                        mToast.setText(text);
                    }
                    mToast.show();
                }
            });
        }
    }

    /**
     * Activity跳转（无参）
     *
     * @param cls
     */
    protected void startAnimActivity(Class<?> cls) {
        this.startAnimActivity(cls, null);
    }

    /**
     * Activity跳转（有参）
     *
     * @param cls
     * @param bundle
     */
    protected void startAnimActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(mContext, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * Activity跳转 ForResult（无参）
     *
     * @param cls
     * @param requestCode
     */
    protected void startAnimActivityForResult(Class<?> cls, int requestCode) {
        this.startAnimActivityForResult(cls, null, requestCode);
    }

    /**
     * Activity跳转 ForResult（有参）
     *
     * @param cls
     * @param bundle
     * @param requestCode
     */
    protected void startAnimActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(mContext, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showErrorMsg(String msg) {
        showShortToast(msg);
    }
}
