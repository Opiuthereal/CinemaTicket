package com.example.recyclerviewdemo;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.BreakIterator;

public class MyViewHolder extends RecyclerView.ViewHolder {

    public BreakIterator titreTextView;
    // Déclaration des vues
    ImageView imageView, ageRestriction;
    TextView titreView, horaire1View, horaire2View, horaire3View, horaire4View, vost1, vost2, vost3, vost4, nomCinoche;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        // Initialisation des vues à partir de l'itemView
        ageRestriction = itemView.findViewById(R.id.ageRestriction);
        imageView = itemView.findViewById(R.id.imageview);
        titreView = itemView.findViewById(R.id.titre);
        horaire1View = itemView.findViewById(R.id.heure1);
        horaire2View = itemView.findViewById(R.id.heure2);
        horaire3View = itemView.findViewById(R.id.heure3);
        horaire4View = itemView.findViewById(R.id.heure4);
        vost1 = itemView.findViewById(R.id.vostHeure1);
        vost2 = itemView.findViewById(R.id.vostHeure2);
        vost3 = itemView.findViewById(R.id.vostHeure3);
        vost4 = itemView.findViewById(R.id.vostHeure4);
        nomCinoche = itemView.findViewById(R.id.nomCinoche);
    }
}
