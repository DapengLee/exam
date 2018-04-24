package com.snow.selfexam.app.module.guide;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.snow.selfexam.R;
import com.snow.selfexam.app.db.UserInfoDBHelper;
import com.snow.selfexam.app.utils.ClearData;
import com.snow.selfexam.app.utils.DBCopyUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 苏雷
 * Created by ASUS on 2017/7/30.
 * 引导页
 */

public class GuideActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    private ViewPager vp;
    private ViewPagerAdapter vpAdapter;
    private List<View> views;

    // 底部小点图片
    private ImageView[] dots;

    // 记录当前选中位置
    private int currentIndex;
    Boolean isFirst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guide_layout);

       // ClearData.cleanDatabaseByName(this,"ks.db");
        //初始化数据的操作
        initDB();
        // 初始化页面
        initViews();
        // 初始化底部小点
        initDots();
    }

    private void initDB() {
        DBCopyUtil.copyDataBaseFromAssets(this, "region.db");
        testUpdateDB();
    }

    /*
         * 更新库
         */
    public void testUpdateDB() {
        UserInfoDBHelper dbHelper = new UserInfoDBHelper(this);
        //获取连接
        SQLiteDatabase database = dbHelper.getReadableDatabase();

      //  Toast.makeText(this, "创建UserInfoDb数据库成功", 0).show();
    }

    private void initViews() {

        final SharedPreferences pref = getSharedPreferences("first", Activity.MODE_PRIVATE);
        isFirst = pref.getBoolean("status", false);

        if (isFirst) {
            Intent intent = new Intent(this, WelcomeActivity.class);
            startActivity(intent);
            finish();
        }


        LayoutInflater inflater = LayoutInflater.from(this);
        RelativeLayout guideFour = (RelativeLayout) inflater.inflate(R.layout.guide_four, null);
        guideFour.findViewById(R.id.toMain).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor edit = pref.edit();
                edit.putBoolean("status", true);
                edit.commit();
                Intent intent = new Intent(GuideActivity.this, WelcomeActivity.class);
                startActivity(intent);
                finish();

            }
        });
        views = new ArrayList<View>();
        // 初始化引导图片列表
        views.add(inflater.inflate(R.layout.guide_one, null));
        views.add(inflater.inflate(R.layout.guide_two, null));
        // views.add(inflater.inflate(R.layout.guide_three, null));
        views.add(guideFour);
        // 初始化Adapter
        vpAdapter = new ViewPagerAdapter(views, this);

        vp = (ViewPager) findViewById(R.id.viewpager);
        vp.setAdapter(vpAdapter);
        // 绑定回调
        vp.setOnPageChangeListener(this);


    }

    private void initDots() {
        LinearLayout ll = (LinearLayout) findViewById(R.id.ll);

        dots = new ImageView[views.size()];

        // 循环取得小点图片
        for (int i = 0; i < views.size(); i++) {
            dots[i] = (ImageView) ll.getChildAt(i);
            dots[i].setEnabled(true);// 都设为灰色
        }

        currentIndex = 0;
        dots[currentIndex].setEnabled(false);// 设置为白色，即选中状态
    }

    private void setCurrentDot(int position) {
        if (position < 0 || position > views.size() - 1
                || currentIndex == position) {
            return;
        }

        dots[position].setEnabled(false);
        dots[currentIndex].setEnabled(true);

        currentIndex = position;
    }

    // 当滑动状态改变时调用
    @Override
    public void onPageScrollStateChanged(int arg0) {
    }

    // 当当前页面被滑动时调用
    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }

    // 当新的页面被选中时调用
    @Override
    public void onPageSelected(int arg0) {
        // 设置底部小点选中状态
        setCurrentDot(arg0);
    }
}
