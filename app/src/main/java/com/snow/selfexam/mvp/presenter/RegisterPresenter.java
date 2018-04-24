package com.snow.selfexam.mvp.presenter;

import android.content.Context;
import android.util.Log;

import com.snow.selfexam.app.base.BasePresenter;
import com.snow.selfexam.app.communicate.CommCallBack;
import com.snow.selfexam.app.communicate.NormalException;
import com.snow.selfexam.app.config.Constants;
import com.snow.selfexam.app.utils.JsonUtil;
import com.snow.selfexam.mvp.modle.RegisterBean;
import com.snow.selfexam.app.communicate.requestbean.RegisterBeanRequest;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/9/14 0014.
 * 手机号/phone,密码/password,身份证/idCard,验证码verCode
 */

public class RegisterPresenter extends BasePresenter {
    public void register(Context context, RegisterBeanRequest regist, final CommCallBack commCallBack) {
        OkHttpUtils
                .post()
                .url(Constants.URLS.BASE_URL + Constants.URLS.REGIST_URL)
                .addParams("phone", regist.getPhone())
                .addParams("password", regist.getPassword())
                .addParams("idcard",regist.getIdCard())
                .addParams("vercode",regist.getVerCode())
                .tag(context)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        commCallBack.onException(new NormalException(e));
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.d(TAG, "Response is: " + response.toString());
                        // 不需要返回值时，不做任何处理
                        if (commCallBack == null) {
                            return;
                        }
                        RegisterBean registerBean = JsonUtil.parseJsonToBean(response, RegisterBean.class);

                        commCallBack.onBeanInfo(registerBean);

                    }
                });
    }
}
