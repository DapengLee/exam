package com.snow.selfexam.mvp.presenter;

import com.snow.selfexam.app.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/9 0009.
 */

public class LearnPresenter extends BasePresenter {

    public static  List<String> getType(int type) {
        List<String> examType = new ArrayList<>();

        if (type==1){
            examType.add("自考");
            /*examType.add("成考");
            examType.add("资格证");*/
        }else if(type==2){
            examType.add("人力资源管理".substring(0,2));
            examType.add("工商企业管理".substring(0,2));
            examType.add("会计".substring(0,2));
            examType.add("证券投资与管理".substring(0,2));
        }else {
            examType.add("中国近代史");
            examType.add("英语");
            examType.add("数学");
            examType.add("语文");
        }


        return examType;

    }
}
