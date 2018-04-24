package com.snow.selfexam.mvp.modle;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/10.
 */

public class AllDataSubjectInfo implements Serializable {
    private String subjectName;
    private int subjectIcon;
    private String subjectDes;


    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public int getSubjectIcon() {
        return subjectIcon;
    }

    public void setSubjectIcon(int subjectIcon) {
        this.subjectIcon = subjectIcon;
    }

    @Override
    public String toString() {
        return "AllDataSubjectInfo{" +
                "subjectName='" + subjectName + '\'' +
                ", subjectIcon=" + subjectIcon +
                ", subjectDes='" + subjectDes + '\'' +
                '}';
    }

    public String getSubjectDes() {
        return subjectDes;
    }

    public void setSubjectDes(String subjectDes) {
        this.subjectDes = subjectDes;
    }
}
