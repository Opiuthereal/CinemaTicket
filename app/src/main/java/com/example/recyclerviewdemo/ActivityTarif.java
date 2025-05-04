package com.example.recyclerviewdemo;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.List;

public class ActivityTarif extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarif);

        // action de la croix pour retour à la page d'avant, la page précédente
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        final ShapeableImageView croixImage = findViewById(R.id.croix_image_tarif);

        croixImage.setOnClickListener(v -> {
            onBackPressed();  // Retourner à l'activité précédente
        });

        //les quantités des tarifs différents que je récupère
        TextView quantitetarifEnfant = findViewById(R.id.quantiteEnfant);
        TextView quantitetarifEtudiant = findViewById(R.id.quantiteEtudiant);
        TextView quantitetarifNormal = findViewById(R.id.quantiteNormal);
        TextView quantitetarifSenior = findViewById(R.id.quantiteSenior);



        //les images + et - de chaque tarifs
        //enfant
        ShapeableImageView plusTarifEnfant = findViewById(R.id.plusTarifEnfant);
        ShapeableImageView moinsTarifEnfant = findViewById(R.id.moinsTarifEnfant);
        //étudiant
        ShapeableImageView plusTarifEtudiant = findViewById(R.id.plusTarifEtudiant);
        ShapeableImageView moinsTarifEtudiant = findViewById(R.id.moinsTarifEtudiant);
        //normal
        ShapeableImageView plusTarifNormal = findViewById(R.id.plusTarifNormal);
        ShapeableImageView moinsTarifNormal = findViewById(R.id.moinsTarifNormal);
        //senior
        ShapeableImageView plusTarifSenior = findViewById(R.id.plusTarifSenior);
        ShapeableImageView moinsTarifSenior = findViewById(R.id.moinsTarifSenior);


        // Listener sur le bouton plus pour Enfant
        plusTarifEnfant.setOnClickListener(v -> {
            // Lire la valeur actuelle
            int currentValue = Integer.parseInt(quantitetarifEnfant.getText().toString());
            // Incrémenter
            currentValue++;
            // Afficher la nouvelle valeur
            quantitetarifEnfant.setText(String.valueOf(currentValue));
            //+1 à l'int du tarif
            int tarifEnfantQuantite = Integer.parseInt(quantitetarifEnfant.getText().toString());
            tarifEnfantQuantite++;
        });

        // Listener sur le bouton moins pour Enfant
        moinsTarifEnfant.setOnClickListener(v -> {
            // Lire la valeur actuelle
            int currentValue = Integer.parseInt(quantitetarifEnfant.getText().toString());
            // Décrémenter si supérieur à 0
            if (currentValue > 0) {
                currentValue--;
                // Afficher la nouvelle valeur
                quantitetarifEnfant.setText(String.valueOf(currentValue));
                //-1 à l'int du tarif
                int tarifEnfantQuantite = Integer.parseInt(quantitetarifEnfant.getText().toString());
                tarifEnfantQuantite--;
            }
        });

        // Listener sur le bouton plus pour Etudiant
        plusTarifEtudiant.setOnClickListener(v -> {
            // Lire la valeur actuelle
            int currentValue = Integer.parseInt(quantitetarifEtudiant.getText().toString());
            // Incrémenter
            currentValue++;
            // Afficher la nouvelle valeur
            quantitetarifEtudiant.setText(String.valueOf(currentValue));
            //+1 à l'int du tarif
            int tarifEtudiantQuantite = Integer.parseInt(quantitetarifEtudiant.getText().toString());
            tarifEtudiantQuantite++;
        });

        // Listener sur le bouton moins pour Etudiant
        moinsTarifEtudiant.setOnClickListener(v -> {
            // Lire la valeur actuelle
            int currentValue = Integer.parseInt(quantitetarifEtudiant.getText().toString());
            // Décrémenter si supérieur à 0
            if (currentValue > 0) {
                currentValue--;
                // Afficher la nouvelle valeur
                quantitetarifEtudiant.setText(String.valueOf(currentValue));
                //-1 à l'int du tarif
                int tarifEtudiantQuantite = Integer.parseInt(quantitetarifEtudiant.getText().toString());
                tarifEtudiantQuantite--;
            }
        });

        // Listener sur le bouton plus pour Normal
        plusTarifNormal.setOnClickListener(v -> {
            // Lire la valeur actuelle
            int currentValue = Integer.parseInt(quantitetarifNormal.getText().toString());
            // Incrémenter
            currentValue++;
            // Afficher la nouvelle valeur
            quantitetarifNormal.setText(String.valueOf(currentValue));
            //+1 à l'int du tarif
            int tarifNormalQuantite = Integer.parseInt(quantitetarifNormal.getText().toString());
            tarifNormalQuantite++;
        });

        // Listener sur le bouton moins pour Normal
        moinsTarifNormal.setOnClickListener(v -> {
            // Lire la valeur actuelle
            int currentValue = Integer.parseInt(quantitetarifNormal.getText().toString());
            // Décrémenter si supérieur à 0
            if (currentValue > 0) {
                currentValue--;
                // Afficher la nouvelle valeur
                quantitetarifNormal.setText(String.valueOf(currentValue));
                //-1 à l'int du tarif
                int tarifNormalQuantite = Integer.parseInt(quantitetarifNormal.getText().toString());
                tarifNormalQuantite--;
            }
        });

        // Listener sur le bouton plus pour Senior
        plusTarifSenior.setOnClickListener(v -> {
            // Lire la valeur actuelle
            int currentValue = Integer.parseInt(quantitetarifSenior.getText().toString());
            // Incrémenter
            currentValue++;
            // Afficher la nouvelle valeur
            quantitetarifSenior.setText(String.valueOf(currentValue));
            //+1 à l'int du tarif
            int tarifSeniorQuantite = Integer.parseInt(quantitetarifSenior.getText().toString());
            tarifSeniorQuantite++;
        });

        // Listener sur le bouton moins pour Senior
        moinsTarifSenior.setOnClickListener(v -> {
            // Lire la valeur actuelle
            int currentValue = Integer.parseInt(quantitetarifSenior.getText().toString());
            // Décrémenter si supérieur à 0
            if (currentValue > 0) {
                currentValue--;
                // Afficher la nouvelle valeur
                quantitetarifSenior.setText(String.valueOf(currentValue));
                //-1 à l'int du tarif
                int tarifSeniorQuantite = Integer.parseInt(quantitetarifSenior.getText().toString());
                tarifSeniorQuantite--;
            }
        });



        // Récupération des données depuis SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("reservation", MODE_PRIVATE);
        int nombreSiegesVerts = sharedPreferences.getInt("nombre_sieges_verts", 0);

        // Récupération du TextView
        TextView tarifsRestantsValue = findViewById(R.id.tarifsRestantsValue);

        // Affichage du nombre de sièges sélectionnés
        tarifsRestantsValue.setText(String.valueOf(nombreSiegesVerts));


    }
}