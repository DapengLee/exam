package com.snow.selfexam.mvp.modle;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/11/18.
 */

public class UserInfoBean implements Serializable {
    private static final long serialVersionUID = -4770956715131174702L;

    /**
     * data : {"id":113,"idcard":"1111","password":"21232f297a57a5a743894a0e4a801fc3","phone":"17631404598","remarks":"","specid":6,"specname":"会计学","specpathdb":"http://192.168.1.136:8888/db/kjx.db","vercode":"130828190089034567"}
     * msg : 获取成功！
     * success : true
     */

    private DataBean data;
    private String msg;
    private boolean success;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

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

    public static class DataBean {
        /**
         * id : 113
         * idcard : 1111
         * password : 21232f297a57a5a743894a0e4a801fc3
         * phone : 17631404598
         * remarks :
         * specid : 6
         * specname : 会计学
         * specpathdb : http://192.168.1.136:8888/db/kjx.db
         * vercode : 130828190089034567
         */

        private int id;
        private String idcard;
        private String password;
        private String phone;
        private String remarks;
        private int specid;
        private String specname;
        private String specpathdb;
        private String vercode;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getIdcard() {
            return idcard;
        }

        public void setIdcard(String idcard) {
            this.idcard = idcard;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public int getSpecid() {
            return specid;
        }

        public void setSpecid(int specid) {
            this.specid = specid;
        }

        public String getSpecname() {
            return specname;
        }

        public void setSpecname(String specname) {
            this.specname = specname;
        }

        public String getSpecpathdb() {
            return specpathdb;
        }

        public void setSpecpathdb(String specpathdb) {
            this.specpathdb = specpathdb;
        }

        public String getVercode() {
            return vercode;
        }

        public void setVercode(String vercode) {
            this.vercode = vercode;
        }
    }
}
