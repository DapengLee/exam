package com.snow.selfexam.app.utils;

import android.os.CountDownTimer;
import android.widget.Button;

/**
 * Created by Administrator on 2017/12/22.
 */

public class DataCountDownTimer extends CountDownTimer {

    private Button  btVerify;

    public DataCountDownTimer(long millisInFuture, long countDownInterval, Button  btVerify) {
        super(millisInFuture, countDownInterval);
        this.btVerify=btVerify;
    }

    //计时过程
    @Override
    public void onTick(long l) {
        //防止计时过程中重复点击
        btVerify.setClickable(false);
        btVerify.setText(l/1000+"s");

    }

    //计时完毕的方法
    @Override
    public void onFinish() {
        //重新给Button设置文字
        btVerify.setText("重新获取");
        //设置可点击
        btVerify.setClickable(true);
    }
}
