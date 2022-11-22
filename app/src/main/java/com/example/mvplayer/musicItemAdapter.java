package com.example.mvplayer;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class musicItemAdapter extends RecyclerView.Adapter<musicItemAdapter.viewHolder>{
    Context context;
    ArrayList<HashMap<String, String>> musicList;
    MediaPlayer player;
    TextView lastItemName;
    ImageView lastItemIcon;

    // Constructor
    public musicItemAdapter(Context context, ArrayList<HashMap<String, String>> musicList){
        this.context = context;
        this.musicList = musicList;
    }

    class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView itemName;
        ImageView itemIcon;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.songName);
            itemIcon = itemView.findViewById(R.id.music_icon);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (player != null){

                // It disables the View for any click listener
                lastItemIcon.setEnabled(false);
                player.stop();
                player.release();
                lastItemIcon.setImageResource(R.drawable.music_icon);
                lastItemName.setTextColor(ContextCompat.getColor(context, R.color.textViewDefault));
            }

            // saving current playing itemName and itemIcon
            lastItemIcon = itemIcon;
            lastItemName = itemName;
            player = MediaPlayer.create(context, Uri.parse(musicList.get(getAdapterPosition()).get("songPath")));
            player.start();
            itemIcon.setImageResource(R.drawable.p_btn);
            itemName.setTextColor(ContextCompat.getColor(context, R.color.orange));

            // It enables the View for any click listener
            itemIcon.setEnabled(true);
            itemIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (player.isPlaying()){
                        player.pause();
                        itemIcon.setImageResource(R.drawable.pl_btn1);
                    }
                    else {
                        player.start();
                        itemIcon.setImageResource(R.drawable.p_btn);
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_layout, parent, false);
        viewHolder holder = new viewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.itemName.setText(musicList.get(position).get("songTitle"));
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(context, "working", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return musicList.size();
    }

}
