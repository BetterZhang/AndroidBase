package com.betterzhang.common.ui.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Android Studio.
 * Author : zhangzhongqiang
 * Email  : betterzhang.dev@gmail.com
 * Time   : 2017/07/26 下午 2:04
 * Desc   : Activity基类
 */

public abstract class BaseActivity extends AppCompatActivity {

    public static final int INSTANT_IN = 0;
    public static final int INSTANT_OUT = 0;

    protected Context mContext;
    protected Toast mToast;
    protected ProgressDialog mDialog;
    protected ToolbarHelper mToolbarHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        overridePendingTransition(INSTANT_IN, INSTANT_OUT);
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        mContext = this;

        mDialog = new ProgressDialog(this);
        mDialog.setIndeterminate(true);
        mDialog.setMessage("请稍后...");
        mDialog.setCanceledOnTouchOutside(true);

        initView();
        loadData(savedInstanceState);
        addListener();
    }

    /**
     * 设置页面布局layout
     *
     * @return
     */
    protected abstract int getLayoutResId();

    /**
     * 绑定页面UI
     */
    protected void initView() {

    }

    /**
     * 加载数据
     */
    protected void loadData(Bundle savedInstanceState) {

    }

    /**
     * 添加UI事件监听
     */
    protected void addListener() {

    }

    // ************** Toolbar Setting start **************

    public void initToolbar(View view, boolean hasBack) {
        mToolbarHelper = new ToolbarHelper(this);

        mToolbarHelper.initToolbar(view);
        setBackNavigation(hasBack);
    }

    public void initToolbar(String title, boolean hasBack) {
        mToolbarHelper = new ToolbarHelper(this);

        mToolbarHelper.initToolbar(title);
        setBackNavigation(hasBack);
    }

    public void initToolbar(String title, boolean hasBack, @ColorInt int resId) {
        mToolbarHelper = new ToolbarHelper(this);

        mToolbarHelper.initToolbar(title, resId);
        setBackNavigation(hasBack);
    }

    public void initToolbar(String[] titles, boolean hasBack) {
        mToolbarHelper = new ToolbarHelper(this);

        setBackNavigation(hasBack);
        mToolbarHelper.initToolbar(titles);
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
                    onBackPressed();
                }
            });
    }

    public void setBackNavigation(@DrawableRes int drawableResId, View.OnClickListener listener) {
        if (mToolbarHelper != null)
            mToolbarHelper.setBackNavigationIcon(drawableResId, listener);
    }

    public void setRightNavigation(String str, @DrawableRes int resId, @StyleRes int _resId, ToolbarHelper.OnSingleMenuItemClickListener listener) {
        if (mToolbarHelper != null)
            mToolbarHelper.setSingleMenu(str, resId, _resId, listener);
    }

    public void setRightNavigation(String str, @DrawableRes int resId, ToolbarHelper.OnSingleMenuItemClickListener listener) {
        if (mToolbarHelper != null)
            mToolbarHelper.setSingleMenu(str, resId, listener);
    }

    // ************** Toolbar Setting end **************

    /**
     * 短暂显示Toast提示(来自res)
     *
     * @param resId
     */
    protected void showShortToast(final int resId) {
        runOnUiThread(new Runnable() {
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
            runOnUiThread(new Runnable() {
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
        runOnUiThread(new Runnable() {
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
            runOnUiThread(new Runnable() {
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

    /**
     * 是否显示加载框
     *
     * @param show
     */
    public void showProgress(boolean show) {
        if (show) {
            mDialog.show();
        } else {
            mDialog.dismiss();
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(INSTANT_IN, INSTANT_OUT);
    }
}
