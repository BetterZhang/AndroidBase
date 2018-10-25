package com.betterzhang.puer.ui.activity;

import android.support.v7.widget.AppCompatEditText;
import com.betterzhang.common.base.BaseActivity;
import com.betterzhang.puer.R;
import com.betterzhang.puer.R2;
import com.betterzhang.puer.contract.LoginContract;
import com.betterzhang.puer.presenter.LoginPresenter;
import java.util.HashMap;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Android Studio.
 * Author : zhangzhongqiang
 * Email  : betterzhang.dev@gmail.com
 * Time   : 2017/08/01 下午 3:13
 * Desc   : 登录页面
 */

public class PuerLoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {

    @BindView(R2.id.et_account)
    AppCompatEditText et_account;
    @BindView(R2.id.et_password)
    AppCompatEditText et_password;

    private String mAccount;
    private String mPassword;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_login;
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected void initView() {
        super.initView();
        initToolbar("登录", true);
    }

    @OnClick(R2.id.btn_login)
    public void onClick() {
        mAccount = getAccount();
        mPassword = getPassword();
        HashMap<String, String> params = new HashMap<>();
        params.put("user_id", mAccount);
        params.put("password", mPassword);
        mPresenter.login(params);
    }

    @Override
    public String getAccount() {
        return et_account.getText().toString();
    }

    @Override
    public String getPassword() {
        return et_password.getText().toString();
    }

    @Override
    public void loginSuccess() {

    }

    @Override
    public void loginFailed() {

    }
}
