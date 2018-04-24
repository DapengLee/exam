package com.snow.selfexam.mvp.view.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.snow.selfexam.R;
import com.snow.selfexam.app.base.BaseFragment;
import com.snow.selfexam.app.db.UserInfoDBHelper;
import com.snow.selfexam.app.module.guide.WelcomeActivity;
import com.snow.selfexam.app.utils.ClearData;
import com.snow.selfexam.app.utils.UIUtils;
import com.snow.selfexam.mvp.modle.RegionModel;
import com.snow.selfexam.mvp.modle.UserInfoBean;
import com.snow.selfexam.mvp.presenter.MorePresenter;
import com.snow.selfexam.mvp.view.activities.AllDataSubjectActivity;
import com.snow.selfexam.mvp.view.activities.SettingActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;


public class MineFragment extends BaseFragment<MorePresenter> implements View.OnClickListener {


    private SimpleDraweeView photoSimpleMine;
    private CardView cv_id_major;
    private CardView cv_id_friend;
    private CardView cv_id_about_us;
    private CardView cv_id_setting;
    private CardView cv_id_idea;
    private Button personal_center_exit;
    UserInfoBean info;
    private TextView tv_show_major, my_data_myjory;


    private String major;
    private String phone;

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(UIUtils.getContext(), R.layout.fragment_personal, null);
        photoSimpleMine = view.findViewById(R.id.photo_simple_mine);
        quaryMajor();
        Uri uri = Uri.parse("res://" +
                getActivity().getPackageName() +
                "/" + R.mipmap.login_logo);
        photoSimpleMine.setImageURI(uri);

        cv_id_major = view.findViewById(R.id.cv_id_major);
        cv_id_friend = view.findViewById(R.id.cv_id_friend);
        cv_id_about_us = view.findViewById(R.id.cv_id_about_us);
        cv_id_setting = view.findViewById(R.id.cv_id_setting);
        cv_id_idea = view.findViewById(R.id.cv_id_idea);
        personal_center_exit = view.findViewById(R.id.personal_center_exit);
        tv_show_major = view.findViewById(R.id.tv_show_major);
        my_data_myjory = view.findViewById(R.id.my_data_myjory);
        personal_center_exit.setOnClickListener(this);
        cv_id_major.setOnClickListener(this);
        cv_id_friend.setOnClickListener(this);
        cv_id_about_us.setOnClickListener(this);
        cv_id_setting.setOnClickListener(this);
        cv_id_idea.setOnClickListener(this);


        tv_show_major.setText(phone);
        my_data_myjory.setText(major);
        //   personal_center_exit.setOnClickListener(this);
       // EventBus.getDefault().register(this);
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
        Toast.makeText(getActivity(), "" + info.getData().getPhone(), Toast.LENGTH_SHORT).show();
     //   tv_show_major.setText(info.getData().getPhone());
        my_data_myjory.setText(info.getData().getSpecname());
    }*/

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.personal_center_exit:

                //  startActivity(new Intent(UIUtils.getContext(), WelcomeActivity.class));
                new AlertDialog.Builder(getActivity())

                        .setTitle("确认注销吗")
                        .setCancelable(false)
                        .setMessage("注销之后登陆需要网络连接 "+"\n"+"题库需要重新下载")

                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                ClearData.cleanDatabases(getActivity());
                                ClearData.cleanSharedPreference(getActivity());
                                //ClearData.cleanApplicationData(getContext());
                                android.os.Process.killProcess(android.os.Process.myPid());
                                //getActivity().finish();

                                //    startActivity(new Intent(getActivity(), WelcomeActivity.class));
                                dialog.dismiss();

                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).
                        create().show();


                break;
            case R.id.cv_id_major:
                //startActivity(new Intent(UIUtils.getContext(), MyMajorActivity.class));
                break;

            case R.id.cv_id_friend:
                Toast.makeText(UIUtils.getContext(), "cv_id_friend", 1).show();
                break;
            case R.id.cv_id_about_us:
                //startActivity(new Intent(UIUtils.getContext(), AboutUsActivity.class));
                startActivity(new Intent(UIUtils.getContext(), AllDataSubjectActivity.class));
                break;
            case R.id.cv_id_setting:
                startActivity(new Intent(UIUtils.getContext(), SettingActivity.class));
                break;
            case R.id.cv_id_idea:
                Toast.makeText(UIUtils.getContext(), "cv_id_idea", 1).show();
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    //    EventBus.getDefault().unregister(this);
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
            phone = cursor.getString(cursor.getColumnIndex("name"));
            return major;
        }
        // 3. 关闭
        cursor.close();
        database.close();
        // 4. 提示
        //  Toast.makeText(UIUtils.getContext(), "count=" + count, 1).show();

        return "专业未选择";
    }
}