package com.example.recyclerviewdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.imageview.ShapeableImageView;

public class InfosPersonnelles extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infos_personnelles);


        // action de la croix pour retour à la page d'avant, la page précédente
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        final ShapeableImageView croixImage = findViewById(R.id.croix_image_infos_perso);

        croixImage.setOnClickListener(v -> {
            onBackPressed();  // Retourner à l'activité précédente
        });

        // Récupération des prix et sièges
        Intent intent = getIntent();

        int nombreSiegesVerts = intent.getIntExtra("nombreSiegesVerts", 0);
        int totalAPayer = intent.getIntExtra("totalAPayer", 0);
        String titre = getIntent().getStringExtra("titre");
        String jour = getIntent().getStringExtra("jour");
        String heure = getIntent().getStringExtra("heure");
        String version = getIntent().getStringExtra("version");
        String cinema = getIntent().getStringExtra("idCinema");


        // Récupération des champs EditText
        AppCompatEditText nomEditText = findViewById(R.id.Nom);
        AppCompatEditText prenomEditText = findViewById(R.id.Prenom);
        AppCompatEditText emailEditText = findViewById(R.id.Email);

        Button validerButton = findViewById(R.id.validerButtonInfos);

        validerButton.setOnClickListener(v -> {
            // Récupération dynamique au moment du clic
            String nom = nomEditText.getText().toString().trim();
            String prenom = prenomEditText.getText().toString().trim();
            String email = emailEditText.getText().toString().trim();

            if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty()) {
                Toast.makeText(this, "Veuillez remplir tous les champs.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Enregistrer dans SharedPreferences
            SharedPreferences sharedPreferences = getSharedPreferences("infos_perso", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            String positionsSieges = getIntent().getStringExtra("positions_sieges_verts");
            /*
            String titre = sharedPreferences.getString("titre", "Titre inconnu");
            String jour = sharedPreferences.getString("jour", "Jour inconnu");
            String heure = sharedPreferences.getString("heure", "Heure inconnue");
            String version = sharedPreferences.getString("version", "Version inconnue");
            String cinema = sharedPreferences.getString("idCinema", "Nom Cinéma");

             */

            editor.putString("nom", nom);
            editor.putString("prenom", prenom);
            editor.putString("email", email);
            editor.putString("titre", titre);
            editor.putString("jour", jour);
            editor.putString("heure", heure);
            editor.putString("version", version);
            editor.putString("idCinema", cinema);
            editor.putString("positions_sieges_verts", positionsSieges.toString());
            editor.putInt("nombre_sieges_verts", nombreSiegesVerts);

            editor.apply();

            // Aller vers la nouvelle activité
            Intent intentInfos = new Intent(InfosPersonnelles.this, TicketResumeAll.class);
            intentInfos.putExtra("nom", nom);
            intentInfos.putExtra("prenom", prenom);
            intentInfos.putExtra("email", email);
            intentInfos.putExtra("nombreSiegesVerts", nombreSiegesVerts);
            intentInfos.putExtra("positions_sieges_verts", positionsSieges);
            intentInfos.putExtra("totalAPayer", totalAPayer);
            intentInfos.putExtra("idCinema", cinema);
            intentInfos.putExtra("titre", titre);
            intentInfos.putExtra("jour", jour);
            intentInfos.putExtra("heure", heure);
            intentInfos.putExtra("version", version);

            startActivity(intentInfos);

        });


    }
}