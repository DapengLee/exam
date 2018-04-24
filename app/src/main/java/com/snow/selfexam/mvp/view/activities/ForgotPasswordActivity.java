package com.snow.selfexam.mvp.view.activities;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.snow.selfexam.R;


/*
*
* 忘记密码
* */
public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText et_forgot_username, et_forgot_password, et_forgot_password_sure, et_forgot_answer;
    private Button btn_forgot_verification, btn_forgot_submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        init();
    }

    private void init() {
        et_forgot_username= (EditText) findViewById(R.id.activity_forgot_username);
        et_forgot_password= (EditText) findViewById(R.id.activity_forgot_password);
        et_forgot_password_sure= (EditText) findViewById(R.id.activity_forgot_password_sure);
        et_forgot_answer= (EditText) findViewById(R.id.activity_forgot_answer);
        btn_forgot_verification= (Button) findViewById(R.id.activity_forgot_btn_verification);
        btn_forgot_submit= (Button) findViewById(R.id.activity_forgot_btn_submit);
        btn_forgot_verification.setOnClickListener(this);
        btn_forgot_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //获取短信
            case R.id.activity_forgot_btn_verification:

                break;
            //提交
            case R.id.activity_forgot_btn_submit:
                String forgot_username = et_forgot_username.getText().toString();
                String forgot_password = et_forgot_password.getText().toString();
                String forgot_password_sure = et_forgot_password_sure.getText().toString();
                String forgot_answer = et_forgot_answer.getText().toString();
                if (forgot_username.isEmpty()) {
                    Toast.makeText(this, "手机号不能为空!", Toast.LENGTH_LONG).show();
                    return;
                }
                if (forgot_password.isEmpty()) {
                    Toast.makeText(this, "密码不能为空!", Toast.LENGTH_LONG).show();
                    return;
                }
                if (forgot_password_sure.isEmpty()) {
                    Toast.makeText(this, "确认密码不能为空!", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!forgot_password.equals(forgot_password_sure)) {
                    Toast.makeText(this, "两次密码输入不一致！", Toast.LENGTH_LONG).show();
                    return;
                }

                if (forgot_answer.isEmpty()) {
                    Toast.makeText(this, "您还没有输入短信验证码", Toast.LENGTH_LONG).show();
                    return;
                }
                /*SQLiteHelper sqlite=new SQLiteHelper(this,"systems.db");
                //以读写的方式获取数据库对象
                SQLiteDatabase dbWritable=sqlite.getWritableDatabase();
                *//*Cursor c=dbWritable.rawQuery("select *from user where username=?",
                        new String[] { forgot_username });
                while (c.moveToFirst()){
                    Toast.makeText(this, "该用户名已被注册！",Toast.LENGTH_LONG).show();

                    return;
                }*//*
                ContentValues cv=new ContentValues();
                //用户名
                cv.put("username", forgot_username);
                //密码
                cv.put("password", forgot_password);
                //报名机构
                cv.put("applicants", forgot_password_sure);
                //短信
                cv.put("verification", forgot_answer);
                //用户
                dbWritable.update("user",cv,"username=?",new String[]{forgot_username});
                cv.clear();
                //c.close();
                dbWritable.close();*/
                Toast.makeText(this, "修改成功，跳转至登录界面！", Toast.LENGTH_LONG).show();
                finish();
                break;
        }
    }
}
