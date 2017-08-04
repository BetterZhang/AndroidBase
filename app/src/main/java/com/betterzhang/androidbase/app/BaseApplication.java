package com.betterzhang.androidbase.app;

import android.app.Application;
import android.content.Context;

/**
 * Created by Android Studio.
 * Author : zhangzhongqiang
 * Email  : betterzhang.dev@gmail.com
 * Time   : 2017/08/04 下午 1:41
 * Desc   : BaseApplication
 */

public class BaseApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this.getApplicationContext();
    }

    public static Context getCurrentAppContext() {
        return mContext;
    }
}
