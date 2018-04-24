package com.snow.selfexam.app.adapter;

import android.widget.ImageView;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.snow.selfexam.R;
import com.snow.selfexam.mvp.modle.HomeItemInfo;


import java.util.List;

public class HomeItemAdapter extends BaseQuickAdapter<HomeItemInfo> {
    public HomeItemAdapter(List<HomeItemInfo> data) {
        super(R.layout.item_home_app,data);
    }

    //BaseViewHolder:对ViewHolder的封装 不同的页面这个条目 类型与数量==>使用集合封装
    //SparseArray:可以看成HashMap<Integer,Object>
    @Override
    protected void convert(BaseViewHolder helper, HomeItemInfo item) {
         //项目编写展示逻辑 setText(参1 id 参2数值)
        helper.setText(R.id.item_tv_appname,item.appName)//
        .setText(R.id.item_tv_desc,item.url);
  //      .linkify(R.id.item_tv_desc);//以连接的方式来展示
//        .setOnClickListener(R.id.item_bnt_download, new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(mContext, "点击下载", Toast.LENGTH_SHORT).show();
//            }
//        });
       ImageView icon= helper.getView(R.id.item_iv_icon);//item.url
        //new BitmapUtil().display(icon,url)
//        helper.convertView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(mContext, "convertView OnClickListener ", Toast.LENGTH_SHORT).show();
//            }
//        });
    }
}
