package com.example.recyclerviewdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class TicketResumeAll extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_resume_all);

        // action de la croix pour retour à la page d'avant, la page précédente
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        final ShapeableImageView croixImage = findViewById(R.id.croix_image_infos_total);

        croixImage.setOnClickListener(v -> {
            onBackPressed();  // Retourner à l'activité précédente
        });

        ImageView qrCodeImageView = findViewById(R.id.qrCodeImageView);

        // Exemple de données à encoder
        String qrData = "Nom: Dupont\nPrénom: Jean\nFilm: Dune 2\nDate: 10/05/2025 19h00\nSiège: A12";

        try {
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.encodeBitmap(qrData, BarcodeFormat.QR_CODE, 400, 400);
            qrCodeImageView.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        // pour passer à la page d'infos personnelles
        Button boutonValidationAll = findViewById(R.id.validerButtonConfirmer);
        boutonValidationAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TicketResumeAll.this, Remerciements.class);
                startActivity(intent);
            }
        });

    }
}