package com.snow.selfexam.app.adapter;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;

import com.snow.selfexam.R;

import java.util.List;



/**
 * 分类的添加adapter
 *
 * @author 李大鹏
 * @version 1.0
 * @date 2016/1/11
 */
public class CateListAdapter extends BaseAdapter {

    private Context mContext;
    List<String> mCategories;
    private LayoutInflater mInflater;
    // default selected item
    private int mSelectedPos = 0;

    public CateListAdapter(Context context, List<String> mCategories) {
        super();
        this.mContext = context;
        this.mCategories = mCategories;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return (null == mCategories) ? 0 : mCategories.size();
    }

    @Override
    public String getItem(int position) {
        return (null == mCategories) ? null :  mCategories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (null == mContext) {
            return null;
        }

        if (null == mCategories || mCategories.size() == 0
                || mCategories.size() <= position) {
            return null;
        }

        final ViewHolder viewHolder;
        if (null == convertView) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.cate_list_item, parent,
                    false);

            viewHolder.cateCheckedTextView = (CheckedTextView) convertView
                    .findViewById(R.id.cate_tv);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.cateCheckedTextView.setText(mCategories.get(position));

        if (mSelectedPos == position) {
            viewHolder.cateCheckedTextView
                    .setTextColor(Color.RED);
        } else {
            viewHolder.cateCheckedTextView.setTextColor(Color
                    .BLACK);
        }

        return convertView;
    }

    class ViewHolder {
        CheckedTextView cateCheckedTextView;
    }

    public void setSelectedPos(int position) {
        this.mSelectedPos = position;
        notifyDataSetChanged();
    }

}
