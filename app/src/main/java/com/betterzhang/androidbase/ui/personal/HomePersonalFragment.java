package com.betterzhang.androidbase.ui.personal;

import com.betterzhang.androidbase.R;
import com.betterzhang.common.base.BaseFragment;
import com.betterzhang.common.base.IPresenter;
import com.betterzhang.puer.ui.activity.PuerLoginActivity;
import butterknife.OnClick;

/**
 * Created by Android Studio.
 * Author : zhangzhongqiang
 * Email  : betterzhang.dev@gmail.com
 * Time   : 2017/08/01 下午 2:39
 * Desc   : "我的" Tab
 */

public class HomePersonalFragment extends BaseFragment {

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_home_personal;
    }

    @Override
    protected IPresenter createPresenter() {
        return null;
    }

    @OnClick(R.id.btn)
    public void onClick() {
        startAnimActivity(PuerLoginActivity.class);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
