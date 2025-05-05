package com.example.recyclerviewdemo;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.example.recyclerviewdemo.databinding.ActivityMainBinding;
import com.example.recyclerviewdemo.databinding.ActivityReservationPlacesBinding;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

        // Placer un siège gris aléatoirement parmi les sièges jaunes visibles
        List<Integer> indicesVisibles = new ArrayList<>();
        for (int i = 0; i < siegeList.size(); i++) {
            if (siegeList.get(i) == R.drawable.siege_jaune) {
                indicesVisibles.add(i);
            }
        }
        if (!indicesVisibles.isEmpty()) {
            Random random = new Random();
            int randomIndex = indicesVisibles.get(random.nextInt(indicesVisibles.size()));
            siegeList.set(randomIndex, R.drawable.siege_gris);
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
                    if (siegeImages[position] == R.drawable.siege_jaune) {
                        // Jaune → Vert
                        siegeImages[position] = R.drawable.siege_vert;
                        Toast.makeText(ReservationPlaces.this, "Place sélectionnée à la position " + position, Toast.LENGTH_SHORT).show();
                    } else if (siegeImages[position] == R.drawable.siege_vert) {
                        // Vert → Jaune
                        siegeImages[position] = R.drawable.siege_jaune;
                        Toast.makeText(ReservationPlaces.this, "Place désélectionnée à la position " + position, Toast.LENGTH_SHORT).show();
                    }

                    // Mise à jour de l'affichage
                    gridAdapter.notifyDataSetChanged();
                }
            }
        });


        //ici l'event du bouton valider mes places qui amenent à ActivityTarif
        Button boutonValidation = findViewById(R.id.validationPlaces);
        boutonValidation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Pour récupérer les places vertes : leur nombre et leur position
                SharedPreferences sharedPreferences = getSharedPreferences("reservation", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                ArrayList<Integer> siegesVerts = new ArrayList<>();

                for (int i = 0; i < siegeImages.length; i++) {
                    if (siegeImages[i] == R.drawable.siege_vert) {
                        siegesVerts.add(i);
                    }
                }

                // Vérifie si aucun siège sélectionné
                if (siegesVerts.isEmpty()) {
                    Toast.makeText(ReservationPlaces.this, "Veuillez sélectionner au moins un siège", Toast.LENGTH_SHORT).show();
                    return; // Empêche de continuer
                }

                // Enregistrement du nombre
                editor.putInt("nombre_sieges_verts", siegesVerts.size());

                // Conversion de la liste en format String séparée par des virgules
                StringBuilder positions = new StringBuilder();
                for (int i = 0; i < siegesVerts.size(); i++) {
                    positions.append(siegesVerts.get(i));
                    if (i < siegesVerts.size() - 1) {
                        positions.append(",");
                    }
                }
                editor.putString("positions_sieges_verts", positions.toString());

                editor.apply();

                // Lancer l'activité suivante
                Intent intent = new Intent(ReservationPlaces.this, ActivityTarif.class);
                startActivity(intent);
            }
        });


        //event de la croix retour de cette page
        // action de la croix pour retour à la page d'avant, la page précédente
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        final ShapeableImageView croixImage = findViewById(R.id.croix_image);

        croixImage.setOnClickListener(v -> {
            onBackPressed();  // Retourner à l'activité précédente
        });



        // Récupérer les données passées (comme l'horaire sélectionné)
        //String horaire = getIntent().getStringExtra("horaire");
        // Utiliser cette donnée dans l'activité si nécessaire


    }
}