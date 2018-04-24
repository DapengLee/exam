package com.snow.selfexam.mvp.presenter;

import android.content.Context;
import android.util.Log;

import com.snow.selfexam.app.base.BasePresenter;
import com.snow.selfexam.app.communicate.CommCallBack;
import com.snow.selfexam.app.communicate.NormalException;
import com.snow.selfexam.app.communicate.requestbean.SubmitDataBeanRequest;
import com.snow.selfexam.app.config.Constants;
import com.snow.selfexam.app.utils.JsonUtil;
import com.snow.selfexam.mvp.modle.UserInfoBean;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by Administrator on 2018/3/9.
 */

public class CategoryMorePresenter extends BasePresenter {

    public void submitData(Context context, SubmitDataBeanRequest submit, final CommCallBack commCallBack) {

        OkHttpUtils
                .post()
                .url(Constants.URLS.BASE_URL + Constants.URLS.CHOICE_URL)
                .addParams("phone", submit.getSubmitDataID())
                .addParams("specid", submit.getSubmitSpecID())
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
                        UserInfoBean userInfoBean = JsonUtil.parseJsonToBean(response, UserInfoBean.class);

                        commCallBack.onBeanInfo(userInfoBean);
                    }
                });
    }
}
