package com.guozhe.android.musicplayer;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

/**
 * Created by guozhe on 2017. 6. 18..
 */

public class Player {
    public static final int STOP = 0;
    public static final int PLAY = 1;
    public static final int PAUSE = 2;
    public static MediaPlayer player =null;
    public static int playerStatus = STOP;



    public static void play( Uri musicUri, Context context){
        if(player != null) {
            player.release();
        }
        player = MediaPlayer.create(context,musicUri);
        player.setLooping(false);
        player.start();
        playerStatus = PLAY;

    }
    public static void pause(){
        player.pause();
        playerStatus = PAUSE;
    }
    public static void replay(){
        player.start();
        playerStatus = PLAY;
    }
    public static int getDuration(){
        if(player != null){
            return player.getDuration();
        }else {
            return 0;
        }
    }
    public static int getCurrent(){
        if(player != null) {
            return player.getCurrentPosition();
        }else {
            return 0;
        }
    }
}
