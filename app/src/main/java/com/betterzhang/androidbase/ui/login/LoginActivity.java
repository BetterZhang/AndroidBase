package com.betterzhang.androidbase.ui.login;

import com.betterzhang.androidbase.R;
import com.betterzhang.common.ui.base.BaseActivity;

/**
 * Created by Android Studio.
 * Author : zhangzhongqiang
 * Email  : betterzhang.dev@gmail.com
 * Time   : 2017/08/01 下午 3:13
 * Desc   : 登录页面
 */

public class LoginActivity extends BaseActivity {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        super.initView();
        initToolbar("登录", true);
    }
}
