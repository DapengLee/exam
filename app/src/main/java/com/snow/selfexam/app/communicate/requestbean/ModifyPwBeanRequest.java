package com.snow.selfexam.app.communicate.requestbean;

/**
 * Created by Administrator on 2018/1/15.
 * <p>
 * 手机号/phone,密码/password,新密码
 */

public class ModifyPwBeanRequest {
    public String phone;
    public String codeModify;
    public String newPassWord;

    public ModifyPwBeanRequest(String phone, String codeModify, String newPassWord) {
        this.phone = phone;
        this.codeModify = codeModify;
        this.newPassWord = newPassWord;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCodeModify() {
        return codeModify;
    }

    public void setCodeModify(String codeModify) {
        this.codeModify = codeModify;
    }

    public String getNewPassWord() {
        return newPassWord;
    }

    public void setNewPassWord(String newPassWord) {
        this.newPassWord = newPassWord;
    }

    @Override
    public String toString() {
        return "ModifyPwBeanRequest{" +
                "phone='" + phone + '\'' +
                ", codeModify='" + codeModify + '\'' +
                ", newPassWord='" + newPassWord + '\'' +
                '}';
    }
}
