package com.snow.selfexam.mvp.view.fragments;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;
import com.snow.selfexam.R;
import com.snow.selfexam.app.adapter.HomeItemAdapter;
import com.snow.selfexam.app.adapter.TopAdapter;
import com.snow.selfexam.app.base.BaseFragment;
import com.snow.selfexam.app.utils.UIUtils;
import com.snow.selfexam.mvp.modle.HomeItemInfo;
import com.snow.selfexam.mvp.presenter.HomePresenter;
import com.zanlabs.widget.infiniteviewpager.InfiniteViewPager;
import com.zanlabs.widget.infiniteviewpager.indicator.UnderlinePageIndicator;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends BaseFragment<HomePresenter> {

    private RecyclerView mHomeRecycler;
    private InfiniteViewPager mHomeViewpager;
    private UnderlinePageIndicator mHomeIndicator;
    private RecyclerViewHeader mHomeHeader;
    private SwipeRefreshLayout mHomeRefresh;
    private HomeItemAdapter mAdapter;
    private List<HomeItemInfo> mAppList;
    private List<Integer> banner_img;


    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(UIUtils.getContext(), R.layout.fragment_home, null);
        mHomeRecycler = view.findViewById(R.id.home_recycler);
        mHomeIndicator = view.findViewById(R.id.home_indicator);
        mHomeHeader = view.findViewById(R.id.home_header);
        mHomeViewpager = view.findViewById(R.id.home_viewpager);
        return view;
    }

    @Override
    public void initVariables() {
        banner_img = new ArrayList<>();
        HomePresenter homePresenter = new HomePresenter(this);
        mAppList = homePresenter.getAppList();
        mHomeRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new HomeItemAdapter(mAppList);
        mHomeRecycler.setAdapter(mAdapter);

    }


    @Override
    public void initData(Bundle savedInstanceState) {
        //  banner_img = Arrays.asList(Constants.BANNER_IMGS);
//        imageView.setImageResource(R.mipmap.banner_a);
        banner_img.add(R.mipmap.banner_a);
        banner_img.add(R.mipmap.banner_b);
        banner_img.add(R.mipmap.banner_c);

        mHomeHeader.attachTo(mHomeRecycler, true);
        TopAdapter adapter = new TopAdapter(getActivity(), banner_img);
        mHomeViewpager.setAdapter(adapter);
        mHomeViewpager.setAutoScrollTime(3000);
        mHomeViewpager.startAutoScroll();
        mHomeIndicator.setViewPager(mHomeViewpager);
    }

    @Override
    public void setData(Object data) {

    }
}
