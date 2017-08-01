package com.betterzhang.androidbase.ui.main;

import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.betterzhang.androidbase.R;
import com.betterzhang.androidbase.ui.market.HomeMarketFragment;
import com.betterzhang.androidbase.ui.personal.HomePersonalFragment;
import com.betterzhang.androidbase.ui.trade.HomeTradeFragment;
import com.betterzhang.common.ui.base.BaseActivity;
import butterknife.BindView;

/**
 * Created by Android Studio.
 * Author : zhangzhongqiang
 * Email  : betterzhang.dev@gmail.com
 * Time   : 2017/07/28 上午 9:52
 * Desc   : MainActivity
 */

public class MainActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener {

    @BindView(R.id.bottomBar)
    BottomNavigationBar bottomBar;
    @BindView(R.id.content)
    FrameLayout content;

    private HomePageFragment mPageFragment;
    private HomeMarketFragment mMarketFragment;
    private HomeTradeFragment mTradeFragment;
    private HomePersonalFragment mPersonalFragment;

    private long exitTime = 0;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        super.initView();
        bottomBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        bottomBar.addItem(new BottomNavigationItem(R.drawable.selector_tab_homepage, "首页"))
                 .setActiveColor(R.color.common_tab_text_selected)
                 .setInActiveColor(R.color.common_tab_text_unselected)
                 .addItem(new BottomNavigationItem(R.drawable.selector_tab_market, "行情"))
                 .setActiveColor(R.color.common_tab_text_selected)
                 .setInActiveColor(R.color.common_tab_text_unselected)
                 .addItem(new BottomNavigationItem(R.drawable.selector_tab_trade, "交易"))
                 .setActiveColor(R.color.common_tab_text_selected)
                 .setInActiveColor(R.color.common_tab_text_unselected)
                 .addItem(new BottomNavigationItem(R.drawable.selector_tab_personal, "我的"))
                 .setActiveColor(R.color.common_tab_text_selected)
                 .setInActiveColor(R.color.common_tab_text_unselected)
                 .setFirstSelectedPosition(0)
                 .initialise();

        setDefaultFragment();
    }

    // 设置默认的fragment
    private void setDefaultFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        mPageFragment = new HomePageFragment();
        transaction.replace(R.id.content, mPageFragment);
        transaction.commit();
    }

    @Override
    protected void addListener() {
        super.addListener();
        bottomBar.setTabSelectedListener(this);
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

    @Override
    public void onTabSelected(int position) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        switch (position) {
            case 0:
                if (mPageFragment == null) {
                    mPageFragment = new HomePageFragment();
                }
                transaction.replace(R.id.content, mPageFragment);
                break;
            case 1:
                if (mMarketFragment == null) {
                    mMarketFragment = new HomeMarketFragment();
                }
                transaction.replace(R.id.content, mMarketFragment);
                break;
            case 2:
                if (mTradeFragment == null) {
                    mTradeFragment = new HomeTradeFragment();
                }
                transaction.replace(R.id.content, mTradeFragment);
                break;
            case 3:
                if (mPersonalFragment == null) {
                    mPersonalFragment = new HomePersonalFragment();
                }
                transaction.replace(R.id.content, mPersonalFragment);
                break;
            default:
                break;
        }
        transaction.commit();
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }
}
