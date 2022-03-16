package com.example.cantonmusicapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.ViewHolder> {
    ArrayList<MusicModel> musicList;
    Context context;

    public MusicAdapter(ArrayList<MusicModel> musicList, Context context) {
        this.musicList = musicList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item,parent,false);
        return new MusicAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder( ViewHolder holder, int position) {
        MusicModel songdata = musicList.get(position);
        holder.title.setText(songdata.getTitle());

        if (myMediaPlayer.Index == position){
            holder.title.setTextColor(Color.parseColor("#FF0000"));

        }else{
            holder.title.setTextColor(Color.parseColor("#000000"));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myMediaPlayer.getInstance().reset();
                myMediaPlayer.Index =  position;
                Intent i = new Intent(context,MusicPlayer.class);
                i.putExtra("LIST",musicList);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);

            }
        });

    }

    @Override
    public int getItemCount() {
        return musicList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView iconImage;
        public ViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.music_title);
            iconImage= itemView.findViewById(R.id.icon_view);


        }
    }
}
