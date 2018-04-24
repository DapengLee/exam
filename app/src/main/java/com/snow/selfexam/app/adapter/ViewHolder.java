package com.snow.selfexam.app.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 实现字母索引的用到viewholder
 *
 * @author 李大鹏
 * @version 1.0
 * @date 2016/1/11
 */
public class ViewHolder {

    //代表当前行的view对象
    private View convertView;
    //用来替代Map<Integer,Object>的容器, 效率比map高
    private SparseArray<View> views;

    /**
     * 构造方法
     *
     * @param context  上下文
     * @param layoutId 布局的id
     * @param position item所对应的position
     */
    private ViewHolder(Context context, int layoutId, int position) {
        this.convertView = View.inflate(context, layoutId, null);
        //保存当前viewholder到convertView中
        this.convertView.setTag(this);
        views = new SparseArray<View>();
    }

    /**
     * 得到Viewholder对象
     *
     * @param context     上下文
     * @param convertView 返回的viewholder
     * @param layoutId    布局的id
     * @param position    item所对应的position
     * @return holder自定义viewholder
     */
    public static ViewHolder getHolder(Context context, View convertView,
                                       int layoutId, int position) {

        if (convertView == null) {
            return new ViewHolder(context, layoutId, position);
        } else {
            ViewHolder holder = (ViewHolder) convertView.getTag();
            return holder;
        }
    }

    /**
     * 根据视图id得到对应的视图对象
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = convertView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 得到当前对应的convertView
     *
     * @return
     */
    public View getConvertView() {
        return convertView;
    }

    public ViewHolder setText(int viewId, String text) {
        TextView textView = getView(viewId);
        textView.setText(text);
        return this;
    }

    /**
     * 设置drawable图片
     *
     * @param viewId
     * @param drawable
     */
    public ViewHolder setImageDrawable(int viewId, Drawable drawable) {
        ImageView imageView = getView(viewId);
        imageView.setImageDrawable(drawable);
        return this;
    }

    /**
     * 设置资源图片
     *
     * @param viewId
     * @param resourceId
     */
    public ViewHolder setImageResource(int viewId, int resourceId) {
        ImageView imageView = getView(viewId);
        imageView.setImageResource(resourceId);
        return this;
    }

    public ViewHolder setButton(int viewId) {
        ImageButton button = getView(viewId);
        //  button.setImageResource(resourceId);
        return this;
    }


    /**
     * 设置点击监听
     *
     * @param viewId
     * @param listener
     * @return
     */
    public ViewHolder setOnclickListener(int viewId, View.OnClickListener listener) {
        getView(viewId).setOnClickListener(listener);
        return this;
    }

    /**
     * 设置指定id的视图可见性
     */
    public void setVisibility(int viewId, int visibility) {
        getView(viewId).setVisibility(visibility);
    }

    //还可以定义一些其它的set方法来为视图设置数据
}
