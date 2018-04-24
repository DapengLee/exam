package com.snow.selfexam.mvp.view.activities;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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
import com.snow.selfexam.app.communicate.requestbean.LoginBeanRequest;
import com.snow.selfexam.app.db.UserInfoDBHelper;
import com.snow.selfexam.app.utils.SmallTools;
import com.snow.selfexam.mvp.modle.UserInfoBean;
import com.snow.selfexam.mvp.presenter.LoginPresenter;
import com.snow.selfexam.mvp.view.weight.DialogFactory;

import org.greenrobot.eventbus.EventBus;


public class LoginActivity extends BaseActivity<LoginPresenter> implements OnClickListener, CommCallBack {
    private ImageView ivBack;
    private EditText etPhone, etPassword;
    private Button btLogin, btForget, btRegister;
    private LoginPresenter mLoginPresenter;

    private ProgressDialog pd;

    private Intent intent;

    @Override
    public int setView(Bundle savedInstanceState) {
        return R.layout.activity_login;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mLoginPresenter = new LoginPresenter();
    }

    @Override
    public void setListener(Bundle savedInstanceState) {
        ivBack.setOnClickListener(this);
        btLogin.setOnClickListener(this);
        btForget.setOnClickListener(this);
        btRegister.setOnClickListener(this);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        ivBack = (ImageView) findViewById(R.id.iv_back);
        etPhone = (EditText) findViewById(R.id.etPhone);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btLogin = (Button) findViewById(R.id.btLogin);
        btForget = (Button) findViewById(R.id.btForget);
        btRegister = (Button) findViewById(R.id.btRegister);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.btLogin:

                //具体登录模块  需要真实联网之后再做  真实的登录
                boolean loginMessage = mLoginPresenter.check(etPhone, etPassword);

                if (loginMessage) {
                    btLogin.setEnabled(true);
                    LoginBeanRequest loginBeanRequest = new LoginBeanRequest(etPhone.getText().toString(), etPassword.getText().toString());
                    mLoginPresenter.login(LoginActivity.this, loginBeanRequest, LoginActivity.this);

                    DialogFactory.showProgressDialog(LoginActivity.this, "提示", "登陆中，请稍后。");
                }
                break;

            //mLoginPresenter.login(etPhone, etPassword);
            /*intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();*/

            case R.id.btForget:
                intent = new Intent(LoginActivity.this, ModifyPwActivity.class);
                // intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);

                startActivityForResult(intent, 0);
                SmallTools.activityIn(LoginActivity.this);
                break;
            case R.id.btRegister:
                intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivityForResult(intent, 0);
                SmallTools.activityIn(LoginActivity.this);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 0:
                    String phone = data.getStringExtra("phone");
                    etPhone.setText("" + phone);
                    break;
            }
        }
    }

    @Override
    public void onBeanInfo(Object beanInfo) {
        DialogFactory.dismissProgressDialog();


        try {
            UserInfoBean loginBeanResponse = (UserInfoBean) beanInfo;
            if (loginBeanResponse.isSuccess()) {
                mLoginPresenter.savaUserData(loginBeanResponse);
                if (loginBeanResponse.getData().getSpecname() != null && !loginBeanResponse.getData().getSpecname().equals("")) {
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
                    values.put("dbpath", loginBeanResponse.getData().getSpecpathdb());

                    long id = database.insert("person", null, values);
                    //3. 关闭
                    database.close();
                    EventBus.getDefault().postSticky(loginBeanResponse);
                    startActivity(new Intent(this, MainActivity.class));
                } else {
                    EventBus.getDefault().postSticky(etPhone.getText().toString());
                    startActivity(new Intent(this, RegionSelectActivity.class));
                }

                finish();
            } else {
                Toast.makeText(this, loginBeanResponse.getMsg(), Toast.LENGTH_SHORT).show();
            }

        } catch (ClassCastException e) {
            Log.e(TAG, e.getMessage());
            Toast.makeText(LoginActivity.this, "登陆失败，原因：" + e.getMessage() + "。", Toast.LENGTH_SHORT).show();
        } finally {
            btLogin.setEnabled(true);

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
        Toast.makeText(LoginActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
        btLogin.setEnabled(true);
    }
}
