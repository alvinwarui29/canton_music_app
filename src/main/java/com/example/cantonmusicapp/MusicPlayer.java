package com.example.cantonmusicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MusicPlayer extends AppCompatActivity {

    TextView titletv,currentTime,totalTime;
    SeekBar seekBar;
    ImageView pauseplay,nextbtn,prvbtn,btIcon;
    ArrayList<MusicModel> musiclist;
    MusicModel currentsong;
    MediaPlayer mediaPlayer = myMediaPlayer.getInstance();
    int x=0;
    Button repeatbtn;
    Button shufflebtn;
    Boolean repeatFlag = false;
    Boolean shuffleFlag= false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        titletv = findViewById(R.id.title_text);
        currentTime = findViewById(R.id.current_time);
        totalTime = findViewById(R.id.total_time);
        seekBar = findViewById(R.id.seek_bar);
        pauseplay = findViewById(R.id.pause_play);
        nextbtn = findViewById(R.id.next);
        prvbtn = findViewById(R.id.previous);
        btIcon = findViewById(R.id.bt_icon);
        titletv.setSelected(true);
        repeatbtn = findViewById(R.id.repeat);
        shufflebtn = findViewById(R.id.shuffle);

        musiclist = (ArrayList<MusicModel>) getIntent()
                .getSerializableExtra("LIST");
        setResourcesWithMusic();
        MusicPlayer.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer!=null){
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    currentTime.setText(convert(mediaPlayer.getCurrentPosition()+""));

                    if (mediaPlayer.isPlaying()){
                        pauseplay.setImageResource(R.drawable.ic_baseline_pause_circle_outline_24);
                        //btIcon.setRotation(x++);
                    }else{
                        pauseplay.setImageResource(R.drawable.ic_baseline_play_circle_outline_24);
                        //btIcon.setRotation(0);
                    }
                }
                new Handler().postDelayed(this,0);
            }
        });


    }
    void setResourcesWithMusic(){
        currentsong = musiclist.get(myMediaPlayer.Index);
        titletv.setText(currentsong.getTitle());
        totalTime.setText(convert(currentsong.getDuration()));

        pauseplay.setOnClickListener(v-> pauseplay());
        nextbtn.setOnClickListener(v-> playNext());
        prvbtn.setOnClickListener(v-> playPrevious());
        //pauseplay.setOnClickListener(v-> pauseplay());

        playmusic();

    }

    private void playmusic(){
        mediaPlayer.reset();
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (mediaPlayer != null && b){
                    mediaPlayer.seekTo(i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        try {
            mediaPlayer.setDataSource(currentsong.getPath());
            mediaPlayer.prepare();
            mediaPlayer.start();
            seekBar.setProgress(0);
            seekBar.setMax(mediaPlayer.getDuration());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void playNext(){

        if (myMediaPlayer.Index == musiclist.size()-1)
            return;
        myMediaPlayer.Index +=1;
        mediaPlayer.reset();
        setResourcesWithMusic();



    }
    private void playPrevious(){

        if (myMediaPlayer.Index == 0)
            return;
        myMediaPlayer.Index -=1;
        mediaPlayer.reset();
        setResourcesWithMusic();
    }
    private void pauseplay(){
        if (mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }else{
            mediaPlayer.start();
        }

    }

    public static String convert(String duration){
        Long millis = Long.parseLong(duration);
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1)
                );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.shuffle_repeat,menu);
        return true;

    }

    public void repeatSong (View view){
        if (repeatFlag){
            repeatbtn.setBackgroundResource(R.drawable.repeat);
        }else{
            repeatbtn.setBackgroundResource(R.drawable.repeat_once);
        }
        repeatFlag = !repeatFlag;
    }

//    public void shuffleSong (View view){
//        if (shuffleFlag){
//            shufflebtn.setBackgroundResource(R.drawable.shuffle_off);
//
//        }else{
//            Collections.shuffle(musiclist);
//            shufflebtn.setBackgroundResource(R.drawable.shuffle);
//
//        }
//
//        shuffleFlag = !shuffleFlag;
//    }



}