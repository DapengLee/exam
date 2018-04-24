package com.snow.selfexam.app.db;

import com.snow.selfexam.app.utils.PinYinUtils;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/3/29.
 */

public class SearchQuestionBean implements  Comparable<SearchQuestionBean>, Serializable{
    private String questionId;// 题目ID
    private String description;// 题目描述
    private int questionType;// 题目类型
    private String questionA;
    private String questionB;
    private String questionC;
    private String questionD;

    public String getQuestionA() {
        return questionA;
    }

    public void setQuestionA(String questionA) {
        this.questionA = questionA;
    }

    public String getQuestionB() {
        return questionB;
    }

    public void setQuestionB(String questionB) {
        this.questionB = questionB;
    }

    public String getQuestionC() {
        return questionC;
    }

    public void setQuestionC(String questionC) {
        this.questionC = questionC;
    }

    public String getQuestionD() {
        return questionD;
    }

    public void setQuestionD(String questionD) {
        this.questionD = questionD;
    }

    public String getQuestionE() {
        return questionE;
    }

    public void setQuestionE(String questionE) {
        this.questionE = questionE;
    }

    private String questionE;


    private String knowledgePointAnswer; // 知识点名
    public String pinyin;//拼音排序

    public SearchQuestionBean() {
    }

    public SearchQuestionBean(String questionId, String description, int questionType, String knowledgePointAnswer) {
        this.questionId = questionId;
        this.description = description;
        this.questionType = questionType;
        this.knowledgePointAnswer = knowledgePointAnswer;
    }

    public SearchQuestionBean(String description, String questionA, String questionB, String questionC, String questionD, String questionE, String knowledgePointAnswer) {
        this.description = description;
        this.questionA = questionA;
        this.questionB = questionB;
        this.questionC = questionC;
        this.questionD = questionD;
        this.questionE = questionE;
        this.knowledgePointAnswer = knowledgePointAnswer;
        pinyin = PinYinUtils.getPinYin(description.replaceAll("\\d+","").replace(".","").trim());
    }

    @Override
    public String toString() {
        return "SearchQuestionBean{" +
                "questionId='" + questionId + '\'' +
                ", description='" + description + '\'' +
                ", questionType=" + questionType +
                ", knowledgePointAnswer='" + knowledgePointAnswer + '\'' +
                '}';
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuestionType() {
        return questionType;
    }

    public void setQuestionType(int questionType) {
        this.questionType = questionType;
    }

    public String getKnowledgePointAnswer() {
        return knowledgePointAnswer;
    }

    public void setKnowledgePointAnswer(String knowledgePointAnswer) {
        this.knowledgePointAnswer = knowledgePointAnswer;
    }
    @Override
    public int compareTo(SearchQuestionBean another) {
        return this.pinyin.compareTo(another.pinyin);
    }
}
