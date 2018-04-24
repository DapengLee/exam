package com.snow.selfexam.mvp.view.fragments;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.snow.selfexam.R;
import com.snow.selfexam.app.bank.activity.ChoiceActivity;
import com.snow.selfexam.app.base.BaseFragment;
import com.snow.selfexam.app.communicate.HttpTool;
import com.snow.selfexam.app.communicate.LoadDbInterface;
import com.snow.selfexam.app.db.RegionDao;
import com.snow.selfexam.app.db.UserInfoDBHelper;
import com.snow.selfexam.app.utils.AppSharePreferenceMgr;
import com.snow.selfexam.app.utils.ToastUtil;
import com.snow.selfexam.app.utils.UIUtils;
import com.snow.selfexam.mvp.modle.PosDataBean;
import com.snow.selfexam.mvp.modle.RegionModel;
import com.snow.selfexam.mvp.presenter.LearnPresenter;
import com.snow.selfexam.mvp.view.activities.CollectionDataActivity;
import com.snow.selfexam.mvp.view.activities.SearchActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;


public class LearnFragment extends BaseFragment<LearnPresenter> implements View.OnClickListener, AdapterView.OnItemClickListener ,LoadDbInterface {

    private Dialog dialog;
    private ImageView ivLoading;
    private LinearLayout llShouCang;
    private LinearLayout llCuoTi;
    private LinearLayout ll_search;
    private LinearLayout ll_day;
    private LinearLayout ll_all;
    private LinearLayout ll_true;
    private DrawerLayout main_drawerLayout;
    private TextView tv_exam_type;
    private TextView tv_exam_zhuanye;
    private TextView tv_exam_kemu;
    private LinearLayout left_frame;
    private ListView left_drawer_lv;
    private List<String> examType;

    private List<RegionModel> mAreaList;
    private RegionDao mRegionDao;
    private String quaryMajor;
    private String major;
    private String subject;

    //http://192.168.1.129:8888/db/newDB.db
    private static final String TAG = "LearnFragment";
    //http://192.168.1.140:8888/mobile
   // private String downPath = "http://192.168.1.140:8888/db/accountant.db";
  //  private String downPath = "http://192.168.1.140:8888/db/accountant.db";
    private String downPath ;
    //  private String downPath = "http://60.28.125.129/f1.market.xiaomi.com/download/AppStore/0ff41344f280f40c83a1bbf7f14279fb6542ebd2a/com.sina.weibo.apk";
    private ProgressDialog hintDialog;

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(UIUtils.getContext(), R.layout.fragment_questions, null);

        main_drawerLayout = view.findViewById(R.id.main_drawerLayout);
        main_drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        left_drawer_lv = view.findViewById(R.id.left_drawer_lv);
        main_drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                //main_drawerLayout.setClickable(true);
                left_drawer_lv.setClickable(true);

                //Toast.makeText(UIUtils.getContext(),"打开了",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        left_frame = view.findViewById(R.id.left_frame);
        llShouCang = view.findViewById(R.id.llShouCang);
        llCuoTi = view.findViewById(R.id.llCuoTi);
        ll_search = view.findViewById(R.id.ll_search);
        ll_day = view.findViewById(R.id.ll_day);
        ll_all = view.findViewById(R.id.ll_all);
        ll_true = view.findViewById(R.id.ll_true);


        tv_exam_type = view.findViewById(R.id.tv_exam_type);
        tv_exam_zhuanye = view.findViewById(R.id.tv_exam_zhuanye);

        tv_exam_kemu = view.findViewById(R.id.tv_exam_kemu);

        left_drawer_lv.setOnItemClickListener(this);

        tv_exam_type.setOnClickListener(this);
        tv_exam_zhuanye.setOnClickListener(this);
        tv_exam_kemu.setOnClickListener(this);


        llCuoTi.setOnClickListener(this);
        llShouCang.setOnClickListener(this);
        ll_search.setOnClickListener(this);
        ll_day.setOnClickListener(this);
        ll_all.setOnClickListener(this);
        ll_true.setOnClickListener(this);
        //查找所选的专业
        mRegionDao = new RegionDao(UIUtils.getContext());
        quaryMajor = quaryMajor();
        tv_exam_zhuanye.setText(quaryMajor.substring(0, 2));
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void setData(Object data) {

    }

    @Override
    public void initVariables() {

    }

