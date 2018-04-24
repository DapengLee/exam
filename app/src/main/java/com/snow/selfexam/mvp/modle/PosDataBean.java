package com.snow.selfexam.mvp.modle;

import java.lang.ref.PhantomReference;

public class PosDataBean {

    private String  typeData;
    private String  isJiXu;
    private String  exam;
    private String  examData;

    public boolean getIsExercise() {
        return isExercise;
    }

    public void setIsExercise(boolean isExercise) {
        this.isExercise = isExercise;
    }

    private boolean  isExercise;

    public String getExam() {
        return exam;
    }

    public void setExam(String exam) {
        this.exam = exam;
    }

    public PosDataBean(String typeData, String isJiXu, String exam, String examData) {
        this.typeData = typeData;
        this.isJiXu = isJiXu;
        this.exam = exam;
        this.examData = examData;
    }

    public PosDataBean(String typeData, String isJiXu, String exam, String examData, boolean isExercise) {
        this.typeData = typeData;
        this.isJiXu = isJiXu;
        this.exam = exam;
        this.examData = examData;
        this.isExercise = isExercise;
    }

    public String getTypeData() {
        return typeData;
    }

    public void setTypeData(String typeData) {
        this.typeData = typeData;
    }

    public String getIsJiXu() {
        return isJiXu;
    }

    public void setIsJiXu(String isJiXu) {
        this.isJiXu = isJiXu;
    }

    public String getExamData() {
        return examData;
    }

    public void setExamData(String examData) {
        this.examData = examData;
    }

    @Override
    public String toString() {
        return "PosDataBean{" +
                "typeData='" + typeData + '\'' +
                ", isJiXu='" + isJiXu + '\'' +
                ", exam='" + exam + '\'' +
                ", examData='" + examData + '\'' +
                '}';
    }
}
