package com.snow.selfexam.mvp.view.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.snow.selfexam.R;
import com.snow.selfexam.app.base.BaseActivity;
import com.snow.selfexam.app.communicate.CommCallBack;
import com.snow.selfexam.app.communicate.NormalException;
import com.snow.selfexam.app.communicate.requestbean.RegisterBeanRequest;
import com.snow.selfexam.app.utils.DataCountDownTimer;
import com.snow.selfexam.app.utils.IDcard;
import com.snow.selfexam.app.utils.ToastUtil;
import com.snow.selfexam.mvp.modle.JiGouInfo;
import com.snow.selfexam.mvp.modle.RegisterBean;
import com.snow.selfexam.mvp.presenter.RegisterPresenter;
import com.snow.selfexam.mvp.view.weight.DialogFactory;

import org.angmarch.views.NiceSpinner;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Timer;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class RegisterActivity extends BaseActivity<RegisterPresenter> implements OnClickListener, CommCallBack {
    private ImageView ivBack;
    private TextView tvOk;
    private EditText etPhone, etVerify, etPassword, etTuiJian, et_number;
    private Button btVerify, btRegister;
    private NiceSpinner btJiGou;

    private JiGouInfo info = new JiGouInfo();

    private int delay = 60;
    private boolean flag = true;

    private Timer timer;

    private Handler handlerTask = new Handler();
    private LinkedList<String> dataSpinner;

    private String phone_number;
    private String cord_number;

    private DataCountDownTimer myCountDownTimer;
    private boolean codeFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mPresenter = new RegisterPresenter();
        setSms();
        dataSpinner = new LinkedList<>(Arrays.asList("长沙雪豹文化传播有限公司", "湖北雪豹文化传播有限公司", "北京雪豹文化传播有限公司", "雪豹文化传播有限公司"));
        initView();
        btJiGou.setTextColor(Color.GREEN);
        btJiGou.attachDataSource(dataSpinner);
        setListener();

    }

    private void setSms() {
        EventHandler eh = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                handlerTask.sendMessage(msg);
            }
        };
        SMSSDK.registerEventHandler(eh);
    }

    /**
     * 使用Handler来分发Message对象到主线程中，处理事件
     */
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int event = msg.arg1;
            int result = msg.arg2;
            Object data = msg.obj;
            if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    boolean smart = (Boolean) data;
                    if (smart) {
                        Toast.makeText(getApplicationContext(), "该手机号已经注册过，请重新输入",
                                Toast.LENGTH_LONG).show();
                        etPhone.requestFocus();
                        return;
                    }
                }
            }
            if (result == SMSSDK.RESULT_COMPLETE) {

                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    Toast.makeText(getApplicationContext(), "验证码输入正确",
                            Toast.LENGTH_LONG).show();
                }
            } else {
                if (flag) {
                    etVerify.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(), "验证码获取失败请重新获取", Toast.LENGTH_LONG).show();
                    etPhone.requestFocus();
                } else {
                    Toast.makeText(getApplicationContext(), "验证码输入错误", Toast.LENGTH_LONG).show();
                }
            }

        }

    };

    private void initView() {
        // TODO Auto-generated method stub
        ivBack = (ImageView) findViewById(R.id.iv_back);
        etPhone = (EditText) findViewById(R.id.etPhone);
        etVerify = (EditText) findViewById(R.id.etVerify);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etTuiJian = (EditText) findViewById(R.id.etTuiJian);

        btVerify = (Button) findViewById(R.id.btVerify);
        btJiGou = (NiceSpinner) findViewById(R.id.nice_spinner);
        btRegister = (Button) findViewById(R.id.btRegister);
        et_number = findViewById(R.id.et_number);
    }

    private void setListener() {
        // TODO Auto-generated method stub
        ivBack.setOnClickListener(this);
        btVerify.setOnClickListener(this);
        btRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.btRegister:
                if (judCord()) {
                    SMSSDK.submitVerificationCode("86", phone_number, cord_number);
                    RegisterBeanRequest registBeanRequest = new RegisterBeanRequest(etPhone.getText().toString(), etPassword.getText().toString(), etVerify.getText().toString(), et_number.getText().toString());
                    mPresenter.register(this, registBeanRequest, this);
                    DialogFactory.showProgressDialog(RegisterActivity.this, "提示", "注册中，请稍后。");
                    flag = false;
                }

                break;
            case R.id.btVerify:
                if (judPhone())//去掉左右空格获取字符串
                {
                    SMSSDK.getVerificationCode("86", phone_number);
                    //    btVerify.requestFocus();
                    myCountDownTimer = new DataCountDownTimer(60000, 1000, btVerify);
                    myCountDownTimer.start();
                }
                break;
        }
    }


    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        if (null != timer) {
            timer.cancel();
            timer = null;
        }
    }

    private boolean judPhone() {
        if (TextUtils.isEmpty(etPhone.getText().toString().trim())) {
            Toast.makeText(RegisterActivity.this, "请输入您的电话号码", Toast.LENGTH_LONG).show();
            etPhone.requestFocus();
            return false;
        } else if (etPhone.getText().toString().trim().length() != 11) {
            Toast.makeText(RegisterActivity.this, "您的电话号码位数不正确", Toast.LENGTH_LONG).show();
            etPhone.requestFocus();
            return false;
        } else {
            phone_number = etPhone.getText().toString().trim();
            String num = "[1][345678]\\d{9}";
            if (phone_number.matches(num))
                return true;
            else {
                Toast.makeText(RegisterActivity.this, "请输入正确的手机号码", Toast.LENGTH_LONG).show();
                return false;
            }
        }
    }

    private boolean judCord() {
        if (!judPhone()) {
            return false;
        }

        String code = etVerify.getText().toString().trim();
        String pwd = etPassword.getText().toString().trim();
        String idCard = et_number.getText().toString();
        if (code.length() < 1) {
            ToastUtil.showToast(this, "请输入正确的验证码");
            return false;
        }
        if (pwd.length() < 1) {
            ToastUtil.showToast(this, "请输入正确的密码");
            return false;
        }
        if (!IDcard.action(this, idCard)) {
            ToastUtil.showToast(this, "请输入正确的身份证");
            return false;
        }

        if (!etTuiJian.getText().toString().equals(pwd)) {
            ToastUtil.showToast(this, "密码不一致");
            return false;
        }
        if (TextUtils.isEmpty(btVerify.getText().toString().trim())) {
            Toast.makeText(RegisterActivity.this, "请输入您的验证码", Toast.LENGTH_LONG).show();
            btVerify.requestFocus();
            return false;
        } else {
            cord_number = btVerify.getText().toString().trim();
            return true;
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
    public void onBeanInfo(Object beanInfo) {
        DialogFactory.dismissProgressDialog();
        RegisterBean registerBean = (RegisterBean) beanInfo;
        if (registerBean.isSuccess()) {
            Toast.makeText(this, "注册成功，请登录", Toast.LENGTH_SHORT).show();
            int resultCode = RESULT_OK;
            String num1 = etPhone.getText().toString();
            Intent data = new Intent();
            data.putExtra("phone", num1);
            setResult(resultCode, data);
            finish();
        } else {
            Toast.makeText(this, "注册失败" + registerBean.getMsg(), Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onException(NormalException e) {
        DialogFactory.dismissProgressDialog();
        // 例外信息
        String errorMsg;
        if (e.hasErrorCode()) {
            errorMsg = e.getMessage();
        } else {
            errorMsg = "网络连接失败。请检查网络，并重试！\r\n" + e.getMessage();
        }
        Toast.makeText(RegisterActivity.this, errorMsg, Toast.LENGTH_SHORT).show();

    }
}
