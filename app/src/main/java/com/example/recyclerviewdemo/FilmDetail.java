package com.example.recyclerviewdemo;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.android.material.imageview.ShapeableImageView;

public class FilmDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_film_detail);

        //controles de vidéo - code base
        VideoView videoView = findViewById(R.id.videoViewBandeAnnonce);
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.monte_cristo;
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);


        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        //controles de son de la vidéo
        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        audioManager.requestAudioFocus(focusChange -> {}, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

        videoView.setOnPreparedListener(mp -> {
            mp.setVolume(1f, 1f); // Son activé
            mp.setLooping(false);  // pas de boucle
        });


        // event pour le bouton résumé qui affiche le gros texte
        // Récupère les TextViews
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        TextView resumeBouton = findViewById(R.id.resumeButton);
        TextView resumeFilm = findViewById(R.id.resumeFilm);

        // action du clic sur "Résumé >"
        resumeBouton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Si le résumé est déjà visible, on le cache. Sinon, on l'affiche.
                if (resumeFilm.getVisibility() == View.VISIBLE) {
                    resumeFilm.setVisibility(View.GONE);
                    resumeBouton.setText("Infos et détails >"); // On change le texte pour indiquer que l'on peut déplier.
                } else {
                    resumeFilm.setVisibility(View.VISIBLE);
                    resumeBouton.setText("Infos et détails <"); // On change le texte pour indiquer que l'on peut replier.
                }
            }
        });

        // action de la croix pour retour à la page d'avant, la page précédente
        final ShapeableImageView croixImage = findViewById(R.id.croix_image);

        croixImage.setOnClickListener(v -> {
            onBackPressed();  // Retourner à l'activité précédente
        });

        // action une fois que j'ai cliqué sur l'image et ça lance la vidéo

        // Récupère les vues de l'image et de la vidéo
        final ImageView imageFilmAvantVideo = findViewById(R.id.ImageFilmAvantVideo);
        final VideoView videoViewBandeAnnonce = findViewById(R.id.videoViewBandeAnnonce);

        // Associer une image du drawable à l'ImageView
        //imageFilmAvantVideo.setImageResource(R.drawable.montecristoback);  // film_image est le nom de ton image dans drawable

        // Mettre un OnClickListener sur l'image
        imageFilmAvantVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cacher l'image
                imageFilmAvantVideo.setVisibility(View.GONE);
                croixImage.setVisibility(View.GONE);

                // Rendre la vidéo visible et commencer la lecture
                videoViewBandeAnnonce.setVisibility(View.VISIBLE);
                croixImage.setVisibility(View.VISIBLE);
                videoViewBandeAnnonce.start(); // Si tu veux commencer la vidéo automatiquement
            }
        });


        TextView titreTextView = findViewById(R.id.titre); // l'id doit correspondre à ton layout
        ImageView imageView = findViewById(R.id.ImageFilmAvantVideo);


        // Récupération du titre
        Intent intent = getIntent();
        String titre = intent.getStringExtra("titreModifié");
        int imageResId = intent.getIntExtra("imageResId", -1);

        // Vérifie que ce n'est pas null
        if (titre != null) {
            titreTextView.setText(titre);
        } else {
            titreTextView.setText("Titre introuvable");
        }

        // Affichage de l'image si elle existe
        if (imageResId != -1) {
            imageView.setImageResource(imageResId);
        } else {
            // Optionnel : Afficher une image par défaut si l'image n'est pas trouvée
            imageView.setImageResource(R.drawable.ic_launcher_foreground);  // Par exemple, une image par défaut
        }



        //ViewCompat A METTRE ici avec id = Main2
    }
}