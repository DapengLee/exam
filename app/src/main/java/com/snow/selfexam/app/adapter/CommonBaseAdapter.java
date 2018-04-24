package com.snow.selfexam.app.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;


/**
 * * 待办事项的页面(全部) 继承了BasePager
 *
 * @author 李大鹏
 * @version 1.0
 * @date 2016/1/11
 */

public abstract class CommonBaseAdapter<T> extends BaseAdapter {

    private Context context;
    private List<T> data;
    private int layoutId;

    public CommonBaseAdapter(Context context, List<T> data, int layoutId) {
        this.context = context;
        this.data = data;
        this.layoutId = layoutId;
    }

    /**
     * 数据的个数
     *
     * @return 数据的个数
     */
    @Override
    public int getCount() {
        return data.size();
    }

    /**
     * 数据具体的类
     *
     * @param position 每一个item的标识(从0开始)
     * @return 返回每一个数据
     */
    @Override
    public T getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //得到Viewholder对象
        ViewHolder holder = ViewHolder.getHolder(context, convertView,
                layoutId, position);
        //调用未实现的抽象方法设置数据
        convert(holder, position);

        //返回holder中的convertView
        return holder.getConvertView();
    }

    /**
     * 设置视图数据的抽象方法, 由具体的adapter子类来实现
     *
     * @param holder   自定义的ViewHolder
     * @param position 具体的item的position(标识从0开始)
     */
    public abstract void convert(ViewHolder holder, int position);
}
