package com.snow.selfexam.mvp.modle;

import java.io.Serializable;
import java.util.List;

public class BannerBean implements Serializable{

    /**
     * data : [{"id":1,"indeximg":"http://192.168.1.156:8888/style/images/banner/banner.jpg"},{"id":2,"indeximg":"http://192.168.1.156:8888/style/images/banner/banner1.jpg"},{"id":3,"indeximg":"http://192.168.1.156:8888/style/images/banner/banner2.jpg"}]
     * msg : 获取成功！
     * success : true
     */

    private String msg;
    private boolean success;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * indeximg : http://192.168.1.156:8888/style/images/banner/banner.jpg
         */

        private int id;
        private String indeximg;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getIndeximg() {
            return indeximg;
        }

        public void setIndeximg(String indeximg) {
            this.indeximg = indeximg;
        }
    }
}
