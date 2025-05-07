package com.example.recyclerviewdemo;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.example.recyclerviewdemo.databinding.ActivityMainBinding;
import com.example.recyclerviewdemo.databinding.ActivityReservationPlacesBinding;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ReservationPlaces extends AppCompatActivity {

    private ActivityReservationPlacesBinding binding;

    // Déclare siegeImages comme une variable d'instance
    private int[] siegeImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReservationPlacesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Récupération des informations passées à l'activité
        String jour = getIntent().getStringExtra("jour");
        String heure = getIntent().getStringExtra("heure");
        String cinema = getIntent().getStringExtra("idCinema");
        Timestamp date = DataMainActivity.changeToDate(jour, heure);
        String titre = getIntent().getStringExtra("titre");
        String version = getIntent().getStringExtra("version");

        List<Integer> siegeList = new ArrayList<>(Collections.nCopies(180, 0));

        // Appel de la fonction setSalle
        DataMainActivity.setSalle(siegeList, cinema, date, titre, new DataMainActivity.SalleCallback() {
            @Override
            public void onSalleFound(String salle) {
                Log.d("Salle", "Salle trouvée : " + salle);
            }

            @Override
            public void onError(Exception e) {
                Log.e("Salle", "Erreur récupération salle", e);
            }
        }, () -> {
            // Ce bloc s'exécute APRES que Firestore ait mis à jour siegeList

            // Initialiser siegeImages avec la taille de siegeList
            siegeImages = new int[siegeList.size()];
            for (int i = 0; i < siegeList.size(); i++) {
                siegeImages[i] = siegeList.get(i);
            }

            // Création et configuration de GridAdapter
            GridAdapter gridAdapter = new GridAdapter(ReservationPlaces.this, siegeImages);
            binding.grillePlaces.setAdapter(gridAdapter);

            // Gestion de l'événement de clic sur un siège
            binding.grillePlaces.setOnItemClickListener((parent, view, position, id) -> {
                if (siegeImages[position] != 0) {
                    if (siegeImages[position] == R.drawable.siege_jaune) {
                        siegeImages[position] = R.drawable.siege_vert;
                        Toast.makeText(ReservationPlaces.this, "Place sélectionnée à la position " + position, Toast.LENGTH_SHORT).show();
                    } else if (siegeImages[position] == R.drawable.siege_vert) {
                        siegeImages[position] = R.drawable.siege_jaune;
                        Toast.makeText(ReservationPlaces.this, "Place désélectionnée à la position " + position, Toast.LENGTH_SHORT).show();
                    }

                    // Mise à jour de l'affichage
                    gridAdapter.notifyDataSetChanged();
                }
            });
        });

        // Gestion de l'événement du bouton de validation des places
        Button boutonValidation = findViewById(R.id.validationPlaces);
        boutonValidation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Récupérer les places vertes sélectionnées
                SharedPreferences sharedPreferences = getSharedPreferences("reservation", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                ArrayList<Integer> siegesVerts = new ArrayList<>();

                // Récupérer les positions des sièges verts dans siegeImages
                for (int i = 0; i < siegeImages.length; i++) {
                    if (siegeImages[i] == R.drawable.siege_vert) {
                        siegesVerts.add(i);
                    }
                }

                // Vérifier si des sièges ont été sélectionnés
                if (siegesVerts.isEmpty()) {
                    Toast.makeText(ReservationPlaces.this, "Veuillez sélectionner au moins un siège", Toast.LENGTH_SHORT).show();
                    return; // Empêcher de continuer si aucun siège n'est sélectionné
                }

                // Enregistrement du nombre de sièges sélectionnés
                editor.putInt("nombre_sieges_verts", siegesVerts.size());

                // Conversion de la liste de positions en une chaîne séparée par des virgules
                StringBuilder positions = new StringBuilder();
                for (int i = 0; i < siegesVerts.size(); i++) {
                    positions.append(siegesVerts.get(i));
                    if (i < siegesVerts.size() - 1) {
                        positions.append(",");
                    }
                }
                editor.putString("positions_sieges_verts", positions.toString());
                editor.putString("titre", titre);
                editor.putString("jour", jour);
                editor.putString("heure", heure);
                editor.putString("idCinema", cinema);
                editor.putString("version", version);

                editor.apply();

                // Lancer l'activité suivante avec les données de réservation
                Intent intent = new Intent(ReservationPlaces.this, ActivityTarif.class);
                intent.putExtra("positions_sieges_verts", positions.toString());
                intent.putExtra("nombre_sieges_verts", siegesVerts.size());
                intent.putExtra("titre", titre);
                intent.putExtra("jour", jour);
                intent.putExtra("heure", heure);
                intent.putExtra("idCinema", cinema);
                intent.putExtra("version", version);
                startActivity(intent);
            }
        });

        // Gestion de l'événement de la croix pour revenir à la page précédente
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        final ShapeableImageView croixImage = findViewById(R.id.croix_image);

        croixImage.setOnClickListener(v -> {
            onBackPressed();  // Retour à l'activité précédente
        });
    }
}