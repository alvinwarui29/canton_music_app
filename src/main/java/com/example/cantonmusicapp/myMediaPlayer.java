package com.example.cantonmusicapp;

import android.media.MediaPlayer;

public class myMediaPlayer {

    static MediaPlayer instance;
    public static MediaPlayer getInstance(){
        if (instance == null){
            instance = new MediaPlayer();
        }
        return instance;
    }
    public static int Index = -1;

}
