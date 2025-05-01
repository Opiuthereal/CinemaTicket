package com.example.recyclerviewdemo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapterHoraire extends RecyclerView.Adapter<MyViewHolderHoraire> {

    Context context;
    List<ItemHoraire> itemsHoraires;
    private OnItemClickListener listener;

    // Constructeur avec listener
    public MyAdapterHoraire(Context context, List<ItemHoraire> itemsHoraires, OnItemClickListener listener) {
        this.context = context;
        this.itemsHoraires = itemsHoraires;
        this.listener = listener;
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
        ItemHoraire currentItem = itemsHoraires.get(position);

        // Ajouter un listener de clic à l'élément
        holder.itemView.setOnClickListener(v -> {
            Log.d("MyAdapterHoraire", "Item clicked: " + currentItem.getHoraireFilm());
            listener.onItemClick(currentItem);
        });

    }

    @Override
    public int getItemCount() {
        return itemsHoraires.size();
    }

    // Interface pour écouter les clics
    public interface OnItemClickListener {
        void onItemClick(ItemHoraire itemHoraire);
    }
}

