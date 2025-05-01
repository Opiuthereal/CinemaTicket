package com.example.recyclerviewdemo;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolderHoraire extends RecyclerView.ViewHolder {

    TextView horaireOfFilm;
    TextView version;

    public MyViewHolderHoraire(@NonNull View itemView) {
        super(itemView);
        horaireOfFilm = itemView.findViewById(R.id.horaire1Film);
        version = itemView.findViewById(R.id.vostHoraire1Film);
    }
}
