package com.example.recyclerviewdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private Context context;
    private List<Film> films;

    public MyAdapter(Context context, List<Film> films) {
        this.context = context;
        this.films = films;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Nous gonflons le layout de chaque item dans le RecyclerView
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // Nous associons les données du film au ViewHolder
        Film currentFilm = films.get(position);
        holder.imageView.setImageResource(currentFilm.getImageResId());
        holder.ageRestriction.setImageResource(currentFilm.getAgeRequis());
        holder.titreView.setText(currentFilm.getTitre());
        holder.horaire1View.setText(currentFilm.getHoraire1());
        holder.horaire2View.setText(currentFilm.getHoraire2());
        holder.horaire3View.setText(currentFilm.getHoraire3());
        holder.horaire4View.setText(currentFilm.getHoraire4());
        holder.vost1.setText(currentFilm.getVost1());
        holder.vost2.setText(currentFilm.getVost2());
        holder.vost3.setText(currentFilm.getVost3());
        holder.vost4.setText(currentFilm.getVost4());
    }

    @Override
    public int getItemCount() {
        // Retourne le nombre d'éléments dans la liste des films
        return films.size();
    }
}