    /* // 3 接收粘性事件
     @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
     public void StickyEventBus(UserInfoBean info) {
         Toast.makeText(getActivity(), "LearnFragment（）" + info.toString(), Toast.LENGTH_SHORT).show();


     }*/
    Boolean isDownDataLoader;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {


            case R.id.llShouCang:
                ToastUtil.showToast(getActivity(), "功能尚未开发");
                break;
            case R.id.llCuoTi:
                // startActivity(new Intent(getActivity(), ErrorActivity.class));
                ToastUtil.showToast(getActivity(), "功能尚未开发");
                break;
            case R.id.ll_search:
                isDownDataLoader = (Boolean) AppSharePreferenceMgr.get(getActivity(), "isDownDataLoader", false);
                if (isDownDataLoader) {
                    EventBus.getDefault().postSticky(tv_exam_kemu.getText().toString());
                    startActivity(new Intent(getActivity(), SearchActivity.class));
                } else {
                    //请先下载题库图
                    showNoticeDialog();
                }
                break;
            case R.id.ll_day:
                isDownDataLoader = (Boolean) AppSharePreferenceMgr.get(getActivity(), "isDownDataLoader", false);
                if (isDownDataLoader) {
                    String sDataName = mRegionDao.loadTBName(tv_exam_kemu.getText().toString());
                    EventBus.getDefault().postSticky(new PosDataBean("2", "", tv_exam_kemu.getText().toString(),sDataName));
                    startActivity(new Intent(getActivity(), ChoiceActivity.class));
                } else {
                    //请先下载题库图
                    showNoticeDialog();
                }
                break;
            case R.id.ll_all:
                isDownDataLoader = (Boolean) AppSharePreferenceMgr.get(getActivity(), "isDownDataLoader", false);
                if (isDownDataLoader) {
                    String sDataName = mRegionDao.loadTBName(tv_exam_kemu.getText().toString());
                    EventBus.getDefault().postSticky(new PosDataBean("2", "", tv_exam_kemu.getText().toString(),sDataName));
                    startActivity(new Intent(getActivity(), CollectionDataActivity.class));
                } else {
                    //请先下载题库图
                    showNoticeDialog();
                }
                break;
            case R.id.ll_true:
                //  startActivity(new Intent(getActivity(), TruePraActivity.class));
                ToastUtil.showToast(getActivity(), "功能尚未开发");
                break;

            case R.id.tv_exam_type:
                // openDrawer();
               /* examType = LearnPresenter.getType(1);
                left_drawer_lv.setAdapter(new ExamAdapter());*/
                break;
            case R.id.tv_exam_zhuanye:

              /*  openDrawer();
                examType = LearnPresenter.getType(2);*//*
                left_drawer_lv.setAdapter(new ExamAdapter());*/
                break;
            case R.id.tv_exam_kemu:
                openDrawer();
                int citeId= mRegionDao.loadCiteId(major);
                mAreaList = mRegionDao.loadCountyList((long) citeId);


               /* if (quaryMajor.equals("社会工作与管理")) {
                    //打开专业列表查询数据
                    mAreaList = mRegionDao.loadCountyList(36L);
                } else if (quaryMajor.equals("护理学")) {
                    mAreaList = mRegionDao.loadCountyList(37L);
                } else if (quaryMajor.equals("会计")) {
                    mAreaList = mRegionDao.loadCountyList(38L);
                } else {
                    mAreaList = mRegionDao.loadCountyList(39L);

                }*/
                //examType = LearnPresenter.getType(3);
                //插入到更新到usrInfo数据库中
                left_drawer_lv.setAdapter(new ExamAdapter());
                break;
        }
    }


    //查找专业
    private String quaryMajor() {

        // 1. 得到连接
        UserInfoDBHelper dbHelper = new UserInfoDBHelper(getActivity());
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        // 2. 执行query select * from person
        Cursor cursor = database.query("person", null, null, null, null, null, null);
        //cursor = database.query("person", null, "_id=?", new String[]{"3"}, null, null, null);
        //得到匹配的总记录数
        int count = cursor.getCount();

        //取出cursor中所有的数据
        while (cursor.moveToNext()) {
            //age
            major = cursor.getString(cursor.getColumnIndex("major"));
            subject = cursor.getString(cursor.getColumnIndex("subject"));
            downPath=cursor.getString(cursor.getColumnIndex("dbpath"));
           if (subject==null){
             int citeId= mRegionDao.loadCiteId(major);
               List<RegionModel> regionModels = mRegionDao.loadCountyList((long) citeId);
               tv_exam_kemu.setText(regionModels.get(1).getName());

           }else {
               tv_exam_kemu.setText(subject);
           }
            return major;
        }
        // 3. 关闭
        cursor.close();
        database.close();
        // 4. 提示
      //  Toast.makeText(UIUtils.getContext(), "count=" + count, 1).show();

        return "专业未选择";
    }


    public void openDrawer() {
        // TODO 点击按钮打开侧滑菜单
        if (!main_drawerLayout.isDrawerOpen(left_frame)) {
            main_drawerLayout.openDrawer(left_frame);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Toast.makeText(UIUtils.getContext(), "" + mAreaList.get(position), Toast.LENGTH_SHORT).show();
        // TODO 点击按钮打开侧滑菜单
        tv_exam_kemu.setText(mAreaList.get(position).getName());
        if (main_drawerLayout.isDrawerOpen(left_frame)) {
            main_drawerLayout.closeDrawer(left_frame);
        }
    }

    @Override
    public void success() {
        AppSharePreferenceMgr.put(getActivity(), "isDownDataLoader", true);
        ToastUtil.showToast(getActivity(),"题库更新成功");
    }

    class ExamAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mAreaList.size();
        }

        @Override
        public Object getItem(int position) {
            return mAreaList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View itemView = View.inflate(getActivity(), R.layout.drawer_list_item, null);
            TextView mTextView = itemView.findViewById(R.id.dw_tv_item);
            mTextView.setText(mAreaList.get(position).getName());
            return itemView;
        }
    }

    /**
     * 显示提示下载对话框
     */
    private void showNoticeDialog() {
        new AlertDialog.Builder(getActivity())
                .setTitle("开始下载题库！")
                .setMessage("请再次确认专业信息")
                .setPositiveButton("下载", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        HttpTool.downloadDatabase(getActivity(),downPath,"ks",LearnFragment.this);
                    }
                }).setNegativeButton("下次再说", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create().show();
    }

}