package com.snow.selfexam.app.bank.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.widget.Toast;

import com.snow.selfexam.app.bank.activity.ChoiceActivity;
import com.snow.selfexam.app.bank.bean.QuestionBean;
import com.snow.selfexam.app.bank.fragment.QuestionItemFragment;
import com.snow.selfexam.app.bank.fragment.ScantronItemFragment;
import com.snow.selfexam.app.utils.UIUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class ItemAdapter extends FragmentStatePagerAdapter {
    Context context;
    List<QuestionBean> questionlist;
    private String infoType;
    private Set<QuestionBean>  errQuestionData;
    private String exam;
    // 3 接收粘性事件
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void StickyEventBus(QuestionBean errQuestion) {
        errQuestionData.add(errQuestion);
       // Toast.makeText(UIUtils.getContext(), " errQuestionData.size()" + errQuestionData.size(), Toast.LENGTH_SHORT).show();
    }

    public ItemAdapter(FragmentManager supportFragmentManager, List<QuestionBean> questionlist, String  infoType, String type) {
        super(supportFragmentManager);
        EventBus.getDefault().register(this);
        this.questionlist=questionlist;
        this.infoType=infoType;
        this.exam=type;
        errQuestionData=new HashSet<>();
    }

    @Override
    public Fragment getItem(int arg0) {
        if (arg0 == questionlist.size()) {
            return new ScantronItemFragment(questionlist,errQuestionData,exam);
        }
        return new QuestionItemFragment(arg0,questionlist,infoType,exam);
    }


    @Override
    public int getCount() {
        return questionlist.size() + 1;
    }
}
