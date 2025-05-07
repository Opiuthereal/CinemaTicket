package com.example.recyclerviewdemo;

import android.content.Context;
import android.content.SharedPreferences;
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
        ItemHoraire currentItem = itemsHoraires.get(position);

        // Remplir les vues avec les données
        holder.dateOfFilm.setText(currentItem.getDateFilm());
        holder.horaireOfFilm.setText(currentItem.getHoraireFilm());
        holder.version.setText(currentItem.getVostHoraireFilm());

        // Ajouter un listener de clic à l'élément
        holder.itemView.setOnClickListener(v -> {
            // Log pour débogage
            Log.d("MyAdapterHoraire", "Item clicked: " + currentItem.getHoraireFilm());

            // Sauvegarder les informations dans SharedPreferences
            SharedPreferences sharedPreferences = context.getSharedPreferences("FilmInfo", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putString("jour", currentItem.getDateFilm());   // Sauvegarder le jour
            editor.putString("heure", currentItem.getHoraireFilm());  // Sauvegarder l'heure
            editor.putString("version", currentItem.getVostHoraireFilm()); // Sauvegarder la version

            editor.apply(); // Appliquer les changements

            // Appeler le listener
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

