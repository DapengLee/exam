package com.snow.selfexam.app.communicate.requestbean;

/**
 * Created by Administrator on 2018/1/15.
 *
 * 手机号/phone,密码/password,身份证/idCard,验证码verCode
 */

public class RegisterBeanRequest {
    public   String phone;
    public String  password;
    public   String  idCard;
    public String verCode;

    public RegisterBeanRequest(String phone, String password, String idCard, String verCode) {
        this.phone = phone;
        this.password = password;
        this.idCard = idCard;
        this.verCode = verCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getVerCode() {
        return verCode;
    }

    public void setVerCode(String verCode) {
        this.verCode = verCode;
    }


    @Override
    public String toString() {
        return "RegisterBeanRequest{" +
                "phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", idCard='" + idCard + '\'' +
                ", verCode='" + verCode + '\'' +
                '}';
    }
}
