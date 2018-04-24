package com.snow.selfexam.app.bank.activity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.snow.selfexam.R;
import com.snow.selfexam.app.bank.adapter.ItemAdapter;
import com.snow.selfexam.app.bank.bean.QuestionBean;
import com.snow.selfexam.app.bank.bean.QuestionOptionBean;
import com.snow.selfexam.app.bank.dao.ExerciseDao;
import com.snow.selfexam.app.bank.view.ConfirmDialog;
import com.snow.selfexam.app.factory.FragmentFactory;
import com.snow.selfexam.app.utils.ToastUtil;
import com.snow.selfexam.mvp.modle.PosDataBean;
import com.snow.selfexam.mvp.view.weight.NoScrollViewPager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;


public class ChoiceActivity extends FragmentActivity implements OnClickListener {
    List<View> list = new ArrayList<View>();
    public List<QuestionBean> questionlist = new ArrayList<QuestionBean>();

    public static QuestionOptionBean option;
    public NoScrollViewPager vp;
    private ItemAdapter pagerAdapter;
    View pager_item;
    public static int currentIndex = 0;
    private TextView tv_time;
    private TextView tv_answercard;
    private ImageView tv_back;
    private ExerciseDao dao;
    private String infoType;
    private String examData;

    private PosDataBean infoTypeBean;

