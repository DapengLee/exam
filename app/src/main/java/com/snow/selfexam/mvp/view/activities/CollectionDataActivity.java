package com.snow.selfexam.mvp.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.snow.selfexam.R;
import com.snow.selfexam.app.adapter.CollItemAdapter;
import com.snow.selfexam.app.bank.activity.ChoiceActivity;
import com.snow.selfexam.app.base.BaseActivity;
import com.snow.selfexam.app.utils.ToastUtil;
import com.snow.selfexam.mvp.modle.AllItemInfo;
import com.snow.selfexam.mvp.modle.PosDataBean;
import com.snow.selfexam.mvp.presenter.CollectionPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class CollectionDataActivity extends BaseActivity<CollectionPresenter> implements BaseQuickAdapter.OnRecyclerViewItemClickListener, View.OnClickListener {


    private ImageView iv_title_back;
    private String info;
    private PosDataBean infoTypeBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_collection_data);
        initView();
    }


    private RecyclerView all_recycler;
    private List<AllItemInfo> data;
    private CollItemAdapter collItemAdapter;


    private void initView() {
        data = new ArrayList<>();
        AllItemInfo infoSingle = new AllItemInfo("单选题");
        AllItemInfo info1Duo = new AllItemInfo("多选题");
        AllItemInfo infoTian = new AllItemInfo("填空题");
        AllItemInfo infoMingCi = new AllItemInfo("名词解释");
        AllItemInfo infoJianDa = new AllItemInfo("简答题");
        AllItemInfo infoLunShu = new AllItemInfo("论述题");
        AllItemInfo infoAnLi = new AllItemInfo("案例分析");

        data.add(infoSingle);
        data.add(info1Duo);
        data.add(infoTian);
        data.add(infoMingCi);
        data.add(infoJianDa);
        data.add(infoLunShu);
        data.add(infoAnLi);
        iv_title_back = findViewById(R.id.iv_title_back);
        iv_title_back.setVisibility(View.VISIBLE);
        iv_title_back.setOnClickListener(this);
        collItemAdapter = new CollItemAdapter(data);
        all_recycler = (RecyclerView) findViewById(R.id.all_recycler);
        TextView tv_title_name = (TextView) findViewById(R.id.tv_title_name);
        tv_title_name.setText("全面性练习");
        all_recycler.setLayoutManager(new LinearLayoutManager(this));
        all_recycler.setAdapter(collItemAdapter);
        collItemAdapter.setOnRecyclerViewItemClickListener(this);
    }

    // 3 接收粘性事件
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void EventBusName(PosDataBean infoTypeBean) {
        //  Toast.makeText(this, "ChoiceActivity（）" + infoTypeBean.toString(), Toast.LENGTH_SHORT).show();
        this.infoTypeBean = infoTypeBean;
    }

    @Override
    public void onItemClick(View view, int position) {
        ToastUtil.showToast(this, "进入" + data.get(position).getTitleName());
        if (data.get(position).getTitleName().equals("单选题")) {
            EventBus.getDefault().postSticky(new PosDataBean("2", "12", infoTypeBean.getExam(), infoTypeBean.getExamData(), true));
            startActivity(new Intent(this, ChoiceActivity.class));
        }
        if (data.get(position).getTitleName().equals("多选题")) {
            EventBus.getDefault().postSticky(new PosDataBean("3", "12", infoTypeBean.getExam(), infoTypeBean.getExamData(), true));
            startActivity(new Intent(this, ChoiceActivity.class));
        }
        if (data.get(position).getTitleName().equals("名词解释")) {
            EventBus.getDefault().postSticky(new PosDataBean("4", "12", infoTypeBean.getExam(), infoTypeBean.getExamData(), true));
            startActivity(new Intent(this, ChoiceActivity.class));
        }

        if (data.get(position).getTitleName().equals("填空题")) {
            EventBus.getDefault().postSticky(new PosDataBean("5", "12", infoTypeBean.getExam(), infoTypeBean.getExamData(), true));
            startActivity(new Intent(this, ChoiceActivity.class));
        }
        if (data.get(position).getTitleName().equals("简答题")) {
            EventBus.getDefault().postSticky(new PosDataBean("6", "12", infoTypeBean.getExam(), infoTypeBean.getExamData(), true));
            startActivity(new Intent(this, ChoiceActivity.class));
        }
        if (data.get(position).getTitleName().equals("论述题")) {
            EventBus.getDefault().postSticky(new PosDataBean("7", "12", infoTypeBean.getExam(), infoTypeBean.getExamData(), true));
            startActivity(new Intent(this, ChoiceActivity.class));
        }
        if (data.get(position).getTitleName().equals("案例分析")) {
            EventBus.getDefault().postSticky(new PosDataBean("8", "12", infoTypeBean.getExam(), infoTypeBean.getExamData(), true));
            startActivity(new Intent(this, ChoiceActivity.class));
        }
    }

    @Override
    public int setView(Bundle savedInstanceState) {
        return 0;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void setListener(Bundle savedInstanceState) {

    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View v) {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
