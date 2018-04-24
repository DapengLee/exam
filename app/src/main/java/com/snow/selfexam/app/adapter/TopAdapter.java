package com.snow.selfexam.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;


import com.facebook.drawee.view.SimpleDraweeView;
import com.snow.selfexam.R;
import com.zanlabs.widget.infiniteviewpager.InfinitePagerAdapter;

import java.util.List;



/**
 * Created by Administrator on 2016/12/5.
 */

public class TopAdapter extends InfinitePagerAdapter
{
    private Context mContext;
    //    private List<Banners> mList;
    private List<Integer> banner_img;

    public TopAdapter(Context context, List<Integer> banner_img)
    {
        mContext = context;
        this.banner_img = banner_img;
    }

    @Override
    public int getItemCount()
    {
//        return mList == null ? 0 : mList.size();
        return banner_img == null ? 0 : banner_img.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup container)
    {
        View view;
        ViewsHolder holder;
        if (convertView == null)
        {
            holder = new ViewsHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.banner_item, container, false);


            holder.mDraweeView=view.findViewById(R.id.top_image);
            view.setTag(holder);
        } else
        {
            view = convertView;
            holder = (ViewsHolder) view.getTag();
        }
      //  holder.mDraweeView.setImageURI(banner_img.get(position));
        holder.mDraweeView.setImageResource(banner_img.get(position));
        view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(mContext,"点击了我",Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    class ViewsHolder
    {

        ImageView mDraweeView;

    }
}
