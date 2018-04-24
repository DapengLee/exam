package com.snow.selfexam.mvp.view.activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.snow.selfexam.R;
import com.snow.selfexam.app.base.BaseActivity;
import com.snow.selfexam.app.communicate.CommCallBack;
import com.snow.selfexam.app.communicate.NormalException;
import com.snow.selfexam.app.communicate.requestbean.ModifyPwBeanRequest;
import com.snow.selfexam.app.utils.DataCountDownTimer;
import com.snow.selfexam.app.utils.SmallTools;
import com.snow.selfexam.app.utils.ToastUtil;
import com.snow.selfexam.mvp.modle.ModifyReturnBean;
import com.snow.selfexam.mvp.presenter.ModifyPwPresenter;

import java.util.Timer;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class ModifyPwActivity extends BaseActivity<ModifyPwPresenter> implements OnClickListener, CommCallBack {
    private ImageView ivBack;
    private EditText etPhone, etVerify, etPassword;
    private Button btVerify, btOk;
    private ModifyPwPresenter modifyPwPresenter;
    private DataCountDownTimer myCountDownTimer;
    private String phone;
    private String pwd;
    private String code;
    private String  phone_number;
    private Handler handlerTask = new Handler();
    private boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_pwd);
        setSms();
        initView();

        setListener();
    }

    private void initView() {
        // TODO Auto-generated method stub
        modifyPwPresenter = new ModifyPwPresenter();
        ivBack = (ImageView) findViewById(R.id.iv_back);
        etPhone = (EditText) findViewById(R.id.etPhone);
        etVerify = (EditText) findViewById(R.id.etVerify);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btVerify = (Button) findViewById(R.id.btVerify);
        btOk = (Button) findViewById(R.id.btOk);
    }

    private void setListener() {
        // TODO Auto-generated method stub
        ivBack.setOnClickListener(this);
        btVerify.setOnClickListener(this);
        btOk.setOnClickListener(this);
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
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
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
            case R.id.btOk:
                boolean isRegisterOk = modifyPwd();
                if (isRegisterOk) {
                    showModifyData();
                }
                break;
        }
    }

    private boolean judPhone() {
        if (TextUtils.isEmpty(etPhone.getText().toString().trim())) {
            Toast.makeText(ModifyPwActivity.this, "请输入您的电话号码", Toast.LENGTH_LONG).show();
            etPhone.requestFocus();
            return false;
        } else if (etPhone.getText().toString().trim().length() != 11) {
            Toast.makeText(ModifyPwActivity.this, "您的电话号码位数不正确", Toast.LENGTH_LONG).show();
            etPhone.requestFocus();
            return false;
        } else {
            phone_number = etPhone.getText().toString().trim();
            String num = "[1][34578]\\d{9}";
            if (phone_number.matches(num))
                return true;
            else {
                Toast.makeText(ModifyPwActivity.this, "请输入正确的手机号码", Toast.LENGTH_LONG).show();
                return false;
            }
        }
    }
    private void showModifyData() {
        new AlertDialog.Builder(this)
                .setTitle("修改密码！")
                .setMessage("你确定是否要修改密码")
                .setPositiveButton("修改", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        modifyPwPresenter.sendData(ModifyPwActivity.this, new ModifyPwBeanRequest(phone, code, pwd), ModifyPwActivity.this);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create().show();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private boolean modifyPwd() {
        phone = etPhone.getText().toString().trim();
        code = etVerify.getText().toString().trim();
        pwd = etPassword.getText().toString().trim();
        if (!SmallTools.isTEL(phone)) {
            ToastUtil.showToast(this, "请输入正确的手机号码");
            return false;
        }
        if (code.length() < 1) {
            ToastUtil.showToast(this, "请输入正确的验证码");
            return false;
        }
        if (pwd.length() < 1) {
            ToastUtil.showToast(this, "请输入正确的密码");
            return false;
        }
        return true;
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
        try {
            ModifyReturnBean loginBeanResponse = (ModifyReturnBean) beanInfo;
            if (loginBeanResponse.isSuccess()) {
                Toast.makeText(this, loginBeanResponse.getMsg(), Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "用户名和密码错误，请重新输入！", Toast.LENGTH_SHORT).show();
            }
        } catch (ClassCastException e) {
            Log.e(TAG, e.getMessage());
            Toast.makeText(ModifyPwActivity.this, "找回密码失败，原因：" + e.getMessage() + "。", Toast.LENGTH_SHORT).show();
        } finally {


        }

    }

    @Override
    public void onException(NormalException e) {

    }
}