    private RadioGroup buttonbar_radio;
    private RadioButton falseRadioButton;
    private RadioButton trueRadioButton;

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
            window.setStatusBarColor(getResources().getColor(R.color.new_blue_normal));
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //设置为无标题栏
        initSystemBarTint();
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_bank);

        tv_back = (ImageView) findViewById(R.id.iv_choice_back);
        tv_answercard = (TextView) findViewById(R.id.tv_answercard);
        tv_time = (TextView) findViewById(R.id.tv_time);
        startCounter();
        tv_back.setOnClickListener(this);

        tv_answercard.setOnClickListener(this);
        tv_time.setOnClickListener(this);
        buttonbar_radio = findViewById(R.id.buttonbar_radio);
        falseRadioButton = findViewById(R.id.select_name_btu);
        trueRadioButton =  findViewById(R.id.select_fac_sup_btu);

        if (infoTypeBean.getIsExercise()) {
            buttonbar_radio.setVisibility(View.VISIBLE);
            loadData("");
        }else {
            loadData();
        }


        buttonbar_radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.select_name_btu:
                       //ToastUtil.showToast(ChoiceActivity.this,"");
                        loadData("1");
                        pagerAdapter = new ItemAdapter(getSupportFragmentManager(), questionlist, infoType, infoTypeBean.getExam());
                        vp.setAdapter(pagerAdapter);
                        break;
                    case R.id.select_fac_sup_btu:
                        //ToastUtil.showToast(ChoiceActivity.this,"1");

                        loadData("");
                        pagerAdapter = new ItemAdapter(getSupportFragmentManager(), questionlist, infoType, infoTypeBean.getExam());
                        vp.setAdapter(pagerAdapter);
                        // }
                        break;

                }
            }
        });




        vp = (NoScrollViewPager) findViewById(R.id.vp);
        vp.setScanScroll(false);
        vp.setCurrentItem(0);
        pagerAdapter = new ItemAdapter(getSupportFragmentManager(), questionlist, infoType, infoTypeBean.getExam());
        vp.setAdapter(pagerAdapter);
        vp.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int position) {
                currentIndex = position;
            }
        });

        LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.leyikao.jumptonext");
        filter.addAction("com.leyikao.jumptopage");
        filter.addAction("com.leyikao.jumptoback");
        lbm.registerReceiver(mMessageReceiver, filter);
    }

    private void loadData(String s) {
        dao = new ExerciseDao(this);

        if (infoTypeBean != null) {
            questionlist = dao.testQuery(infoTypeBean.getTypeData(), infoTypeBean.getIsJiXu(), infoTypeBean.getExamData(),s);
        }
        if (questionlist.size() == 0) {
            ToastUtil.showToast(this, "题库暂时没有题，敬请期待");
        }
    }

    // 3 接收粘性事件
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void EventBusName(PosDataBean infoTypeBean) {

        this.infoTypeBean = infoTypeBean;
        this.infoType = infoTypeBean.getTypeData();
        this.examData = infoTypeBean.getExamData();
        //  Toast.makeText(this, "ChoiceActivity（）" + infoTypeBean.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_choice_back://点击头部返回
                finish();
                break;
            case R.id.tv_answercard://点击头部答题卡

                jumpToPage(questionlist.size());

                break;
            case R.id.tv_time://点击头部计时器
                //TODO计时器停止计时
                stopCounter();
                final ConfirmDialog confirmDialog = new ConfirmDialog(this, "共" + questionlist.size() + "道题");
                confirmDialog.setCancelable(false);
                confirmDialog.show();
                confirmDialog.setClicklistener(new ConfirmDialog.ClickListenerInterface() {

                    @Override
                    public void doProceed() {
                        //TODO计时器继续计时
                        confirmDialog.dismiss();
                        startCounter();
                    }

                });
                break;
            default:
                break;
        }
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.leyikao.jumptonext")) {
                jumpToNext(1);
            } else if (intent.getAction().equals("com.leyikao.jumptopage")) {
                int index = intent.getIntExtra("index", 0);
                jumpToPage(index);
            } else if (intent.getAction().equals("com.leyikao.jumptoback")) {
                jumpToNext(2);
            }
        }
    };

    public void jumpToNext(int i) {
        if (i == 1) {
            int position = vp.getCurrentItem();
            vp.setCurrentItem(position + 1);
            currentIndex = position;
            // Toast.makeText(this, "" + position, Toast.LENGTH_SHORT).show();
        } else {
            int position = vp.getCurrentItem();
            if (position == 0) {
                Toast.makeText(this, "这是第一题", Toast.LENGTH_SHORT).show();
            } else {
                vp.setCurrentItem(position - 1);
            }

        }
    }

    public void jumpToPage(int index) {
        vp.setCurrentItem(index);
    }

    private void loadData() {
        dao = new ExerciseDao(this);

        if (infoTypeBean != null) {
            questionlist = dao.testQuery(infoTypeBean.getTypeData(), infoTypeBean.getIsJiXu(), infoTypeBean.getExamData(),"");
        }
        if (questionlist.size() == 0) {
            ToastUtil.showToast(this, "题库暂时没有题，敬请期待");
        }
    }

    //计时器任务
    int time = 0;
    int second = 0;
    int minute = 0;
    String timeStr = "00:00";
    int[] iTime = new int[]{0, 0, 0, 0};
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    time++;
                    second = time % 60;
                    minute = time / 60;
                    if (minute > 99) {
                        break;
                    }
                    //Log.e("秒数", ""+second);
                    //Log.e("分钟数", ""+minute);
                    if (second < 10 && minute < 10) {
                        iTime[0] = 0;
                        iTime[1] = minute;
                        iTime[2] = 0;
                        iTime[3] = second;

                    } else if (second >= 10 && minute < 10) {
                        iTime[0] = 0;
                        iTime[1] = minute;
                        iTime[2] = (second + "").charAt(0) - 48;
                        iTime[3] = (second + "").charAt(1) - 48;

                    } else if (second < 10 && minute >= 10) {
                        iTime[0] = (minute + "").charAt(0) - 48;
                        iTime[1] = (minute + "").charAt(1) - 48;
                        iTime[2] = 0;
                        iTime[3] = second;

                    } else if (second >= 10 && minute >= 10) {
                        iTime[0] = (minute + "").charAt(0) - 48;
                        iTime[1] = (minute + "").charAt(1) - 48;
                        iTime[2] = (second + "").charAt(0) - 48;
                        iTime[3] = (second + "").charAt(1) - 48;

                    }
                    tv_time.setText("" + iTime[0] + iTime[1] + ":" + iTime[2] + iTime[3]);
                    handler.sendEmptyMessageDelayed(1, 1000);
                    break;

                default:
                    break;
            }

        }

        ;
    };


    // 开始计时
    public void startCounter() {
        handler.sendEmptyMessageDelayed(1, 1000);
    }

    // 暂停计时
    public void stopCounter() {
        handler.removeCallbacksAndMessages(null);
    }

    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(
                mMessageReceiver);
        super.onDestroy();
    }
}
