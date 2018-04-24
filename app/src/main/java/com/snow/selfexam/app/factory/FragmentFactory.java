package com.snow.selfexam.app.factory;

import android.support.v4.app.Fragment;


import com.snow.selfexam.app.base.BaseFragment;

import com.snow.selfexam.app.utils.LogUtils;
import com.snow.selfexam.mvp.view.fragments.HomeFragment;
import com.snow.selfexam.mvp.view.fragments.MineFragment;
import com.snow.selfexam.mvp.view.fragments.LearnFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by amew_000 on 2016/12/8.
 */
public class FragmentFactory {

    public static final int FRAGMENT_HOME = 0;
    public static final int FRAGMENT_LEARN= 1;
    public static final int FRAGMENT_MORE = 2;
    //Fragment的缓存
    public static Map<Integer,BaseFragment> mCacheFragmentList = new HashMap<>();

    public static Fragment createFragment(int position) {
        BaseFragment fragment = null;
        if(mCacheFragmentList.containsKey(position)){   //优先从缓存里面拿
            fragment = mCacheFragmentList.get(position);
            return fragment;
        }
        switch (position){
            case FRAGMENT_HOME:
                fragment = new HomeFragment();
                LogUtils.e("创建了HomeFragment");
                break;
            case FRAGMENT_LEARN:
                fragment = new LearnFragment();
                LogUtils.e("创建了LearnFragment");
                break;
            case FRAGMENT_MORE:
                fragment = new MineFragment();
                LogUtils.e("创建了MineFragment");
                break;
        }
        mCacheFragmentList.put(position,fragment);
        return fragment;
    }
}
