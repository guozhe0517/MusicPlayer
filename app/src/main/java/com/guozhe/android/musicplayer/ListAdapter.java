package com.guozhe.android.musicplayer;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.guozhe.android.musicplayer.ListFragment.OnListFragmentInteractionListener;
import com.guozhe.android.musicplayer.domain.Music;
import com.guozhe.android.musicplayer.dummy.DummyContent.DummyItem;

import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static com.guozhe.android.musicplayer.Player.playerStatus;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {


    private final OnListFragmentInteractionListener mListener;
    private final List<Music.Item> datas;
    private Context context = null;

    public ListAdapter(List<Music.Item> items, OnListFragmentInteractionListener listener) {
        mListener = listener;
        datas = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(context == null)
            context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Music.Item item= datas.get(position);
        holder.position = position;
        holder.musicUri = datas.get(position).musicUri;
        holder.mIdView.setText(datas.get(position).id);
        holder.mContentView.setText(datas.get(position).title);


        Glide
                .with(context)
                .load(datas.get(position).albumart)
                .bitmapTransform(new CropCircleTransformation(context))
                .into(holder.imgAlbum);

        if(datas.get(position).itemClicked){
            holder.btnPause.setVisibility(View.VISIBLE);
        }else {
            holder.btnPause.setVisibility(View.GONE);
        }
    }
    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void goDetail(int position){

        mListener.goDetailInteraction(position);
    }
    public void setItemClicked(int position){
        for(Music.Item item:datas){
            item.itemClicked = false;
        }
        datas.get(position).itemClicked = true;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public int position;
        public Uri musicUri;
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public final ImageView imgAlbum;
        public final ImageButton btnPause;
        public ViewHolder(final View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
            imgAlbum = (ImageView) view.findViewById(R.id.imgAlbum);
            btnPause = (ImageButton)view.findViewById(R.id.btnPause);
            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setItemClicked(position);
                    Player.play(musicUri,mView.getContext());
                    btnPause.setImageResource(android.R.drawable.ic_media_pause);
                }
            });
            btnPause.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (playerStatus){
                        case Player.PLAY:
                            Player.pause();
                            btnPause.setImageResource(android.R.drawable.ic_media_play);
                            break;
                        case Player.PAUSE:
                            Player.replay();
                            btnPause.setImageResource(android.R.drawable.ic_media_pause);
                            break;

                    }
                }
            });


            mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
                public boolean onLongClick(View v) {
                    goDetail(position);
                    return true;
                }


            });
        }



    }
}
