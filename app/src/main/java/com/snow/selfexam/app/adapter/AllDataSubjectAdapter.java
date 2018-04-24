package com.snow.selfexam.app.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.snow.selfexam.R;
import com.snow.selfexam.mvp.modle.CategoryBean;
import com.snow.selfexam.mvp.view.activities.AllDataSubjectActivity;

import java.util.List;

/**
 * Created by Administrator on 2018/3/11.
 */

public class AllDataSubjectAdapter extends BaseAdapter {
    private Context mContext;
    private List<CategoryBean.DataBean> subjectInfos;


    public AllDataSubjectAdapter(Context mContext, List<CategoryBean.DataBean> subjectInfos) {
        this.mContext = mContext;
        this.subjectInfos = subjectInfos;
    }

    @Override
    public int getCount() {
        return subjectInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return subjectInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = View.inflate(mContext, R.layout.item_child_cataory, null);
        SimpleDraweeView sv = itemView.findViewById(R.id.category_simple_mine);
        TextView title = itemView.findViewById(R.id.tv_catory_child_item);
        CategoryBean.DataBean allDataSubjectInfo = subjectInfos.get(position);
        sv.setImageURI(allDataSubjectInfo.getSpecimg());
        title.setText(allDataSubjectInfo.getSpecname());
        return itemView;
    }
}
