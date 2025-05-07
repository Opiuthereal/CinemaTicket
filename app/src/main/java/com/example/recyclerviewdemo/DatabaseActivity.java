package com.example.recyclerviewdemo;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DatabaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_database);



        //Ajout Acteur
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        EditText PrenomActeur = findViewById(R.id.editPrenomActeur);
        EditText NomActeur = findViewById(R.id.editNomActeur);
        Button AjouterActeur = findViewById(R.id.buttonAjouterActeur);

        AjouterActeur.setOnClickListener(v -> CreateActeur(db, PrenomActeur, NomActeur));



        //Ajout Auteur
        EditText PrenomAuteur = findViewById(R.id.editPrenomAuteur);
        EditText NomAuteur = findViewById(R.id.editNomAuteur);
        Button AjouterAuteur = findViewById(R.id.buttonAjouterAuteur);

        AjouterAuteur.setOnClickListener(v -> CreateAuteur(db, PrenomAuteur, NomAuteur));



        //Ajout Film
        EditText Titre = findViewById(R.id.editTitre);
        EditText DateSortie = findViewById(R.id.editDateSortie);
        EditText Duree = findViewById(R.id.editDuree);
        EditText Image = findViewById(R.id.editImage);
        EditText Resume = findViewById(R.id.editResume);
        EditText Note = findViewById(R.id.editNote);
        Button AjouterFilm = findViewById(R.id.buttonAjouterFilm);

        AjouterFilm.setOnClickListener(v -> CreateFilm(db, Titre, DateSortie, Duree, Image, Resume, Note));



        //Ajout Genre
        EditText NomGenre = findViewById(R.id.editNomGenre);
        Button AjouterGenre = findViewById(R.id.buttonAjouterGenre);

        AjouterGenre.setOnClickListener(v -> CreateGenre(db, NomGenre));

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.Main2), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        //Ajout Cinema
        EditText NomCine = findViewById(R.id.editCine);
        EditText numero = findViewById(R.id.editNumero);
        EditText rue = findViewById(R.id.editRue);
        EditText ville = findViewById(R.id.editVille);
        Button AjouterCine = findViewById(R.id.buttonAjouterCine);

        AjouterCine.setOnClickListener(v -> CreateCine(db, NomCine, numero, rue, ville));

        //Ajout Salles
        EditText idCine = findViewById(R.id.editIdCine);
        EditText numSalle = findViewById(R.id.editNumSalle);
        EditText typeDiff = findViewById(R.id.editTypeDiffusion);
        EditText x = findViewById(R.id.editX);
        EditText y = findViewById(R.id.editY);
        Button AjouterSalle = findViewById(R.id.buttonAjouterSalle);

        AjouterSalle.setOnClickListener(v -> CreateSalle(db, idCine, numSalle, typeDiff, x, y));

        //Ajout Diffusion (référence film seulement)
        EditText iDCine = findViewById(R.id.editIDCine);
        EditText numDiff = findViewById(R.id.editnumDiff);
        EditText idFilm = findViewById(R.id.editidFilm);
        Button AjouterFilmDiff = findViewById(R.id.buttonAjouterFilmDiff);

        AjouterFilmDiff.setOnClickListener(v -> CreateRefDiff(db, iDCine, numDiff, idFilm));
    }
    public void CreateRefDiff(FirebaseFirestore database, EditText iDCine, EditText numDiff, EditText idFilm) {
        String Cine = iDCine.getText().toString().trim();
        String num = numDiff.getText().toString().trim();
        String CheminFilm = idFilm.getText().toString().trim();

        if (Cine.isEmpty() || num.isEmpty() || CheminFilm.isEmpty()) {
            System.out.println("Champ vide !");
            return;
        }

        DocumentReference filmRef = database.document(CheminFilm);

        Map<String, Object> data = new HashMap<>();
        data.put("Film", filmRef);

        database.collection("Cinema").document(Cine).collection("Diffusion").document(num)
                .set(data, SetOptions.merge())
                .addOnSuccessListener(aVoid ->
                        Toast.makeText(this, "Diffusion ajoutée avec référence !", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Erreur : " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }


    public void CreateSalle(FirebaseFirestore database, EditText idCine, EditText numSalle, EditText typeDiff, EditText x, EditText y) {
        String idCinema = idCine.getText().toString().trim();
        String salle = numSalle.getText().toString().trim();
        String diffusion = typeDiff.getText().toString().trim();
        String abscisseSiege = x.getText().toString().trim();
        String ordonneSiege = y.getText().toString().trim();

        if (salle.isEmpty() || diffusion.isEmpty() || ordonneSiege.isEmpty()) {
            System.out.println("Champ vide !");
            return;
        }

        Map<String, Object> data = new HashMap<>();
        data.put("typeDiffusion", diffusion);

        database.collection("Cinema").document(idCinema).collection("Salle").document(salle)
                .set(data)
                .addOnSuccessListener(aVoid ->
                        Toast.makeText(this, "Cine ajouté avec succès", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Erreur : " + e.getMessage(), Toast.LENGTH_SHORT).show());

        CreateSiege(database, abscisseSiege, ordonneSiege, idCinema, salle);
    }

    public void CreateSiege(FirebaseFirestore database, String x, String y, String idCinema, String salle) {
        int compteur = 0;
        List<Integer> abscisse = new ArrayList<>();

        if (x != null && !x.trim().isEmpty()) {
            abscisse = toArray(x);
        }

        List<Integer> ordonne = toArray(y);

        for (int i = 0; i < 15; i++) {
            if (!ordonne.contains(i)) {
                for (int j = 0; j < 12; j++) {
                    if (!abscisse.contains(j)) {

                        Map<String, Object> data = new HashMap<>();
                        data.put("position", Arrays.asList(i, j));

                        database.collection("Cinema")
                                .document(idCinema)
                                .collection("Salle")
                                .document(salle)
                                .collection("Siege")
                                .document(Integer.toString(compteur))
                                .set(data)
                                .addOnSuccessListener(aVoid ->
                                        Toast.makeText(this, "Siège ajouté avec succès", Toast.LENGTH_SHORT).show())
                                .addOnFailureListener(e ->
                                        Toast.makeText(this, "Erreur : " + e.getMessage(), Toast.LENGTH_SHORT).show());

                        compteur++;
                    }
                }
            }
        }
    }

    public List<Integer> toArray(String coordone) {
        String[] parts = coordone.split(",");
        List<Integer> resultat = new ArrayList<>();

        for (String part : parts) {
            part = part.trim();
            if (!part.isEmpty()) {
                resultat.add(Integer.parseInt(part));
            }
        }

        return resultat;
    }



    public void CreateCine(FirebaseFirestore database, EditText NomCine, EditText numero, EditText rue, EditText ville) {
        String Cine = NomCine.getText().toString().trim();
        String Num = numero.getText().toString().trim();
        String Rue = rue.getText().toString().trim();
        String Ville = ville.getText().toString().trim();

        if (Cine.isEmpty() || Num.isEmpty() || Rue.isEmpty() || Ville.isEmpty()) {
            System.out.println("Champ vide !");
            return;
        }

        String docId = toMajusculeNoAccent(Cine);

        Map<String, Object> data = new HashMap<>();
        data.put("nom", Cine);
        data.put("numéro", Num);
        data.put("rue", Rue);
        data.put("ville", Ville);

        database.collection("Cinema").document(docId)
                .set(data)
                .addOnSuccessListener(aVoid ->
                        Toast.makeText(this, "Cine ajouté avec succès", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Erreur : " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
    public void CreateActeur(FirebaseFirestore database, EditText editPrenom, EditText editNom) {
        String prenom = editPrenom.getText().toString().trim();
        String nom = editNom.getText().toString().trim();

        if (prenom.isEmpty() || nom.isEmpty()) {
            System.out.println("Prénom ou nom vide !");
            return;
        }

        String docId = prenom + nom;

        Map<String, Object> data = new HashMap<>();
        data.put("prenom", prenom);
        data.put("nom", nom);

        database.collection("Acteur").document(docId)
                .set(data)
                .addOnSuccessListener(aVoid ->
                        Toast.makeText(this, "Acteur ajouté avec succès", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Erreur : " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    public void CreateFilm (FirebaseFirestore database, EditText editTitre, EditText editDateSortie, EditText editDuree, EditText editImage, EditText editResume, EditText editNote ) {
        String Titre = editTitre.getText().toString().trim();
        String DateSortie = editDateSortie.getText().toString().trim();
        String Duree = editDuree.getText().toString().trim();
        String Image = editImage.getText().toString().trim();
        String Resume = editResume.getText().toString().trim();
        String Note = editNote.getText().toString().trim();

        if (Titre.isEmpty() || DateSortie.isEmpty() || Duree.isEmpty() || Image.isEmpty() || Resume.isEmpty() || Note.isEmpty()) {
            System.out.println("Prénom ou nom vide !");
            return;
        }

        String docId = toMajusculeNoAccent(Titre);

        Map<String, Object> filmdata = new HashMap<>();
        filmdata.put("titre", Titre);
        filmdata.put("dateSortie", DateSortie);
        filmdata.put("duree", Duree);
        filmdata.put("image", Image);
        filmdata.put("resume", Resume);
        filmdata.put("note", Note);

        database.collection("Film").document(docId)
                .set(filmdata)
                .addOnSuccessListener(aVoid ->
                        Toast.makeText(this, "Film ajouté avec succès", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Erreur : " + e.getMessage(), Toast.LENGTH_SHORT).show());

    }

    public void CreateAuteur(FirebaseFirestore database, EditText editPrenom, EditText editNom) {
        String prenom = editPrenom.getText().toString().trim();
        String nom = editNom.getText().toString().trim();

        if (prenom.isEmpty() || nom.isEmpty()) {
            System.out.println("Prénom ou nom vide !");
            return;
        }

        String docId = prenom + nom;

        Map<String, Object> data = new HashMap<>();
        data.put("prenom", prenom);
        data.put("nom", nom);

        database.collection("Auteur").document(docId)
                .set(data)
                .addOnSuccessListener(aVoid ->
                        Toast.makeText(this, "Auteur ajouté avec succès", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Erreur : " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    public void CreateGenre(FirebaseFirestore database, EditText NomGenre) {
        String genre = NomGenre.getText().toString().trim();

        if (genre.isEmpty()) {
            System.out.println("Prénom ou nom vide !");
            return;
        }

        Map<String, Object> data = new HashMap<>();
        data.put("nom", genre);

        database.collection("TypeFilm").document(genre)
                .set(data)
                .addOnSuccessListener(aVoid ->
                        Toast.makeText(this, "Genre ajouté avec succès", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Erreur : " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    static String toMajusculeNoAccent(String input) {
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        String withoutAccents = normalized.replaceAll("\\p{M}", "");
        String cleaned = withoutAccents.replaceAll("[^a-zA-Z0-9 ]", " ");

        StringBuilder result = new StringBuilder();
        for (String word : cleaned.trim().toLowerCase().split("\\s+")) {
            if (!word.isEmpty()) {
                result.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1));
            }
        }
        return result.toString();
    }

}