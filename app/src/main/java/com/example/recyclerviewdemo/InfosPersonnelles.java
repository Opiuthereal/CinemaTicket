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
            editor.putString("nom", nom);
            editor.putString("prenom", prenom);
            editor.putString("email", email);
            editor.apply();

            // Aller vers la nouvelle activité
            Intent intent = new Intent(InfosPersonnelles.this, TicketResumeAll.class);
            startActivity(intent);
        });


    }
}