package com.snow.selfexam.mvp.view.activities;

import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.snow.selfexam.R;
import com.snow.selfexam.app.base.BaseActivity;
import com.snow.selfexam.app.factory.FragmentFactory;
import com.snow.selfexam.mvp.presenter.MainPresenter;
import com.snow.selfexam.mvp.view.weight.NoScrollViewPager;

import org.greenrobot.eventbus.EventBus;


public class MainActivity extends BaseActivity<MainPresenter> {

    //禁止滑动的ViewPager
    private NoScrollViewPager homeViewpager;
    //切换的Button
    private RadioGroup homeRadioGroup;

    RadioButton rba, rbb, rbd;
    private TextView tv_title_name;
    public static final int SECOND_ACTIVITY = 0;
    private String temp;


    /**
     * 加载布局的界面
     *
     * @param savedInstanceState
     * @return
     */
    @Override
    public int setView(Bundle savedInstanceState) {
        // 从savedInstanceState中恢复数据, 如果没有数据需要恢复savedInstanceState为null
        if (savedInstanceState != null) {
            temp = savedInstanceState.getString("temp");
            System.out.println("onCreate: temp = " + temp);
        }
        return R.layout.activity_main;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        homeViewpager = (NoScrollViewPager) findViewById(R.id.home_viewpager);
        homeRadioGroup = (RadioGroup) findViewById(R.id.home_radiogroup);
        rbb = (RadioButton) findViewById(R.id.rb_learn);
        rbd = (RadioButton) findViewById(R.id.rb_more);
        rba = (RadioButton) findViewById(R.id.rb_home);
        tv_title_name = findViewById(R.id.tv_title_name);
        tv_title_name.setText("学习中心");
        rbb.setChecked(true);
        homeViewpager.setScanScroll(false);
    }
    @Override
    public void onResume() {
        super.onResume();
        temp = "xing";
        System.out.println("onResume: temp = " + temp);
        /*// 切换屏幕方向会导致activity的摧毁和重建
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            System.out.println("屏幕切换");
        }*/
    }

    // 将数据保存到outState对象中, 该对象会在重建activity时传递给onCreate方法
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("temp", temp);
    }




    /**
     * 设置监听事件
     *
     * @param savedInstanceState
     */
    @Override
    public void setListener(Bundle savedInstanceState) {
        homeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_home:
                        homeViewpager.setCurrentItem(FragmentFactory.FRAGMENT_HOME);
                        tv_title_name.setText("首页");
                        break;
                    case R.id.rb_learn:
                        homeViewpager.setCurrentItem(FragmentFactory.FRAGMENT_LEARN);
                        tv_title_name.setText("学习中心");
                        // }
                        break;
                    case R.id.rb_more:
                        homeViewpager.setCurrentItem(FragmentFactory.FRAGMENT_MORE);
                        tv_title_name.setText("个人中心");
                        break;
                }
            }
        });
    }

    /**
     * 加载数据的页面
     *
     * @param savedInstanceState
     */
    @Override
    public void initData(Bundle savedInstanceState) {
        mPresenter = new MainPresenter(this);
        mPresenter.setAdapter(homeViewpager);
        homeViewpager.setCurrentItem(1);
    }

}