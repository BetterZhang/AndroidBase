package com.betterzhang.common.utils;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;
import com.bumptech.glide.Glide;

/**
 * Created by Android Studio.
 * Author : zhangzhongqiang
 * Email  : betterzhang.dev@gmail.com
 * Time   : 2017/07/31 上午 9:52
 * Desc   : 图片加载工具类，统一适配（方便换库，方便管理）
 */

public class ImageLoader {

    public static void load(Context context, @DrawableRes int imageRes, ImageView view) {
        Glide.with(context).load(imageRes).into(view);
    }

    public static void clear(Context context, ImageView view) {
        Glide.with(context).clear(view);
    }

}
