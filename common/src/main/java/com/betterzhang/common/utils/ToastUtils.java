package com.betterzhang.common.utils;

import android.widget.Toast;
import com.betterzhang.common.app.BaseApplication;

/**
 * Created by Android Studio.
 * Author : zhangzhongqiang
 * Email  : betterzhang.dev@gmail.com
 * Time   : 2019/10/23 16:56
 * Desc   : description
 */
public class ToastUtils {

    private static Toast mToast;

    public static void show(String msg) {
        if (null == mToast) {
            mToast = Toast.makeText(BaseApplication.getContext(), msg, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(msg);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    public static void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
        }
    }

}
