package com.nicekoh.musicplayer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.nicekoh.musicplayer.MusicAct2;
import com.nicekoh.musicplayer.OpenVideo;
import com.nicekoh.musicplayer.R;
import com.nicekoh.musicplayer.VideoModal;

public class Recycler_adapter_music extends RecyclerView.Adapter<Recycler_adapter_music.ViewHolder> {
    Context context;
    ArrayList<VideoModal> arrayList;
    OpenVideo openVideo;
    ArrayList<VideoModal> videoList;


    public Recycler_adapter_music(Context context, ArrayList<VideoModal> videoModals) {

        this.context = context;
        this.arrayList= videoModals;
    }



    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.card_music,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {


            VideoModal videoModal = arrayList.get(position);
            holder.name.setText(videoModal.getName());
            holder.size.setText(videoModal.getSize());
           //  holder.date.setText(videoModal.getDate());
            holder.duration.setText(videoModal.duration);
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {  Bundle bundle=new Bundle();
                    bundle.putParcelableArrayList("ArrayList", arrayList);
                    context.startActivity(new Intent(context, MusicAct2.class).putExtra("POSI",position).putExtras(bundle));

                }
            });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name,size,duration,date;
        LinearLayout no_music;
        CardView cardView;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.cardMusic);
            name=itemView.findViewById(R.id.cardname_music);
            size=itemView.findViewById(R.id.cardsize_music);
            duration=itemView.findViewById(R.id.cardduration_music);
            date=itemView.findViewById(R.id.carddatemusic);
            name.setSelected(true);
        }
    }
    public void  updateVideoFiles(ArrayList<VideoModal> files) {

        arrayList=(ArrayList<VideoModal>) files;

        notifyDataSetChanged();
    }
}
