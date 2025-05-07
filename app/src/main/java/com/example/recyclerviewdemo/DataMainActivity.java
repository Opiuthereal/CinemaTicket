package com.example.recyclerviewdemo;

import android.content.Context;
import android.util.Log;
import android.util.Pair;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import com.example.recyclerviewdemo.DatabaseActivity;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class DataMainActivity {

    public interface FilmCallback {
        void onFilmsLoaded(List<FilmRecyclerView> films);
    }

    public interface HoraireCallback {
        void onHorairesLoaded(List<ItemHoraire> itemsHoraires);
    }
    static FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static void addAffiche(Context context, String nomCinema, FilmCallback callback) {
        String idCine = DatabaseActivity.toMajusculeNoAccent(nomCinema);
        List<FilmRecyclerView> films = new ArrayList<>();

        db.collection("Cinema")
                .document(idCine)
                .collection("Diffusion")
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    Set<DocumentReference> uniqueFilmRefs = new HashSet<>();
                    AtomicInteger counter = new AtomicInteger();
                    int totalRefs = querySnapshot.size();

                    for (DocumentSnapshot doc : querySnapshot.getDocuments()) {
                        DocumentReference filmRef = doc.getDocumentReference("Film");
                        if (filmRef != null) {
                            uniqueFilmRefs.add(filmRef);
                        }
                    }

                    if (uniqueFilmRefs.isEmpty()) {
                        callback.onFilmsLoaded(films); // liste vide
                        return;
                    }

                    for (DocumentReference filmRef : uniqueFilmRefs) {
                        filmRef.get()
                                .addOnSuccessListener(filmSnapshot -> {
                                    if (filmSnapshot.exists()) {
                                        String titre = filmSnapshot.getString("titre");
                                        String imageName = filmSnapshot.getString("image");

                                        int imageResId = R.drawable.cine_logo;
                                        if (imageName != null) {
                                            imageResId = context.getResources().getIdentifier(
                                                    imageName, "drawable", context.getPackageName());
                                        }

                                        FilmRecyclerView film = new FilmRecyclerView(
                                                titre != null ? titre : "",
                                                "14:00", "16:30", "19:00", "21:45",
                                                "VO", "VOST", "VF", "VF", nomCinema,
                                                imageResId,
                                                R.drawable.pegi16
                                        );

                                        films.add(film);
                                    }

                                    if (films.size() == uniqueFilmRefs.size()) {
                                        callback.onFilmsLoaded(films);
                                    }

                                })
                                .addOnFailureListener(e -> {
                                    Log.e("Firestore", "Erreur récupération Film", e);
                                    if (films.size() == uniqueFilmRefs.size()) {
                                        callback.onFilmsLoaded(films);
                                    }
                                });
                    }

                })
                .addOnFailureListener(e -> {
                    Log.e("Firestore", "Erreur récupération Diffusion", e);
                    callback.onFilmsLoaded(films);
                });
    }

    public static void addFilm(TextView duree, TextView note, TextView Genre, TextView date, TextView Auteur, TextView Acteur, TextView resume, String titre) {
        String docId = DatabaseActivity.toMajusculeNoAccent(titre);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Film").document(docId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        duree.setText(documentSnapshot.getString("duree"));
                        note.setText(documentSnapshot.getString("note"));
                        date.setText(documentSnapshot.getString("dateSortie"));
                        resume.setText(documentSnapshot.getString("resume"));

                        // GENRE
                        DocumentReference genreRef = documentSnapshot.getDocumentReference("Genre");
                        if (genreRef != null) {
                            genreRef.get().addOnSuccessListener(genreSnap -> {
                                if (genreSnap.exists()) {
                                    Genre.setText(genreSnap.getString("nom"));
                                }
                            });
                        }

                        // AUTEUR
                        DocumentReference auteurRef = documentSnapshot.getDocumentReference("Auteur");
                        if (auteurRef != null) {
                            auteurRef.get().addOnSuccessListener(auteurSnap -> {
                                if (auteurSnap.exists()) {
                                    String nom = auteurSnap.getString("nom");
                                    String prenom = auteurSnap.getString("prenom");
                                    Auteur.setText((prenom != null ? prenom : "") + " " + (nom != null ? nom : ""));
                                }
                            });
                        }

                        // ACTEUR
                        DocumentReference acteurRef = documentSnapshot.getDocumentReference("Acteur");
                        if (acteurRef != null) {
                            acteurRef.get().addOnSuccessListener(acteurSnap -> {
                                if (acteurSnap.exists()) {
                                    String nom = acteurSnap.getString("nom");
                                    String prenom = acteurSnap.getString("prenom");
                                    Acteur.setText((prenom != null ? prenom : "") + " " + (nom != null ? nom : ""));
                                }
                            });
                        }

                    } else {
                        Log.e("Firestore", "Document Film non trouvé : " + docId);
                    }
                })
                .addOnFailureListener(e -> Log.e("Firestore", "Erreur récupération Film : " + docId, e));
    }

    public static void nomVid(String titre, TextView result) {
        String docId = DatabaseActivity.toMajusculeNoAccent(titre);

        db.collection("Film").document(docId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String videoName = documentSnapshot.getString("video");
                        if (videoName != null) {
                            result.setText(videoName);
                        } else {
                            result.setText("Vidéo non trouvée");
                        }
                    } else {
                        result.setText("Film introuvable");
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("Firestore", "Erreur récupération vidéo", e);
                    result.setText("Erreur de chargement");
                });
    }

    public static void addHoraire(Context context, String nomCine, String titre, HoraireCallback callback) {
        String cineId = DatabaseActivity.toMajusculeNoAccent(nomCine);
        String titreId = DatabaseActivity.toMajusculeNoAccent(titre);

        List<Pair<Date, ItemHoraire>> horairesTemp = new ArrayList<>();

        db.collection("Cinema")
                .document(cineId)
                .collection("Diffusion")
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    for (DocumentSnapshot doc : querySnapshot.getDocuments()) {
                        DocumentReference filmRef = doc.getDocumentReference("Film");
                        if (filmRef != null && filmRef.getPath().equals("Film/" + titreId)) {
                            Timestamp timestamp = doc.getTimestamp("date");
                            if (timestamp != null) {
                                Date date = timestamp.toDate();
                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM", Locale.getDefault());
                                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

                                String jour = dateFormat.format(date);
                                String heure = timeFormat.format(date);

                                ItemHoraire item = new ItemHoraire(jour, heure, "VF");
                                horairesTemp.add(new Pair<>(date, item));
                            }
                        }
                    }

                    // Trie les horaires par date
                    Collections.sort(horairesTemp, Comparator.comparing(pair -> pair.first));

                    // Extrait la liste finale triée
                    List<ItemHoraire> sortedItems = new ArrayList<>();
                    for (Pair<Date, ItemHoraire> pair : horairesTemp) {
                        sortedItems.add(pair.second);
                    }

                    callback.onHorairesLoaded(sortedItems);
                })
                .addOnFailureListener(e -> {
                    Log.e("Firestore", "Erreur récupération des horaires", e);
                    callback.onHorairesLoaded(new ArrayList<>());
                });
    }

}
