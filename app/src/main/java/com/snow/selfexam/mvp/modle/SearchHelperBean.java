package com.snow.selfexam.mvp.modle;

public class SearchHelperBean {

    private String  tbName;
    private String  keyWord;

    public SearchHelperBean(String tbName, String keyWord) {
        this.tbName = tbName;
        this.keyWord = keyWord;
    }

    public String getTbName() {
        return tbName;
    }

    public void setTbName(String tbName) {
        this.tbName = tbName;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    @Override
    public String toString() {
        return "SearchHelperBean{" +
                "tbName='" + tbName + '\'' +
                ", keyWord='" + keyWord + '\'' +
                '}';
    }
}
