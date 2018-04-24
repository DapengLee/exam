package com.snow.selfexam.app.utils;

import android.content.Context;
import android.content.res.Resources;

import com.snow.selfexam.app.base.SelfExamApplication;


/**
 * Created by amew_000 on 2016/11/23.
 */
public class UIUtils {
    /**
     * 得到上下文
     */
    public static Context getContext() {
        return SelfExamApplication.getContext();
    }

    /**
     * 得到resource对象
     *
     * @return
     */
    public static Resources getResources() {
        return getContext().getResources();
    }

    /**
     * 得到string.xml中的字符串信息
     */
    public static String getString(int resId) {
        return getContext().getString(resId);
    }

    /**
     * 得到字符串数组信息
     */
    public static String[] getStrings(int resId) {
        return getResources().getStringArray(resId);
    }

    /**
     * 得到color.xml中的颜色信息
     */
    public static int getColor(int resId) {
        return getResources().getColor(resId);
    }

    /**
     * 得到应用程序包名
     */
    public static String getPackageName() {
        return getContext().getPackageName();
    }

    /**
     * px转换为dp
     */
    public static int px2Dip(int px) {
        // 2.  px/dp = density
        //取得当前手机px和dp的倍数关系
        float density = getResources().getDisplayMetrics().density;
        int dip = (int) (px / density + .5f);
        return dip;
    }
    /**
     * dp转换为px
     */
    public static int dip2Px(int dip) {
        float density = getResources().getDisplayMetrics().density;
        int px = (int) (dip * density + .5f);
        return px;
    }
}
