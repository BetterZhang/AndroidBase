package com.betterzhang.androidbase.ui.trade;

import com.betterzhang.androidbase.R;
import com.betterzhang.common.base.BaseFragment;
import com.betterzhang.common.base.IPresenter;

/**
 * Created by Android Studio.
 * Author : zhangzhongqiang
 * Email  : betterzhang.dev@gmail.com
 * Time   : 2017/08/01 下午 2:40
 * Desc   : "交易" Tab
 */

public class HomeTradeFragment extends BaseFragment {

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_home_trade;
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
