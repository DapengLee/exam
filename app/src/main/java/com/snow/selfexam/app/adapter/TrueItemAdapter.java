package com.snow.selfexam.app.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.snow.selfexam.R;
import com.snow.selfexam.mvp.modle.TrueItemInfo;

import java.util.List;

public class TrueItemAdapter extends BaseQuickAdapter<TrueItemInfo> {
    public TrueItemAdapter(List<TrueItemInfo> data) {
        super(R.layout.item_true_app, data);
    }

    //BaseViewHolder:对ViewHolder的封装 不同的页面这个条目 类型与数量==>使用集合封装
    //SparseArray:可以看成HashMap<Integer,Object>
    @Override
    protected void convert(BaseViewHolder helper, TrueItemInfo item) {
        //项目编写展示逻辑 setText(参1 id 参2数值)
        helper.setText(R.id.item_tv_con, item.getTitleName());//
    }
}
