package com.example.recyclerviewdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.google.android.material.imageview.ShapeableImageView;

public class Remerciements extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remerciements);

        // action de la croix pour retour à la page d'avant, la page précédente
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        final ShapeableImageView croixImage = findViewById(R.id.croix_image_final);

        croixImage.setOnClickListener(v -> {
            onBackPressed();  // Retourner à l'activité précédente
        });

    }
}