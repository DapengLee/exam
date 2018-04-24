package com.snow.selfexam.mvp.modle;

/**
 * Created by Administrator on 2018/1/18.
 */

public class RegisterBean {

    /**
     * msg : 此账号已存在，请直接登录!
     * success : false
     */

    private String msg;
    private boolean success;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
