package com.snow.selfexam.app.communicate.requestbean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/3/11.
 */

public class SubmitDataBeanRequest implements Serializable {

    private String submitDataID;
    private String submitSpecID;

    public SubmitDataBeanRequest(String submitDataID, String submitSpecID) {
        this.submitDataID = submitDataID;
        this.submitSpecID = submitSpecID;
    }

    public String getSubmitDataID() {
        return submitDataID;
    }

    public void setSubmitDataID(String submitDataID) {
        this.submitDataID = submitDataID;
    }

    public String getSubmitSpecID() {
        return submitSpecID;
    }

    public void setSubmitSpecID(String submitSpecID) {
        this.submitSpecID = submitSpecID;
    }

}
