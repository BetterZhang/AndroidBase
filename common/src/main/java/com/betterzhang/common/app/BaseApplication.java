package com.betterzhang.common.app;

import android.app.Application;
import android.content.Context;
import com.betterzhang.common.utils.AppUtils;

/**
 * Created by Android Studio.
 * Author : zhangzhongqiang
 * Email  : betterzhang.dev@gmail.com
 * Time   : 2017/08/08 下午 4:19
 * Desc   : BaseApplication
 */

public class BaseApplication extends Application {

    private static BaseApplication instance;
    private static Context context;

    {
        instance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AppUtils.syncIsDebug(getApplicationContext());
    }

    public static BaseApplication getInstance() {
        return instance;
    }

    public static Context getContext() {
        return instance.getApplicationContext();
    }

}
