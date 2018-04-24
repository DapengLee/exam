package com.snow.selfexam.mvp.modle;

import java.util.List;

/**
 * Created by Administrator on 2018/3/5.
 */

public class CategoryBean {


    @Override
    public String toString() {
        return "CategoryBean{" +
                "msg='" + msg + '\'' +
                ", success=" + success +
                ", data=" + data +
                '}';
    }

    /**
     * data : [{"id":1,"pathdb":"/sql/mysql/Software.db","specimg":"/img/solf.png","specname":"软件工程"},{"id":2,"pathdb":"/db/goodexamdb.db","specimg":"/img/rlzy.png","specname":"人力资源管理"},{"id":3,"pathdb":"/sql/mysql/jjfs.db","specimg":"/img/jjf.png","specname":"经济法"}]
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
        @Override
        public String toString() {
            return "DataBean{" +
                    "id=" + id +
                    ", pathdb='" + pathdb + '\'' +
                    ", specimg='" + specimg + '\'' +
                    ", specname='" + specname + '\'' +
                    '}';
        }

        /**
         * id : 1
         * pathdb : /sql/mysql/Software.db
         * specimg : /img/solf.png
         * specname : 软件工程
         */

        private int id;
        private String pathdb;
        private String specimg;
        private String specname;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPathdb() {
            return pathdb;
        }

        public void setPathdb(String pathdb) {
            this.pathdb = pathdb;
        }

        public String getSpecimg() {
            return specimg;
        }

        public void setSpecimg(String specimg) {
            this.specimg = specimg;
        }

        public String getSpecname() {
            return specname;
        }

        public void setSpecname(String specname) {
            this.specname = specname;
        }
    }
}
