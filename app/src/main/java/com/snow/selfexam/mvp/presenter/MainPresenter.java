package com.snow.selfexam.mvp.presenter;

import com.snow.selfexam.app.adapter.MainFragmentPagerAdapter;
import com.snow.selfexam.app.base.BasePresenter;
import com.snow.selfexam.mvp.view.activities.MainActivity;
import com.snow.selfexam.mvp.view.weight.NoScrollViewPager;

/**
 * Created by Administrator on 2017/9/9 0009.
 */

public class MainPresenter  extends BasePresenter {

    private MainActivity mainActivity;
    //切换的adapter
    private MainFragmentPagerAdapter fragmentPagerAdapter;

    public MainPresenter(MainActivity mainActivity) {
        this.mainActivity=mainActivity;
    }

    //装配数据的adapter
    public void setAdapter(NoScrollViewPager homeViewpager) {
        fragmentPagerAdapter = new MainFragmentPagerAdapter(mainActivity.getSupportFragmentManager());
        homeViewpager.setAdapter(fragmentPagerAdapter);
        homeViewpager.setOffscreenPageLimit(2);
    }
}
