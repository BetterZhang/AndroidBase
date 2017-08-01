package com.betterzhang.androidbase.ui.main;

import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
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

    private long exitTime = 0;

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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            // 判断是否在两秒之内连续点击返回键，是则退出，否则不退出
            if (System.currentTimeMillis() - exitTime > 2000) {
                Snackbar.make(content, getString(R.string.text_exit_app), Snackbar.LENGTH_SHORT)
                        .setAction(getString(R.string.text_cancel), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                exitTime = 0;
                            }
                        })
                        .setActionTextColor(ContextCompat.getColor(this, R.color.common_tab_text_selected))
                        .show();
                // 将系统当前的时间赋值给exitTime
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.exit(0);
                    }
                }.start();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
