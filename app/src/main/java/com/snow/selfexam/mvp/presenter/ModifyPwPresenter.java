package com.snow.selfexam.mvp.presenter;

import android.content.Context;
import android.util.Log;

import com.snow.selfexam.app.base.BasePresenter;
import com.snow.selfexam.app.communicate.CommCallBack;
import com.snow.selfexam.app.communicate.NormalException;
import com.snow.selfexam.app.communicate.requestbean.ModifyPwBeanRequest;
import com.snow.selfexam.app.config.Constants;
import com.snow.selfexam.app.utils.JsonUtil;
import com.snow.selfexam.mvp.modle.ModifyReturnBean;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/9/14 0014.
 * phone	手机号	String	1
 * oldpsw	原密码	String	1
 * password	新密码	String	1
 */

public class ModifyPwPresenter extends BasePresenter {
    public void sendData(Context context, ModifyPwBeanRequest modifyPwBeanRequest, final CommCallBack commCallBack) {
        OkHttpUtils
                .post()
                .url(Constants.URLS.BASE_URL + Constants.URLS.FIX_URL)
                .addParams("phone", modifyPwBeanRequest.getPhone())
                .addParams("vercode", modifyPwBeanRequest.getCodeModify())
                .addParams("password", modifyPwBeanRequest.getNewPassWord())
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
                        ModifyReturnBean modifyReturnBean = JsonUtil.parseJsonToBean(response, ModifyReturnBean.class);

                        commCallBack.onBeanInfo(modifyReturnBean);

                    }

                });

    }
}
