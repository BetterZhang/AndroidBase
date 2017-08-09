package com.betterzhang.androidbase.ui.login;

import android.support.v7.widget.AppCompatEditText;
import com.betterzhang.androidbase.R;
import com.betterzhang.androidbase.service.PuerTradeService;
import com.betterzhang.common.ui.base.BaseActivity;
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

public class LoginActivity extends BaseActivity {

    @BindView(R.id.et_account)
    AppCompatEditText et_account;
    @BindView(R.id.et_password)
    AppCompatEditText et_password;

    private String mAccount;
    private String mPassword;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        super.initView();
        initToolbar("登录", true);
    }

    @OnClick(R.id.btn_login)
    public void onClick() {
        mAccount = et_account.getText().toString();
        mPassword = et_password.getText().toString();
        HashMap<String, String> params = new HashMap<>();
        params.put("user_id", mAccount);
        params.put("password", mPassword);
        PuerTradeService.getInstance().puerLogin(params);
    }
}
