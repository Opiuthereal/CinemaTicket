package com.example.recyclerviewdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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


        //les prix des tarifs différents que je récupère avec la sharedPreference nécessaire pour les mettre à jours
        TextView tarifEnfant = findViewById(R.id.tarifEnfantValeur);
        TextView tarifEtudiant = findViewById(R.id.tarifEtudiantValeur);
        TextView tarifNormal = findViewById(R.id.tarifNormalValeur);
        TextView tarifSenior = findViewById(R.id.tarifSeniorValeur);
        String cinema = getIntent().getStringExtra("idCinema");

        DataMainActivity.setTarif(tarifEnfant, tarifEtudiant, tarifNormal, tarifSenior,cinema);

        /*
        //ici on convertit les quantités pour les multiplier par les prix des tickets
        int valeurquantitetarifEnfant = Integer.parseInt(quantitetarifEnfant.getText().toString());
        int valeurquantitetarifEtudiant = Integer.parseInt(quantitetarifEtudiant.getText().toString());
        int valeurquantitetarifNormal = Integer.parseInt(quantitetarifNormal.getText().toString());
        int valeurquantitetarifSenior = Integer.parseInt(quantitetarifSenior.getText().toString());

         */



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


        //ici la valeur du tarif restant
        TextView tarifRestant = findViewById(R.id.tarifsRestantsValue);


        plusTarifEnfant.setOnClickListener(v -> {
            int tarifRestantActuel = Integer.parseInt(tarifRestant.getText().toString());

            // Si plus aucun tarif restant, on n'autorise pas l'ajout
            if (tarifRestantActuel <= 0) {
                Toast.makeText(ActivityTarif.this, "Plus de tarifs disponibles", Toast.LENGTH_SHORT).show();
                return;
            }

            // Incrémenter la quantité du tarif enfant
            int tarifEnfantQuantite = Integer.parseInt(quantitetarifEnfant.getText().toString());
            tarifEnfantQuantite++;
            quantitetarifEnfant.setText(String.valueOf(tarifEnfantQuantite));

            // Décrémenter les tarifs restants
            tarifRestantActuel--;
            tarifRestant.setText(String.valueOf(tarifRestantActuel));
        });

        //le - pour le tarif enfant
        moinsTarifEnfant.setOnClickListener(v -> {
            int tarifEnfantQuantite = Integer.parseInt(quantitetarifEnfant.getText().toString());

            // Ne rien faire si la quantité est déjà à 0
            if (tarifEnfantQuantite <= 0) {
                return;
            }

            // Décrémenter la quantité enfant
            tarifEnfantQuantite--;
            quantitetarifEnfant.setText(String.valueOf(tarifEnfantQuantite));

            // Incrémenter les tarifs restants (puisqu'on en libère un)
            int tarifRestantActuel = Integer.parseInt(tarifRestant.getText().toString());
            tarifRestantActuel++;
            tarifRestant.setText(String.valueOf(tarifRestantActuel));
        });


        // Listener sur le bouton plus pour Etudiant
        plusTarifEtudiant.setOnClickListener(v -> {
            int tarifRestantActuel = Integer.parseInt(tarifRestant.getText().toString());

            if (tarifRestantActuel <= 0) {
                Toast.makeText(v.getContext(), "Plus de tarifs disponibles", Toast.LENGTH_SHORT).show();
                return;
            }

            int tarifEtudiantQuantite = Integer.parseInt(quantitetarifEtudiant.getText().toString());
            tarifEtudiantQuantite++;
            quantitetarifEtudiant.setText(String.valueOf(tarifEtudiantQuantite));

            tarifRestantActuel--;
            tarifRestant.setText(String.valueOf(tarifRestantActuel));
        });

        // Listener sur le bouton moins pour Etudiant
        moinsTarifEtudiant.setOnClickListener(v -> {
            int tarifEtudiantQuantite = Integer.parseInt(quantitetarifEtudiant.getText().toString());

            if (tarifEtudiantQuantite <= 0) {
                return; // rien à décrémenter
            }

            tarifEtudiantQuantite--;
            quantitetarifEtudiant.setText(String.valueOf(tarifEtudiantQuantite));

            // On libère un tarif
            int tarifRestantActuel = Integer.parseInt(tarifRestant.getText().toString());
            tarifRestantActuel++;
            tarifRestant.setText(String.valueOf(tarifRestantActuel));
        });


        // Listener sur le bouton plus pour Normal
        plusTarifNormal.setOnClickListener(v -> {
            int tarifRestantActuel = Integer.parseInt(tarifRestant.getText().toString());

            if (tarifRestantActuel <= 0) {
                Toast.makeText(v.getContext(), "Plus de tarifs disponibles", Toast.LENGTH_SHORT).show();
                return;
            }

            int tarifNormalQuantite = Integer.parseInt(quantitetarifNormal.getText().toString());
            tarifNormalQuantite++;
            quantitetarifNormal.setText(String.valueOf(tarifNormalQuantite));

            tarifRestantActuel--;
            tarifRestant.setText(String.valueOf(tarifRestantActuel));
        });

        // Listener sur le bouton moins pour Normal
        moinsTarifNormal.setOnClickListener(v -> {
            int tarifNormalQuantite = Integer.parseInt(quantitetarifNormal.getText().toString());

            if (tarifNormalQuantite <= 0) {
                return;
            }

            tarifNormalQuantite--;
            quantitetarifNormal.setText(String.valueOf(tarifNormalQuantite));

            int tarifRestantActuel = Integer.parseInt(tarifRestant.getText().toString());
            tarifRestantActuel++;
            tarifRestant.setText(String.valueOf(tarifRestantActuel));
        });


        // Listener sur le bouton plus pour Senior
        plusTarifSenior.setOnClickListener(v -> {
            int tarifRestantActuel = Integer.parseInt(tarifRestant.getText().toString());

            if (tarifRestantActuel <= 0) {
                Toast.makeText(v.getContext(), "Plus de tarifs disponibles", Toast.LENGTH_SHORT).show();
                return;
            }

            int tarifSeniorQuantite = Integer.parseInt(quantitetarifSenior.getText().toString());
            tarifSeniorQuantite++;
            quantitetarifSenior.setText(String.valueOf(tarifSeniorQuantite));

            tarifRestantActuel--;
            tarifRestant.setText(String.valueOf(tarifRestantActuel));
        });

        // Listener sur le bouton moins pour Senior
        moinsTarifSenior.setOnClickListener(v -> {
            int tarifSeniorQuantite = Integer.parseInt(quantitetarifSenior.getText().toString());

            if (tarifSeniorQuantite <= 0) {
                return;
            }

            tarifSeniorQuantite--;
            quantitetarifSenior.setText(String.valueOf(tarifSeniorQuantite));

            int tarifRestantActuel = Integer.parseInt(tarifRestant.getText().toString());
            tarifRestantActuel++;
            tarifRestant.setText(String.valueOf(tarifRestantActuel));
        });

        //-------------------------------------------------------------------------------------------------------


        // Obtenir l'objet SharedPreferences (mode privé)
        SharedPreferences sharedPreferencesTarifs = getSharedPreferences("tarifsQuantites", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferencesTarifs.edit();


        //-------------------------------------------------------------------------------------------------------

        // Récupération des données depuis SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("reservation", MODE_PRIVATE);
        int nombreSiegesVerts = sharedPreferences.getInt("nombre_sieges_verts", 0);
        String positionsSieges = getIntent().getStringExtra("positions_sieges_verts");
        String titre = getIntent().getStringExtra("titre");
        String jour = getIntent().getStringExtra("jour");
        String heure = getIntent().getStringExtra("heure");
        String version = getIntent().getStringExtra("version");


        // Récupération des données depuis SharedPreferences
        /*
        String titre = sharedPreferences.getString("titre", "Titre inconnu");
        String jour = sharedPreferences.getString("jour", "Jour inconnu");
        String heure = sharedPreferences.getString("heure", "Heure inconnue");
        String version = sharedPreferences.getString("version", "Version inconnue");
        String cinema = sharedPreferences.getString("idCinema", "Nom Cinéma");

         */


        // Récupération du TextView
        TextView tarifsRestantsValue = findViewById(R.id.tarifsRestantsValue);

        // Affichage du nombre de sièges sélectionnés en tant que tarifs restants
        //qui sert aussi de valeur de nombre de sièges
        tarifsRestantsValue.setText(String.valueOf(nombreSiegesVerts));


        // pour passer à la page d'infos personnelles
        Button boutonValidationTarifs = findViewById(R.id.validerButtonTarifs);
        boutonValidationTarifs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // Récupère la valeur du tarif restant
                    int tarifRestantActuel = Integer.parseInt(tarifRestant.getText().toString());

                    if (tarifRestantActuel == 0) {
                        // ----------------------- DEBUT : Ton code déplacé ici -----------------------

                        TextView prixtarifEnfant = findViewById(R.id.tarifEnfantValeur);
                        TextView prixtarifEtudiant = findViewById(R.id.tarifEtudiantValeur);
                        TextView prixtarifNormal = findViewById(R.id.tarifNormalValeur);
                        TextView prixtarifSenior = findViewById(R.id.tarifSeniorValeur);

                        int valeurprixtarifEnfant = Integer.parseInt(prixtarifEnfant.getText().toString());
                        int valeurprixtarifEtudiant = Integer.parseInt(prixtarifEtudiant.getText().toString());
                        int valeurprixtarifNormal = Integer.parseInt(prixtarifNormal.getText().toString());
                        int valeurprixtarifSenior = Integer.parseInt(prixtarifSenior.getText().toString());


                        int tarifEnfantQuantite = Integer.parseInt(quantitetarifEnfant.getText().toString());
                        int tarifEtudiantQuantite = Integer.parseInt(quantitetarifEtudiant.getText().toString());
                        int tarifNormalQuantite = Integer.parseInt(quantitetarifNormal.getText().toString());
                        int tarifSeniorQuantite = Integer.parseInt(quantitetarifSenior.getText().toString());

                        int totalAPayer = (tarifEnfantQuantite * valeurprixtarifEnfant) +
                                (tarifEtudiantQuantite * valeurprixtarifEtudiant) +
                                (tarifNormalQuantite * valeurprixtarifNormal) +
                                (tarifSeniorQuantite * valeurprixtarifSenior);


                        editor.putInt("prixEnfant", valeurprixtarifEnfant);
                        editor.putInt("prixEtudiant", valeurprixtarifEtudiant);
                        editor.putInt("prixNormal", valeurprixtarifNormal);
                        editor.putInt("prixSenior", valeurprixtarifSenior);

                        editor.putInt("quantiteEnfant", tarifEnfantQuantite);
                        editor.putInt("quantiteEtudiant", tarifEtudiantQuantite);
                        editor.putInt("quantiteNormal", tarifNormalQuantite);
                        editor.putInt("quantiteSenior", tarifSeniorQuantite);

                        editor.putString("position_sieges_verts", positionsSieges);
                        editor.putString("titre", titre);
                        editor.putString("jour", jour);
                        editor.putString("heure", heure);
                        editor.putString("version", version);
                        editor.putString("idCinema", cinema);
                        editor.putInt("totalAPayer", totalAPayer);
                        editor.putInt("nombre_sieges_verts", nombreSiegesVerts);
                        editor.apply();


                        // Intent vers l'activité suivante
                        Intent intent = new Intent(ActivityTarif.this, InfosPersonnelles.class);
                        intent.putExtra("nombreSiegesVerts", nombreSiegesVerts);
                        intent.putExtra("totalAPayer", totalAPayer);
                        intent.putExtra("positions_sieges_verts", positionsSieges);
                        intent.putExtra("titre", titre);
                        intent.putExtra("jour", jour);
                        intent.putExtra("heure", heure);
                        intent.putExtra("version", version);
                        intent.putExtra("idCinema", cinema);
                        startActivity(intent);


                        // ----------------------- FIN : Ton code déplacé ici -----------------------
                    } else {
                        Toast.makeText(ActivityTarif.this, "Veuillez sélectionner tous les tarifs avant de valider.", Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(ActivityTarif.this, "Erreur : veuillez vérifier les champs", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}