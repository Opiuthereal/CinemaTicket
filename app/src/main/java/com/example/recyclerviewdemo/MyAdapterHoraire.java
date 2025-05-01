package com.example.recyclerviewdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapterHoraire extends RecyclerView.Adapter<MyViewHolderHoraire> {

    Context context;
    List<ItemHoraire> itemsHoraires;

    public MyAdapterHoraire(Context context, List<ItemHoraire> itemsHoraires) {
        this.context = context;
        this.itemsHoraires = itemsHoraires;
    }

    @NonNull
    @Override
    public MyViewHolderHoraire onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolderHoraire(LayoutInflater.from(context).inflate(R.layout.item_horaires, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolderHoraire holder, int position) {
        holder.horaireOfFilm.setText(itemsHoraires.get(position).getHoraireFilm());
        holder.version.setText(itemsHoraires.get(position).getVostHoraireFilm());
    }

    @Override
    public int getItemCount() {
        return itemsHoraires.size();
    }
}
