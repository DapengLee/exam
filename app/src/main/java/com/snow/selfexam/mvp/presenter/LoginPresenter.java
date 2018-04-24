package com.snow.selfexam.mvp.presenter;

import android.content.Context;
import android.util.Log;
import android.widget.EditText;

import com.snow.selfexam.app.base.BasePresenter;
import com.snow.selfexam.app.communicate.CommCallBack;
import com.snow.selfexam.app.communicate.NormalException;
import com.snow.selfexam.app.communicate.requestbean.LoginBeanRequest;
import com.snow.selfexam.app.config.Constants;
import com.snow.selfexam.app.utils.JsonUtil;
import com.snow.selfexam.app.utils.SmallTools;
import com.snow.selfexam.app.utils.ToastUtil;
import com.snow.selfexam.app.utils.UIUtils;
import com.snow.selfexam.mvp.modle.UserInfoBean;
import com.snow.selfexam.mvp.view.activities.LoginActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/9/14 0014.
 */

public class LoginPresenter extends BasePresenter {

    public void login(Context context, LoginBeanRequest login, final CommCallBack commCallBack) {

        OkHttpUtils
                .post()
                .url(Constants.URLS.BASE_URL + Constants.URLS.LOGIN_URL)
                .addParams("phone", login.getUserName())
                .addParams("password", login.getPassWord())
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

    public void cancel(LoginActivity loginActivity) {
        OkHttpUtils.getInstance().cancelTag(loginActivity);
    }

    public void savaUserData(UserInfoBean loginBeanResponse) {
       /* // 将用户信息放入通用信息里
        UserInfoBean userInfo = new UserInfoBean(loginBeanResponse.getUserID(),
                loginBeanResponse.getUserName(), loginBeanResponse.getDepartmentType(),
                loginBeanResponse.getDepartmentName(), loginBeanResponse.getDepartmentSchool(),
                loginBeanResponse.getMobile());
        ActivityBaseInfoHelper.setUserInfo(userInfo);*/
        // 发送粘性事件
        EventBus.getDefault().postSticky(loginBeanResponse);
    }

    public boolean check(EditText etPhone, EditText etPassword) {
        final String phone = etPhone.getText().toString().trim();
        final String pwd = etPassword.getText().toString().trim();
        if (!SmallTools.isTEL(phone)) {
            ToastUtil.showToast(UIUtils.getContext(), "请输入正确的手机号码");
            return false;
        }
        if (pwd.length() < 1) {
            ToastUtil.showToast(UIUtils.getContext(), "请输入正确的密码");
            return false;
        }
        return true;
    }
}
