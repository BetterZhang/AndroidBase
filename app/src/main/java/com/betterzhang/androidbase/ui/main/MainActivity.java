package com.betterzhang.androidbase.ui.main;

import android.widget.FrameLayout;
import com.betterzhang.androidbase.R;
import com.betterzhang.common.ui.base.BaseActivity;
import com.roughike.bottombar.BottomBar;
import butterknife.BindView;

/**
 * Created by Android Studio.
 * Author : zhangzhongqiang
 * Email  : betterzhang.dev@gmail.com
 * Time   : 2017/07/28 上午 9:52
 * Desc   : MainActivity
 */

public class MainActivity extends BaseActivity {

    @BindView(R.id.bottomBar)
    BottomBar bottomBar;
    @BindView(R.id.content)
    FrameLayout content;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void addListener() {
        super.addListener();
        bottomBar.setOnTabSelectListener(tabId -> {
            String msg = "";
            switch (tabId) {
                case R.id.tab_home_page:
                    msg = "首页";
                    break;
                case R.id.tab_home_market:
                    msg = "行情";
                    break;
                case R.id.tab_home_trade:
                    msg = "交易";
                    break;
                case R.id.tab_home_personal:
                    msg = "我的";
                    break;
                default:
                    break;
            }
            showShortToast(msg);
        });
    }
}
