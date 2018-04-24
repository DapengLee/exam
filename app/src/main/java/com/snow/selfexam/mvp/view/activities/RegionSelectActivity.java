package com.snow.selfexam.mvp.view.activities;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.snow.selfexam.R;
import com.snow.selfexam.app.adapter.RegionAdapter;
import com.snow.selfexam.app.base.BaseActivity;
import com.snow.selfexam.app.communicate.CommCallBack;
import com.snow.selfexam.app.communicate.NormalException;
import com.snow.selfexam.app.communicate.requestbean.SubmitDataBeanRequest;
import com.snow.selfexam.app.db.RegionDao;
import com.snow.selfexam.app.db.UserInfoDBHelper;
import com.snow.selfexam.app.utils.LogUtils;
import com.snow.selfexam.mvp.modle.RegionModel;
import com.snow.selfexam.mvp.modle.UserInfoBean;
import com.snow.selfexam.mvp.presenter.RegionDaoPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class RegionSelectActivity extends BaseActivity<RegionDaoPresenter> implements BaseQuickAdapter.OnRecyclerViewItemClickListener, View.OnClickListener, CommCallBack {

    public static final String REGION_PROVINCE = "region_province";
    public static final String REGION_CITY = "region_city";
    public static final String REGION_AREA = "region_area";
    private static final int RESULT_CODE_SUCCESS = 200;


    private RecyclerView mRecyclerView;
    private RegionAdapter mAdapter;
    private RegionDao mRegionDao;

    private List<RegionModel> mList;

    private List<RegionModel> mProvinceList;
    private List<RegionModel> mCityList;
    private List<RegionModel> mAreaList;
    private int state = 0;

    private String mProvince;
    private String mCity;
    private String mArea;

    private TextView titleTextView;
    private ImageView backImageView;

    private RegionDaoPresenter morePresenter;
    private UserInfoBean loginBeanResponse;

    private String infoPhone;
    private String specId;

    @Override
    public void onItemClick(View view, int position) {
        RegionModel region = mAdapter.getItem(position);

        if (state == 0) {
            setTitle("选择专业");
            titleTextView.setText("选择专业");
            mCityList = mRegionDao.loadCityList(region.getId());
            backImageView.setVisibility(View.VISIBLE);
            mList.clear();
            mAdapter.addData(mCityList);

            mProvince = region.getName();
            state++;
        } else if (state == 1) {
            setTitle("选择科目");
            titleTextView.setText("选择科目");
            mAreaList = mRegionDao.loadCountyList(region.getId());
            mCity = region.getName();
            backImageView.setVisibility(View.VISIBLE);
            if (mAreaList.size() == 0) {
                //防止有的城市没有县级
                //  finishSelect();

            } else {
                mList.clear();
                mAdapter.addData(mAreaList);

                state++;
            }


        } else if (state == 2) {
            mArea = region.getName();
            //  state++;

            //   finishSelect();
            Toast.makeText(this, "选择了" + mProvince + mCity + mArea, Toast.LENGTH_SHORT).show();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("是否确定专业选择：");
            builder.setMessage("您寻选择的是?" + mProvince + mCity + mArea+"选择之后不能换专业 。");
            builder.setIcon(R.mipmap.ic_launcher_round);
            //点击对话框以外的区域是否让对话框消失
            builder.setCancelable(true);
            //设置正面按钮
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                  //  Toast.makeText(RegionSelectActivity.this, "你点击了是的", Toast.LENGTH_SHORT).show();

                    //  EventBus.getDefault().post(subjectInfos.get(position));  3(经济学),4（护理学）,5（社会工作）,6（会计）
                    //

                    String  specId=mRegionDao.specId(mCity);
                    LogUtils.e("RegionSelectActivity" + infoPhone + specId);
                    SubmitDataBeanRequest request = new SubmitDataBeanRequest(infoPhone, specId);
                    morePresenter.submitData(RegionSelectActivity.this, request, RegionSelectActivity.this);
                    dialog.dismiss();
                }
            });
            //设置反面按钮
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(RegionSelectActivity.this, "你取消了操作", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();


        }
    }

    // 3 接收粘性事件
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void StickyEventBus(String info) {
        infoPhone = info;
        LogUtils.e(info);
    }

    @Override
    public void onBackPressed() {
        if (state == 0) {
            super.onBackPressed();
        }
        if (state == 1) {
            titleTextView.setText("选择专业");
            mList.clear();
            mAdapter.addData(mProvinceList);
            state--;
        } else if (state == 2) {
            titleTextView.setText("选择科目");
            mList.clear();
            mAdapter.addData(mCityList);
            state--;
        }
    }

    @Override
    public int setView(Bundle savedInstanceState) {
        return R.layout.activity_select_region;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void setListener(Bundle savedInstanceState) {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        morePresenter = new RegionDaoPresenter();
        EventBus.getDefault().register(this);
        mRegionDao = new RegionDao(this);

        mList = new ArrayList<>();
        mAdapter = new RegionAdapter(mList);
        mAdapter.setOnRecyclerViewItemClickListener(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

        titleTextView = findViewById(R.id.tv_title_name);
        backImageView = findViewById(R.id.iv_title_back);
        backImageView.setOnClickListener(this);

        setTitle("选择考试类型");
        titleTextView.setText("选择考试类型");
        mProvinceList = mRegionDao.loadProvinceList();
        mAdapter.addData(mProvinceList);
    }

    @Override
    public void onClick(View v) {
        if (state == 0) {
            super.onBackPressed();
        }

        if (state == 1) {
            titleTextView.setText("选择专业");
            mList.clear();
            mAdapter.addData(mProvinceList);
            state--;
        } else if (state == 2) {
            titleTextView.setText("选择科目");
            mList.clear();
            mAdapter.addData(mCityList);
            state--;
        }
    }

    @Override
    public void onBeanInfo(Object beanInfo) {

        try {
            loginBeanResponse = (UserInfoBean) beanInfo;
            if (loginBeanResponse.isSuccess()) {
                Toast.makeText(this, "亲爱的" + loginBeanResponse.getData().getPhone() + "您选择了" + loginBeanResponse.getData().getSpecname(), Toast.LENGTH_SHORT).show();
                //  startActivity(new Intent(this, MainActivity.class));
                LogUtils.e("loginBeanResponse" + loginBeanResponse.getData().toString());
                //保存应户名和密码
                //1. 得到连接
                UserInfoDBHelper dbHelper = new UserInfoDBHelper(this);
                SQLiteDatabase database = dbHelper.getReadableDatabase();
                //2. 执行insert  insert into person(name, age) values('Tom', 12)
                ContentValues values = new ContentValues();
                values.put("name", loginBeanResponse.getData().getPhone());
                values.put("password", loginBeanResponse.getData().getPassword());
                values.put("major", loginBeanResponse.getData().getSpecname());
                values.put("login", "1");
                values.put("subject", mArea);
                values.put("dbpath", loginBeanResponse.getData().getSpecpathdb());

                long id = database.insert("person", null, values);
                //3. 关闭
                database.close();
                //4. 提示
                Toast.makeText(this, "成功选择专业" + id, 1).show();

                EventBus.getDefault().postSticky(loginBeanResponse);
                startActivity(new Intent(this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(this, "选取专业失败，请重新输入！", Toast.LENGTH_SHORT).show();
            }

        } catch (ClassCastException e) {
            Log.e(TAG, e.getMessage());

        } finally {
        }
    }

    @Override
    public void onException(NormalException e) {

    }
}
