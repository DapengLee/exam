package com.snow.selfexam.mvp.presenter;

import android.os.SystemClock;
import android.support.v7.widget.GridLayoutManager;

import com.snow.selfexam.app.adapter.CatoryChildAdapter;
import com.snow.selfexam.app.base.BasePresenter;
import com.snow.selfexam.app.base.IPresenter;
import com.snow.selfexam.app.utils.UIUtils;
import com.snow.selfexam.mvp.modle.CatagoryItemInfo;
import com.snow.selfexam.mvp.modle.HomeItemInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/12 0012.
 */

public class CatagoryChildPresenter extends BasePresenter {


    public List<CatagoryItemInfo> getAppList(int type) {

        //get请求服务
        List<CatagoryItemInfo> list=new ArrayList<>();



        switch (type) {
            case 0:

                for(int i=0;i<10;i++)
                {
                    CatagoryItemInfo info=new CatagoryItemInfo();
                    info.appName="自考 人力";
                    list.add(info);
                }


                break;
            case 1:
                // tvFragmentChildCatory.setText("资格证");
                for(int i=0;i<10;i++)
                {
                    CatagoryItemInfo info=new CatagoryItemInfo();
                    info.appName="资格证 人力";
                    list.add(info);
                }
                break;
            case 2:

                for(int i=0;i<10;i++)
                {
                    CatagoryItemInfo info=new CatagoryItemInfo();
                    info.appName="成教 人力";
                    list.add(info);
                }
                //tvFragmentChildCatory.setText("成交");
                break;

        }
        return list;
    }
}
