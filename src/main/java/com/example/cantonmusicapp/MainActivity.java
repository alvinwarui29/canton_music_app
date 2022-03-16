package com.example.cantonmusicapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuWrapperICS;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView RV;
    TextView nosong;
    ArrayList<MusicModel> musicList = new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);








        nosong= findViewById(R.id.txt_Nosong);
        RV = findViewById(R.id.songsRecyclerView);

        if (checkPermission()==false){
            requestPermission();
            return;
        }
        String[] projection = {
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DURATION,
        };
        String selection = MediaStore.Audio.Media.IS_MUSIC + " !=0 ";


        Cursor cursor = getContentResolver().query
                (MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        projection,selection,null,null);


        while (cursor.moveToNext()){
            MusicModel musicdata = new MusicModel(cursor.getString(1),cursor.getString(0),cursor.getString(2));
            if (new File(musicdata.getPath()).exists()){
                musicList.add(musicdata);
            }


            if (musicList.size() == 0){
                nosong.setVisibility(View.VISIBLE);
            }else{
                RV.setLayoutManager(new LinearLayoutManager(this));
                RV.setAdapter(new MusicAdapter(musicList,getApplicationContext()));
            }


        }



    }
    boolean checkPermission(){
        int result = ContextCompat.checkSelfPermission
                (MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED){
            return true;
        }else{
            return false;
        }

    }
    void  requestPermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale
                (MainActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)){
            Toast.makeText(this, "READ PERMISSION IS REQUIRED", Toast.LENGTH_SHORT).show();
        }else{
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                    },123);

        }

        }

    @Override
    protected void onResume() {
        super.onResume();
        if(RV != null){
            RV.setAdapter(new MusicAdapter(musicList,getApplicationContext()));

        }
    }

}