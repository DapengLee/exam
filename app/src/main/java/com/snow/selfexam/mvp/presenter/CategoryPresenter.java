package com.snow.selfexam.mvp.presenter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.snow.selfexam.app.adapter.CateListAdapter;
import com.snow.selfexam.app.base.BasePresenter;
import com.snow.selfexam.app.communicate.CommCallBack;
import com.snow.selfexam.app.communicate.NormalException;
import com.snow.selfexam.app.config.Constants;
import com.snow.selfexam.app.utils.JsonUtil;
import com.snow.selfexam.mvp.modle.CategoryBean;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/9/9 0009.
 */

public class CategoryPresenter  extends BasePresenter {

    private int mFromY = 0;

    /**
     * 实现侧滑菜单
     * @param mCateListAdapter  侧滑菜单的装配
     * @param view                数据view
     * @param i                   代表的position
     * @param cateIndicatorImg  指示器的图片
     */
    public void setLeftMenu(CateListAdapter mCateListAdapter, View view, int i, ImageView cateIndicatorImg) {
        if (null != mCateListAdapter) {
            mCateListAdapter.setSelectedPos(i);
        }
        int toY = view.getTop() + view.getHeight() / 2;
        doAnimation(toY,cateIndicatorImg);
    }

    /**
     * 实现侧滑菜单
     *
     * @param toY                滑向的指示器
     * @param cateIndicatorImg 滑向的指示器的图标
     */
    private void doAnimation(int toY,ImageView cateIndicatorImg) {
        int cateIndicatorY = cateIndicatorImg.getTop()
                + cateIndicatorImg.getMeasuredHeight() / 2;
        TranslateAnimation animation = new TranslateAnimation(0, 0, mFromY
                - cateIndicatorY, toY - cateIndicatorY);
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        animation.setFillAfter(true);
        animation.setDuration(400);
        cateIndicatorImg.startAnimation(animation);
        mFromY = toY;
    }


    //获取专业的列表

    public void getSpec(Context context, final CommCallBack commCallBack) {

      /*  OkHttpUtils
                .get()
                .url(Constants.URLS.BASE_URL + Constants.URLS.DB_URL)
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
                        CategoryBean categoryBean = JsonUtil.parseJsonToBean(response, CategoryBean.class);

                        commCallBack.onBeanInfo(categoryBean);

                    }
                });*/
    }
}
