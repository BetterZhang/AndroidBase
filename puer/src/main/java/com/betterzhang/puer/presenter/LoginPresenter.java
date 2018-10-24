package com.betterzhang.puer.presenter;

import com.betterzhang.common.base.BasePresenter;
import com.betterzhang.puer.contract.LoginContract;
import com.betterzhang.puer.service.PuerTradeService;
import java.util.HashMap;

/**
 * Created by Android Studio.
 * Author : zhangzhongqiang
 * Email  : betterzhang.dev@gmail.com
 * Time   : 2018/10/24 下午4:52
 * Desc   : LoginPresenter
 */
public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {

    @Override
    public void login(HashMap<String, String> params) {
        PuerTradeService.getInstance().puerLogin(params);
    }

}
