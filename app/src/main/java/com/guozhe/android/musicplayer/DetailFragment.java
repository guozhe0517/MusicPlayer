package com.guozhe.android.musicplayer;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.guozhe.android.musicplayer.domain.Music;

import java.util.List;

public class DetailFragment extends Fragment {
    public static final int CHANGE_SEEKBAR = 99;
    static final String ARG1 = "Position";
    ViewHolder viewHolder = null;
    private int position = -1;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case CHANGE_SEEKBAR:
                    viewHolder.setSeekbarPosition(msg.arg1);
            }
        }
    };

    public DetailFragment() {
        // Required empty public constructor
    }

    public static DetailFragment newInstance(int positon) {
        DetailFragment fragment = new DetailFragment();
        Bundle bundle =new Bundle();
        bundle.putInt("ARG1",positon);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_pager, container, false);
        Bundle bundle = getArguments();
        position = bundle.getInt(ARG1);
        viewHolder = new ViewHolder(view,position);
        return view;
    }
    public List<Music.Item> getDatas(){
        Music music = Music.getInstance();
        music.loader(getContext());
        return music.getItems();
    }


    public class ViewHolder implements View.OnClickListener{
        ViewPager viewPager;
        RelativeLayout layoutContorller;
        ImageButton btnPlay,btnNext,btnPrev;
        SeekBar seekBar;
        TextView duration,current;



        public ViewHolder(View view,int position){
            viewPager = (ViewPager)view.findViewById(R.id.viewPager);
            layoutContorller = (RelativeLayout)view.findViewById(R.id.layoutController);
            btnPlay = (ImageButton) view.findViewById(R.id.btnPlay);
            btnNext = (ImageButton) view.findViewById(R.id.btnNext);
            btnPrev = (ImageButton) view.findViewById(R.id.btnPrev);
            seekBar = (SeekBar)view.findViewById(R.id.seekBar);
            duration = (TextView)view.findViewById(R.id.duration);
            current = (TextView)view.findViewById(R.id.current);
            setOnClickListener();
            setViewPager(position);
        }
        private void setOnClickListener(){
            btnPlay.setOnClickListener(this);
            btnPrev.setOnClickListener(this);
            btnNext.setOnClickListener(this);
        }

        private void setViewPager(int position){
            DetailAdapter adapter = new DetailAdapter(getDatas());
            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(position);
        }
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnPlay:
                   Uri musicUri = getDatas().get(position).musicUri;
                    Player.play(musicUri,v.getContext());

                    seekBar.setMax(Player.getDuration());
                    new SeekBarThread(handler).start();
                    break;
                case R.id.btnNext :

                    break;
                case R.id.btnPrev:

                    break;
            }
        }
        public void setSeekbarPosition(int current){
            seekBar.setProgress(current);
        }
    }
}
class SeekBarThread extends Thread{
    Handler handler;
    boolean runFlag = true;
    public SeekBarThread(Handler handler){
        this.handler = handler;
    }

    @Override
    public void run(){
        while (true) {
            int current = Player.getCurrent();
            Message msg = new Message();
            msg.what = DetailFragment.CHANGE_SEEKBAR;
            msg.arg1 = current;
            handler.sendMessage(msg);
            if(current >= Player.getDuration()){
                runFlag = false;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
