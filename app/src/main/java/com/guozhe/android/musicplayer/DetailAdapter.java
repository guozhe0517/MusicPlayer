package com.guozhe.android.musicplayer;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.guozhe.android.musicplayer.domain.Music;

import java.util.List;

/**
 * Created by guozhe on 2017. 6. 18..
 */

public class DetailAdapter extends PagerAdapter {
    List<Music.Item> datas;
    public DetailAdapter(List<Music.Item> datas){
        this.datas =datas;

    }
    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_pager_item,null);
        ImageView imageView = (ImageView)view.findViewById(R.id.imageView);
        TextView textView = (TextView)view.findViewById(R.id.textView);
        Glide.with(container.getContext())
                .load(datas.get(position).albumart)
                .into(imageView);
        textView.setText(datas.get(position).title);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
