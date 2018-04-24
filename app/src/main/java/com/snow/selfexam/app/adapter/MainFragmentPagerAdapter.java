package com.snow.selfexam.app.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.snow.selfexam.app.factory.FragmentFactory;

/**
 * Created by Administrator on 2017/9/9 0009.
 */
public class MainFragmentPagerAdapter  extends FragmentPagerAdapter {
    public MainFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Fragment getItem(int position) {
        return FragmentFactory.createFragment(position);
    }
}
