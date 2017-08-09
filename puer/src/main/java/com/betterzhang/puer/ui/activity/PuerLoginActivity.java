package com.betterzhang.puer.ui.activity;

import android.support.v7.widget.AppCompatEditText;
import com.betterzhang.puer.R2;
import com.betterzhang.common.ui.base.BaseActivity;
import com.betterzhang.puer.R;
import com.betterzhang.puer.service.PuerTradeService;
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

public class PuerLoginActivity extends BaseActivity {

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
    protected void initView() {
        super.initView();
        initToolbar("登录", true);
    }

    @OnClick(R2.id.btn_login)
    public void onClick() {
        mAccount = et_account.getText().toString();
        mPassword = et_password.getText().toString();
        HashMap<String, String> params = new HashMap<>();
        params.put("user_id", mAccount);
        params.put("password", mPassword);
        PuerTradeService.getInstance().puerLogin(params);
    }
}
