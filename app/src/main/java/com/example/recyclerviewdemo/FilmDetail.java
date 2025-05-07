package com.example.recyclerviewdemo;

import static com.example.recyclerviewdemo.DataMainActivity.addFilm;
import static com.example.recyclerviewdemo.DataMainActivity.nomVid;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.List;
import com.example.recyclerviewdemo.DataMainActivity;

public class FilmDetail extends AppCompatActivity implements MyAdapterHoraire.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_film_detail);

        // Récupération du titre et de l'image de film
        Intent intent = getIntent();
        String titre = intent.getStringExtra("titreModifié");
        String nomCine = intent.getStringExtra("idCinema");
        TextView titreTextView = findViewById(R.id.titre);
        ImageView imageView = findViewById(R.id.ImageFilmAvantVideo);
        TextView duree = findViewById(R.id.dureeFilm);
        TextView note = findViewById(R.id.noteFilm);
        TextView genre = findViewById(R.id.genreFilm);
        TextView date = findViewById(R.id.dateSortieFilmBdd);
        TextView auteur = findViewById(R.id.auteurs);
        TextView acteur = findViewById(R.id.acteurs);
        TextView resume = findViewById(R.id.resumeFilm);
        TextView videoNom = findViewById(R.id.nomVideo);
        VideoView videoView = findViewById(R.id.videoViewBandeAnnonce);

        nomVid(titre, videoNom); // met à jour le TextView


        resume.post(() -> {
            String videoName = videoNom.getText().toString();
            int videoResId = getResources().getIdentifier(videoName, "raw", getPackageName());

            if (videoResId != 0) {
                String videoPath = "android.resource://" + getPackageName() + "/" + videoResId;
                Uri uri = Uri.parse(videoPath);
                videoView.setVideoURI(uri);

                // Optionnel : ajouter des contrôles
                MediaController mediaController = new MediaController(videoView.getContext());
                mediaController.setAnchorView(videoView);
                videoView.setMediaController(mediaController);

                videoView.start();
            } else {
                Log.e("Vidéo", "Ressource RAW non trouvée pour : " + videoName);
            }
        });


        addFilm(duree, note, genre, date, auteur, acteur, resume, titre);


        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        //controles de son de la vidéo
        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        audioManager.requestAudioFocus(focusChange -> {
        }, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

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
                    resumeBouton.setText("Infos et détails  ▶"); // On change le texte pour indiquer que l'on peut déplier.
                } else {
                    resumeFilm.setVisibility(View.VISIBLE);
                    resumeBouton.setText("Infos et détails  ▼"); // On change le texte pour indiquer que l'on peut replier.
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

        int imageResId = intent.getIntExtra("imageResId", -1);

        // Vérifie que ce n'est pas null
        if (titre != null) {
            titreTextView.setText(titre);
        } else {
            titreTextView.setText("Titre introuvable");
        }

        // Affichage de la bonne image si elle existe
        if (imageResId != -1) {
            imageView.setImageResource(imageResId);
        } else {
            // Optionnel : Afficher une image par défaut si l'image n'est pas trouvée
            imageView.setImageResource(R.drawable.ic_launcher_foreground);  // Par exemple, une image par défaut
        }


        setupRecyclerViewHoraire(R.id.recyclerViewHoraire, nomCine, titre);

        // Fragment de réservation de tickets
        FragmentTicketReservation fragment = new FragmentTicketReservation();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentTicketReservation, fragment)
                .commit();

        //je lance mon fragment plus haut et sharedPreferences pour lui attribuer les bonnes infos
        SharedPreferences sharedPreferences = getSharedPreferences("FilmInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("titre", intent.getStringExtra("titreModifié"));
        editor.putString("jour", intent.getStringExtra("jour"));
        editor.putString("heure", intent.getStringExtra("heure"));
        editor.putString("version", intent.getStringExtra("version"));
        editor.putString("idCinema", intent.getStringExtra("idCinema"));
        editor.putString("duree", intent.getStringExtra("duree"));
        editor.putInt("imageResId", intent.getIntExtra("imageResId", -1));
        editor.apply(); // ou commit()

    }

    private void setupRecyclerViewHoraire(int recyclerViewId, String nomCine, String titre) {
        RecyclerView recyclerView = findViewById(recyclerViewId);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // Appel Firestore asynchrone
        DataMainActivity.addHoraire(this, nomCine, titre, horaires -> {
            MyAdapterHoraire adapter = new MyAdapterHoraire(this, horaires, this);
            recyclerView.setAdapter(adapter);

            if (horaires.isEmpty()) {
                Toast.makeText(this, "Aucun horaire disponible", Toast.LENGTH_SHORT).show();
            }
        });
    }



    // Fonction déclenchée lorsqu’un horaire est cliqué
    public void onItemClick(ItemHoraire itemHoraire) {
        Log.d("FilmDetail", "Item clicked: " + itemHoraire.getHoraireFilm());

        FrameLayout fragmentContainer = findViewById(R.id.fragmentTicketReservation);
        fragmentContainer.setVisibility(View.VISIBLE);


        // Créer une instance du fragment et passer les arguments
        FragmentTicketReservation fragment = new FragmentTicketReservation();
        Bundle args = new Bundle();
        args.putString("horaire", itemHoraire.getHoraireFilm());  // Passer l'horaire sélectionné
        fragment.setArguments(args);

        // Afficher le fragment dans le conteneur, avec retour possible grâce à addToBackStack
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragmentTicketReservation, fragment)
                .addToBackStack(null)
                .commit();

        // Faire défiler vers le fragment après une petite attente (le temps que le fragment apparaisse)
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            ScrollView scrollView = findViewById(R.id.scrollviewDown);
            if (scrollView != null && fragmentContainer != null) {
                scrollView.smoothScrollTo(0, fragmentContainer.getTop());
            }
        }, 200); // 200 ms d’attente pour que le fragment soit bien ajouté


    }


    //ViewCompat A METTRE ici avec id = Main2

}
