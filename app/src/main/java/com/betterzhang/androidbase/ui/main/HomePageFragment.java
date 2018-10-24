package com.betterzhang.androidbase.ui.main;

import com.betterzhang.androidbase.R;
import com.betterzhang.common.base.BaseFragment;
import com.betterzhang.common.base.IPresenter;

/**
 * Created by Android Studio.
 * Author : zhangzhongqiang
 * Email  : betterzhang.dev@gmail.com
 * Time   : 2017/08/01 下午 2:37
 * Desc   : "主页" Tab
 */

public class HomePageFragment extends BaseFragment {

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_home_page;
    }

    @Override
    protected IPresenter createPresenter() {
        return null;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
