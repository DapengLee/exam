package com.snow.selfexam.mvp.view.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.snow.selfexam.R;
import com.snow.selfexam.app.adapter.TrueItemAdapter;
import com.snow.selfexam.mvp.modle.TrueItemInfo;

import java.util.ArrayList;
import java.util.List;

public class TruePraActivity extends AppCompatActivity {


    private RecyclerView true_recycler;
    private List<TrueItemInfo> data;
    private TrueItemAdapter trueItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_true_pra);
        initView();
    }

    private void initView() {
        data = new ArrayList<>();
        data.add(new TrueItemInfo("2017年真题试卷"));
        trueItemAdapter = new TrueItemAdapter(data);
        true_recycler = (RecyclerView) findViewById(R.id.true_recycler);
        true_recycler.setLayoutManager(new LinearLayoutManager(this));
        true_recycler.setAdapter(new TrueItemAdapter(data));
        trueItemAdapter.openLoadMore(true);
    }
}
