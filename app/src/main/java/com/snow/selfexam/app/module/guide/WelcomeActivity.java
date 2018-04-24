package com.snow.selfexam.app.module.guide;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.snow.selfexam.R;
import com.snow.selfexam.app.db.UserInfoDBHelper;
import com.snow.selfexam.app.utils.DBCopyUtil;
import com.snow.selfexam.app.utils.SmallTools;
import com.snow.selfexam.mvp.modle.UserInfoBean;
import com.snow.selfexam.mvp.view.activities.LoginActivity;
import com.snow.selfexam.mvp.view.activities.MainActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 欢迎页面
 * Created by ASUS on 2017/9/10.
 */

public class WelcomeActivity extends AppCompatActivity {
    private ImageView iv_splash_logo;
    private String userName;
    private String passWord;
    private String major;
    private Intent intent;
    private UserInfoBean userInfoBean;
    private String login="";
    private UserInfoBean.DataBean  userDataBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置为无标题栏
        initSystemBarTint();
        setContentView(R.layout.activity_welcome);
        initView();
        //临时测试数据
        DBCopyUtil.copyDataBaseFromAssets(this, "region.db");
        //c查询用户的信息


        if (testQuery()) {
            //你要转向的Activity
            // 发送粘性事件
            EventBus.getDefault().postSticky(userInfoBean);
            intent = new Intent(this, MainActivity.class);
        } else {
            //你要转向的Activity
            intent = new Intent(this, LoginActivity.class);
        }


        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                SmallTools.activityOut(WelcomeActivity.this);
                startActivity(intent); //执行
                finish();
            }
        };
        timer.schedule(task, 1000 * 3);
    }

    /**
     * 设置状态栏颜色
     */
    protected void initSystemBarTint() {
        Window window = getWindow();
        if (translucentStatusBar()) {
            // 设置状态栏全透明
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
            return;
        }
        // 沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //5.0以上使用原生方法
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.white));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //4.4-5.0使用三方工具类，有些4.4的手机有问题，这里为演示方便，不使用沉浸式
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
         /*   SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintColor(setStatusBarColor());*/
        }
    }

    /**
     * 子类可以重写决定是否使用透明状态栏
     */
    protected boolean translucentStatusBar() {
        return false;
    }


    private void initView() {
        iv_splash_logo = (ImageView) findViewById(R.id.iv_splash_logo);
       /* ObjectAnimator anim1 = ObjectAnimator.ofFloat(iv_splash_logo, "scaleX", 1.2f, 0.8f);
        anim1.setRepeatCount(2);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(iv_splash_logo, "scaleY", 1.2f, 0.8f);
        anim2.setRepeatCount(2);
        AnimatorSet set = new AnimatorSet();
        set.play(anim1).with(anim2);
        set.setDuration(1000);
        set.start();*/
    }

    /*
     * 查询
	 */
    public boolean testQuery() {
        userInfoBean = new UserInfoBean();
        userDataBean=new UserInfoBean.DataBean();

        // 1. 得到连接
        UserInfoDBHelper dbHelper = new UserInfoDBHelper(this);
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        // 2. 执行query select * from person
        Cursor cursor = database.query("person", null, null, null, null, null, null);
        //cursor = database.query("person", null, "_id=?", new String[]{"3"}, null, null, null);
        //得到匹配的总记录数
        int count = cursor.getCount();

        //取出cursor中所有的数据
        while (cursor.moveToNext()) {
            //_id
            int id = cursor.getInt(0);
            //name
            userName = cursor.getString(1);
            userDataBean.setPhone(userName);
            //密码
            passWord = cursor.getString(cursor.getColumnIndex("password"));
            userDataBean.setPassword(passWord);
            //专业
            major = cursor.getString(cursor.getColumnIndex("major"));
            Log.e("TAG", id + "-" + userName + "-" + passWord + "-" + major);
            userDataBean.setSpecname(major);
            //登录
            login = cursor.getString(cursor.getColumnIndex("login"));

            userInfoBean.setData(userDataBean);
        }
        // 3. 关闭
        cursor.close();
        database.close();
        // 4. 提示
       // Toast.makeText(this, "count=" + count, 1).show();

        if (login.equals("1")&&login!=null) {
            return true;
        } else {
            return false;
        }
    };
}
