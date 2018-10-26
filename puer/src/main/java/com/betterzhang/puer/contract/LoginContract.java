package com.betterzhang.puer.contract;

import com.betterzhang.common.base.IPresenter;
import com.betterzhang.common.base.IView;
import java.util.HashMap;

/**
 * Created by Android Studio.
 * Author : zhangzhongqiang
 * Email  : betterzhang.dev@gmail.com
 * Time   : 2018/10/24 下午4:47
 * Desc   : 登录契约类
 */
public interface LoginContract {

    interface View extends IView {

        String getAccount();

        String getPassword();

        void loginSuccess();

        void loginFailed();

    }

    interface Presenter extends IPresenter<LoginContract.View> {

        void login(HashMap<String, String> params);

    }

}
