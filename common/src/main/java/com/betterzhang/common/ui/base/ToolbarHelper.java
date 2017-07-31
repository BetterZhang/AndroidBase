package com.betterzhang.common.ui.base;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.MenuRes;
import android.support.annotation.StyleRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.betterzhang.common.R;

/**
 * Created by Android Studio.
 * Author : zhangzhongqiang
 * Email  : betterzhang.dev@gmail.com
 * Time   : 2017/07/31 上午 10:50
 * Desc   : Toolbar工具类
 */

public class ToolbarHelper {

    private AppCompatActivity mActivity;
    private Toolbar mToolbar;
    private LinearLayout mCenterToolbarView;
    private View mUserView;
    private boolean bUserViewIsTitle = false;

    public ToolbarHelper(AppCompatActivity activity, View view) {
        mActivity = activity;
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        //mToolbar.setPadding(0, ScreenUtil.getStatusBarHeight(mActivity), 0, 0);
    }

    public ToolbarHelper(AppCompatActivity activity) {
        mActivity = activity;
        mToolbar = (Toolbar) mActivity.findViewById(R.id.toolbar);
        mToolbar.setContentInsetStartWithNavigation(0);
        //mToolbar.setPadding(0, ScreenUtil.getStatusBarHeight(mActivity), 0, 0);
    }

    public void initToolbar(View view) {
        mCenterToolbarView = (LinearLayout) mToolbar.findViewById(R.id.centerToolbarView);
        mCenterToolbarView.addView(view);
        mUserView = view;
    }

    public void initToolbar(String title) {
        initToolbar(createTitleView());
        setTitle(title);
    }

    public void initToolbar(String title, @ColorInt int resId) {
        initToolbar(createTitleView());
        setTitle(title, resId);
    }

    public void initToolbar(String[] title) {
        initToolbar(createTitleViewWithSure());
        setTitle(title);
    }

    public void removeView(View view) {
        mCenterToolbarView.removeView(view);
    }

    private View createTitleView() {
        LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_title, null);
        bUserViewIsTitle = true;

        return view;
    }

    private View createTitleViewWithSure() {
        LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_title_with_sure, null);
        bUserViewIsTitle = true;

        return view;
    }

    public void setTitle(String title) {
        if (bUserViewIsTitle) {
            TextView tv_title = (TextView) mUserView.findViewById(R.id.toolbar_title);
            tv_title.setText(title);
        }
    }

    public void setTitle(String title, @ColorInt int resId) {
        if (bUserViewIsTitle) {
            TextView tv_title = (TextView) mUserView.findViewById(R.id.toolbar_title);
            tv_title.setTextColor(resId);
            tv_title.setText(title);
        }
    }

    public void setTitle(String[] title) {
        if (bUserViewIsTitle) {
            TextView tv_title = (TextView) mUserView.findViewById(R.id.toolbar_title);
            TextView tv_title_with_sure = (TextView) mUserView.findViewById(R.id.toolbar_title_with_sure);
            tv_title.setText(title[0]);
            tv_title_with_sure.setText(title[1]);
        }
    }

    public void setBackNavigation(boolean hasBack, View.OnClickListener listener) {
        if (hasBack) {
            mToolbar.setNavigationIcon(R.drawable.ic_back_gray);
//            mActivity.setSupportActionBar(mToolbar);
//            mActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            mActivity.getSupportActionBar().setDisplayShowTitleEnabled(false);
            mToolbar.setNavigationOnClickListener(listener);
        } else {
//            mActivity.setSupportActionBar(mToolbar);
//            mActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//            mActivity.getSupportActionBar().setDisplayShowTitleEnabled(false);
            mToolbar.setNavigationIcon(null);
        }
    }

    public void setBackNavigationIcon(int resId, View.OnClickListener listener) {
        mToolbar.setNavigationIcon(resId);
        mToolbar.setNavigationOnClickListener(listener);
    }

    public void setBackNavigationIcon(int resId) {
        mToolbar.setNavigationIcon(resId);
    }

    public void setSingleMenu(String title, @DrawableRes int resId, final OnSingleMenuItemClickListener listener) {
        mToolbar.getMenu().clear();

        MenuItem item = mToolbar.getMenu().add(title);
        item.setIcon(resId);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                listener.onSingleMenuItemClick();
                return true;
            }
        });
    }

    public void setSingleMenu(String title, @DrawableRes int resId, @StyleRes int styleResId, final OnSingleMenuItemClickListener listener) {
        mToolbar.getMenu().clear();
        mToolbar.getContext().setTheme(styleResId);

        MenuItem item = mToolbar.getMenu().add(title);
        item.setIcon(resId);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                listener.onSingleMenuItemClick();
                return true;
            }
        });
    }

    public void setMenu(@MenuRes int resId, final Toolbar.OnMenuItemClickListener listener) {
        mToolbar.inflateMenu(resId);
        mToolbar.setOnMenuItemClickListener(listener);
    }

    public void setMenu(@MenuRes int resId, @StyleRes int styleResId, final Toolbar.OnMenuItemClickListener listener) {
        mToolbar.getMenu().clear();
        mToolbar.getContext().setTheme(styleResId);

        mToolbar.inflateMenu(resId);
        mToolbar.setOnMenuItemClickListener(listener);
    }

    public void clearMenu() {
        mToolbar.getMenu().clear();
    }

    public void setBackgroundColor(@ColorInt int color) {
        mToolbar.setBackgroundColor(color);
    }

    public interface OnSingleMenuItemClickListener {
        public void onSingleMenuItemClick();
    }

}
