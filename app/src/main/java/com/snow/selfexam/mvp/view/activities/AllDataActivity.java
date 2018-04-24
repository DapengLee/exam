package com.snow.selfexam.mvp.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.snow.selfexam.R;
import com.snow.selfexam.app.adapter.AllItemAdapter;
import com.snow.selfexam.app.bank.activity.ChoiceActivity;
import com.snow.selfexam.app.utils.ToastUtil;
import com.snow.selfexam.mvp.modle.AllItemInfo;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class AllDataActivity extends AppCompatActivity implements BaseQuickAdapter.OnRecyclerViewItemClickListener {

    private RecyclerView all_recycler;
    private List<AllItemInfo> data;
    private AllItemAdapter allItemAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_data);
        initView();
    }

    private void initView() {
        data = new ArrayList<>();
        AllItemInfo infoSingle = new AllItemInfo("单选题");
        AllItemInfo info1Duo = new AllItemInfo("多选题");
        AllItemInfo infoMingCi = new AllItemInfo("名词解释");
        AllItemInfo infoJianDa = new AllItemInfo("简答题");
        AllItemInfo infoLunShu = new AllItemInfo("论述题");

        data.add(infoSingle);
        data.add(info1Duo);
        data.add(infoMingCi);
        data.add(infoJianDa);
        data.add(infoLunShu);
        allItemAdapter = new AllItemAdapter(data);
        all_recycler = (RecyclerView) findViewById(R.id.all_recycler);
        all_recycler.setLayoutManager(new LinearLayoutManager(this));
        all_recycler.setAdapter(allItemAdapter);
        allItemAdapter.setOnRecyclerViewItemClickListener(this);
    }

    @Override
    public void onItemClick(View view, int position) {
        ToastUtil.showToast(this, "点击了我" + data.get(position).getTitleName());
        if (data.get(position).getTitleName().equals("单选题")) {
            EventBus.getDefault().postSticky("2");
            startActivity(new Intent(this, ChoiceActivity.class));
        }
        if (data.get(position).getTitleName().equals("多选题")) {
            EventBus.getDefault().postSticky("3");
            startActivity(new Intent(this, ChoiceActivity.class));
        }
        if (data.get(position).getTitleName().equals("名词解释")) {
            EventBus.getDefault().postSticky("4");
            startActivity(new Intent(this, ChoiceActivity.class));
        }

        if (data.get(position).getTitleName().equals("填空题")) {
            EventBus.getDefault().postSticky("5");
            startActivity(new Intent(this, ChoiceActivity.class));
        }
        if (data.get(position).getTitleName().equals("简单题")) {
            EventBus.getDefault().postSticky("6");
            startActivity(new Intent(this, ChoiceActivity.class));
        }
        if (data.get(position).getTitleName().equals("论述题")) {
            EventBus.getDefault().postSticky("7");
            startActivity(new Intent(this, NounActivity.class));
        }
        if (data.get(position).getTitleName().equals("案例分析")) {
            EventBus.getDefault().postSticky("8");
            startActivity(new Intent(this, NounActivity.class));
        }
    }
}
