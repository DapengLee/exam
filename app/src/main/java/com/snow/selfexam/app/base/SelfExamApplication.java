package com.snow.selfexam.app.base;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.mob.MobSDK;

/**
 * Created by amew_000 on 2016/12/7.
 */
public class SelfExamApplication extends Application{
    private static Context mContext;
    private static Handler mMainThreadHandler;
    private static int mMainThreadId;

    /**
     * 得到上下文
     * @return
     */
    public static Context getContext() {
        return mContext;
    }

    /**
     * 得到主线程handler
     * @return
     */
    public static Handler getMainThreadHandler() {
        return mMainThreadHandler;
    }

    /**
     * 得到主线程id
     * @return
     */
    public static int getMainThreadId() {
        return mMainThreadId;
    }

    @Override
    public void onCreate() {
        //上下文
        mContext = getApplicationContext();
        //主线程Handler
        mMainThreadHandler = new Handler();
        //主线程Id
        mMainThreadId = android.os.Process.myTid();
        MobSDK.init(this);
        Fresco.initialize(this);
        super.onCreate();
    }
}
