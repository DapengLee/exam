package com.snow.selfexam.app.communicate.requestbean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/11/18.
 */

public class LoginBeanRequest implements Serializable{
    private String userName;
    private String passWord;

    public LoginBeanRequest(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
