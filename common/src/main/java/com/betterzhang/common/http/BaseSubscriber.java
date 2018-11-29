package com.betterzhang.common.http;

import android.text.TextUtils;
import com.betterzhang.common.base.IView;
import com.google.gson.JsonParseException;
import org.json.JSONException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;
import io.reactivex.subscribers.ResourceSubscriber;
import retrofit2.HttpException;

/**
 * Created by Android Studio.
 * Author : zhangzhongqiang
 * Email  : betterzhang.dev@gmail.com
 * Time   : 2018/10/26 下午4:03
 * Desc   : BaseSubscriber
 */
public abstract class BaseSubscriber<T> extends ResourceSubscriber<T> {

    private IView mView;
    private String mErrorMsg;

    protected BaseSubscriber(IView view) {
        this.mView = view;
    }

    protected BaseSubscriber(IView view, String errorMsg) {
        this.mView = view;
        this.mErrorMsg = errorMsg;
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(Throwable t) {
        if (mView == null)
            return;

        String msg = "";
        if (!TextUtils.isEmpty(mErrorMsg)) {
            msg = mErrorMsg;
        } else if (t instanceof ApiException) {
            msg = t.getMessage();
        } else if (t instanceof UnknownHostException) {
            msg = "网络异常，请检查您的网络状态";
        } else if (t instanceof ConnectException) {
            msg = "网络连接异常，请检查您的网络状态";
        } else if (t instanceof SocketTimeoutException) {
            msg = "网络连接超时，请检查您的网络状态，稍后重试";
        } else if (t instanceof HttpException) {
            msg = "请检查您的网络连接，稍后重试";
        } else if (t instanceof ParseException
                || t instanceof JSONException
                || t instanceof JsonParseException) {
            msg = "解析失败";
        } else {
            msg = "未知错误";
        }
        mView.showErrorMsg(msg);
    }
}
