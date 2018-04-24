package com.snow.selfexam.mvp.view.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.snow.selfexam.R;
import com.snow.selfexam.app.adapter.AllDataSubjectAdapter;
import com.snow.selfexam.app.base.BaseActivity;
import com.snow.selfexam.app.communicate.CommCallBack;
import com.snow.selfexam.app.communicate.NormalException;
import com.snow.selfexam.app.communicate.requestbean.SubmitDataBeanRequest;
import com.snow.selfexam.app.utils.AppSharePreferenceMgr;
import com.snow.selfexam.mvp.modle.CategoryBean;
import com.snow.selfexam.mvp.presenter.CategoryMorePresenter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;

public class AllDataSubjectActivity extends BaseActivity<CategoryMorePresenter> implements AdapterView.OnItemClickListener, View.OnClickListener, CommCallBack {

    private GridView all_data_gv;
    private List<CategoryBean.DataBean> subjectInfos;
    private TextView tv_title_name;
    private ImageView iv_title_back;
    private CategoryMorePresenter morePresenter;
    private Button mButton;


    //测试数据
    private ProgressBar mProgressBar;
    private static final String TAG = "AllDataSubjectActivity";
    private String downPath = "http://60.28.125.129/f1.market.xiaomi.com/download/AppStore/0ff41344f280f40c83a1bbf7f14279fb6542ebd2a/com.sina.weibo.apk";
    private ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_data_subject);
        morePresenter = new CategoryMorePresenter();
        subjectInfos = new ArrayList<>();
        all_data_gv = (GridView) findViewById(R.id.all_data_gv);
        EventBus.getDefault().register(this);


        mProgressBar = (ProgressBar) findViewById(R.id.id_progress);
        mProgressBar.setMax(100);
        mButton = findViewById(R.id.bt_downloadFile);
        mButton.setOnClickListener(this);
        initView();

    }

    /**
     * 显示提示下载对话框
     */
    private void showNoticeDialog() {
        new AlertDialog.Builder(this)
                .setTitle("开始下载题库！")
                .setMessage("请再次确认专业信息")
                .setPositiveButton("下载", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        showDownloadDialog();
                    }
                }).setNegativeButton("下次再说", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create().show();
    }

    //ProgressDialog下载的进度框
    public void showDownloadDialog() {
        dialog = new ProgressDialog(this);
        dialog.setTitle("温馨提示");
        dialog.setMessage("正在下载...");
        dialog.setProgressStyle(dialog.STYLE_HORIZONTAL);
        dialog.setCanceledOnTouchOutside(false);
        downloadFile();
        //  dialog.show();
    }


    public void downloadFile() {
        String url = downPath;
        OkHttpUtils//
                .get()//
                .url(url)//
                .build()//
                .execute(new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(), "gson-2.2.1.jar")//
                {

                    @Override
                    public void onBefore(Request request, int id) {
                        dialog.show();
                    }

                    @Override
                    public void inProgress(float progress, long total, int id) {
                        //    mProgressBar.setProgress((int) (100 * progress));
                        dialog.setProgress((int) (100 * progress));
                        Log.e(TAG, "inProgress :" + (int) (100 * progress));
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError :" + e.getMessage());
                    }

                    @Override
                    public void onResponse(File file, int id) {
                        Log.e(TAG, "onResponse :" + file.getAbsolutePath());
                        dialog.dismiss();

                    }
                });
    }

    private void initView() {


        all_data_gv.setOnItemClickListener(this);
        tv_title_name = findViewById(R.id.tv_title_name);
        iv_title_back = findViewById(R.id.iv_title_back);
        iv_title_back.setVisibility(View.VISIBLE);
        iv_title_back.setOnClickListener(this);
        tv_title_name.setText("自考列表详情");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        //  EventBus.getDefault().post(subjectInfos.get(position));
        SubmitDataBeanRequest request = new SubmitDataBeanRequest("13587687845", "1");
        morePresenter.submitData(this, request, this);
        //   finish();
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

        //   downloadFile();
        showNoticeDialog();
        Toast.makeText(this, "开始下载", Toast.LENGTH_SHORT).show();
    }

    // 3 接收粘性事件
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void StickyEventBus(List<CategoryBean.DataBean> subjectInfosaa) {
        //    Toast.makeText(this, "" + subjectInfos.toString(), Toast.LENGTH_SHORT).show();
        subjectInfos = new ArrayList<>();
        subjectInfos = subjectInfosaa;
        all_data_gv.setAdapter(new AllDataSubjectAdapter(this, subjectInfos));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().removeAllStickyEvents();
    }

    @Override
    public void onBeanInfo(Object beanInfo) {
        AppSharePreferenceMgr.put(this, "FLAG", "FLAGDATA");
        //  EventBus.getDefault().postSticky(subjectInfos.get(position));
    }

    @Override
    public void onException(NormalException e) {

    }
}
