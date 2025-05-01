package com.example.recyclerviewdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.recyclerviewdemo.databinding.ActivityMainBinding;
import com.example.recyclerviewdemo.databinding.ActivityReservationPlacesBinding;

import java.util.ArrayList;
import java.util.List;

public class ReservationPlaces extends AppCompatActivity {

    ActivityReservationPlacesBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReservationPlacesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        //Ici les placements des sièges via des ArrayList
        List<Integer> siegeList = new ArrayList<>();

        for (int i = 0; i < 15; i++) { // 15 lignes
            if (i == 1) {
                // Ligne 2 invisible
                for (int j = 0; j < 12; j++) {
                    siegeList.add(0);
                }
                continue;
            }

            if (i == 14) {
                // Dernière ligne : seuls les sièges des colonnes 5 à 8 sont visibles
                for (int j = 0; j < 12; j++) {
                    if (j >= 4 && j <= 7) {
                        siegeList.add(R.drawable.siege_jaune); // visibles
                    } else {
                        siegeList.add(0); // invisibles
                    }
                }
                continue;
            }

            // Pour les autres lignes
            for (int j = 0; j < 12; j++) {
                if (j == 3 || j == 8) {
                    siegeList.add(0); // colonnes 4 et 9 invisibles
                } else {
                    siegeList.add(R.drawable.siege_jaune);
                }
            }
        }

        // Conversion en tableau
        int[] siegeImages = new int[siegeList.size()];
        for (int i = 0; i < siegeList.size(); i++) {
            siegeImages[i] = siegeList.get(i);
        }

        //-----------------------------------------------------------------------------------




        GridAdapter gridAdapter = new GridAdapter(ReservationPlaces.this, siegeImages);
        binding.grillePlaces.setAdapter(gridAdapter);

        binding.grillePlaces.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (siegeImages[position] != 0) {
                    siegeImages[position] = R.drawable.siege_vert; // on change l'image
                    gridAdapter.notifyDataSetChanged(); // on force la mise à jour de l'affichage

                    Toast.makeText(ReservationPlaces.this, "Place réservée à la position " + position, Toast.LENGTH_SHORT).show();
                }
            }
        });



        // Récupérer les données passées (comme l'horaire sélectionné)
        //String horaire = getIntent().getStringExtra("horaire");
        // Utiliser cette donnée dans l'activité si nécessaire


    }
}