package com.example.recyclerviewdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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



        //------------------------------------------------------------------------------------------------

        //Reception des intent des autres pages
        Intent intent = getIntent();

        String nom = intent.getStringExtra("nom");
        String prenom = intent.getStringExtra("prenom");
        String email = intent.getStringExtra("email");

        int nombreSiegesVerts = intent.getIntExtra("nombreSiegesVerts", 0);
        int totalAPayer = intent.getIntExtra("totalAPayer", 0);
        String positionsSieges = getIntent().getStringExtra("positions_sieges_verts");
        String titre = getIntent().getStringExtra("titre");
        String jour = getIntent().getStringExtra("jour");
        String heure = getIntent().getStringExtra("heure");
        String version = getIntent().getStringExtra("version");
        String cinema = getIntent().getStringExtra("idCinema");

        TextView nomTextView = findViewById(R.id.nomUserFinal);
        TextView prenomTextView = findViewById(R.id.prenomUserFinal);
        TextView emailTextView = findViewById(R.id.emailUserFinal);
        TextView placesTextView = findViewById(R.id.nbPlacesFinal);
        TextView prixTextView = findViewById(R.id.prixFinal);
        TextView positionView = findViewById(R.id.positionsPlacesFinal);
        TextView nomCineView = findViewById(R.id.cineFinal);
        TextView titreView = findViewById(R.id.titreFilmFinal);
        TextView jourView = findViewById(R.id.jourFinal);
        TextView heureView = findViewById(R.id.heureFinal);
        TextView versionView = findViewById(R.id.tyoeDiffusionFinal);



        nomTextView.setText(nom);
        prenomTextView.setText(prenom);
        emailTextView.setText(email);
        placesTextView.setText(String.valueOf(nombreSiegesVerts));
        prixTextView.setText(totalAPayer + " €");
        positionView.setText("Sièges aux positions : " + positionsSieges);
        nomCineView.setText(cinema);
        titreView.setText(titre);
        jourView.setText(jour);
        heureView.setText(heure);
        versionView.setText(version);

        // Exemple de données à encoder
        String qrData = "Nom: " + nom + "\nPrénom:" + prenom + "\nemail:" +  email + "\nNomFilm" + titre + "\nHoraireJour" + jour + "\nHoraireHeure" + heure + "\nPositionsSieges" + positionsSieges;

        try {
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.encodeBitmap(qrData, BarcodeFormat.QR_CODE, 400, 400);
            qrCodeImageView.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }



        //------------------------------------------------------------------------------------------------

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