package com.example.recyclerviewdemo;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Insets;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        //Choix des cinemas avec mon spinner
        Spinner spinner = findViewById(R.id.spinnerCinema);

        String[] items = {"Cinéma Pathé", "Cinéma UGC", "Cinéma Gaumont"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, items
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        //----------------------------------------------------------------------------------------------------
        //PATHE


        //Pour le premier cinema avec ses films
        RecyclerView recyclerView = findViewById(R.id.recyclerViewFilms);

        List<FilmRecyclerView> films = new ArrayList<>();
        films.add(new FilmRecyclerView("Le compte de Monte Cristo", "14:00", "16:30", "19:00", "21:45", "VO", "VOST", "VF", "VF", R.drawable.montecristoback, R.drawable.pegi16));
        films.add(new FilmRecyclerView("Batman", "13h00", "15h15", "18h30", "20h00", "VO", "VOST", "VF", "VO", R.drawable.batman, R.drawable.pegi16));
        // Ajoute autant de films que tu veux

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(new MyAdapter(getApplicationContext(), films));

        //Pour le second cinema avec ses films
        RecyclerView recyclerView2 = findViewById(R.id.recyclerViewFilms2);

        List<FilmRecyclerView> films2 = new ArrayList<>();
        films2.add(new FilmRecyclerView("Ta mere en slip", "14:00", "16:30", "19:00", "21:45", "VO", "VOST", "VF", "VF", R.drawable.ic_launcher_foreground, R.drawable.pegi16));
        films2.add(new FilmRecyclerView("ton caca bo", "13h00", "15h15", "18h30", "20h00", "VO", "VOST", "VF", "VO", R.drawable.ic_launcher_background, R.drawable.pegi16));
        // Ajoute autant de films que tu veux

        recyclerView2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView2.setAdapter(new MyAdapter(getApplicationContext(), films2));

        //----------------------------------------------------------------------------------------------------
        //UGC

        //Pour le premier cinema avec ses films
        RecyclerView recyclerView3 = findViewById(R.id.recyclerViewFilms3);

        List<FilmRecyclerView> films3 = new ArrayList<>();
        films3.add(new FilmRecyclerView("HAHA", "14:00", "16:30", "19:00", "21:45", "VO", "VOST", "VF", "VF", R.drawable.montecristoback, R.drawable.pegi16));
        films3.add(new FilmRecyclerView("LOL", "13h00", "15h15", "18h30", "20h00", "VO", "VOST", "VF", "VO", R.drawable.batman, R.drawable.pegi16));
        // Ajoute autant de films que tu veux

        recyclerView3.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView3.setAdapter(new MyAdapter(getApplicationContext(), films3));

        //Pour le second cinema avec ses films
        RecyclerView recyclerView4 = findViewById(R.id.recyclerViewFilms4);

        List<FilmRecyclerView> films4 = new ArrayList<>();
        films4.add(new FilmRecyclerView("PROUT", "14:00", "16:30", "19:00", "21:45", "VO", "VOST", "VF", "VF", R.drawable.ic_launcher_foreground, R.drawable.pegi16));
        films4.add(new FilmRecyclerView("MERDE", "13h00", "15h15", "18h30", "20h00", "VO", "VOST", "VF", "VO", R.drawable.ic_launcher_background, R.drawable.pegi16));
        // Ajoute autant de films que tu veux

        recyclerView4.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView4.setAdapter(new MyAdapter(getApplicationContext(), films4));


        //----------------------------------------------------------------------------------------------------
        //GAUMONT

        //Pour le premier cinema avec ses films
        RecyclerView recyclerView5 = findViewById(R.id.recyclerViewFilms5);

        List<FilmRecyclerView> films5 = new ArrayList<>();
        films5.add(new FilmRecyclerView("NAN", "14:00", "16:30", "19:00", "21:45", "VO", "VOST", "VF", "VF", R.drawable.montecristoback, R.drawable.pegi16));
        films5.add(new FilmRecyclerView("OK DAK", "13h00", "15h15", "18h30", "20h00", "VO", "VOST", "VF", "VO", R.drawable.batman, R.drawable.pegi16));
        // Ajoute autant de films que tu veux

        recyclerView5.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView5.setAdapter(new MyAdapter(getApplicationContext(), films5));

        //Pour le second cinema avec ses films
        RecyclerView recyclerView6 = findViewById(R.id.recyclerViewFilms6);

        List<FilmRecyclerView> films6 = new ArrayList<>();
        films6.add(new FilmRecyclerView("LES VOYAGEURS", "14:00", "16:30", "19:00", "21:45", "VO", "VOST", "VF", "VF", R.drawable.ic_launcher_foreground, R.drawable.pegi16));
        films6.add(new FilmRecyclerView("BEN CA ALORS !", "13h00", "15h15", "18h30", "20h00", "VO", "VOST", "VF", "VO", R.drawable.ic_launcher_background, R.drawable.pegi16));
        // Ajoute autant de films que tu veux

        recyclerView6.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView6.setAdapter(new MyAdapter(getApplicationContext(), films6));

        //-----------------------------------------------------------------------------------------------------------------------

        //Tous les noms des cinémas pour ensuite agir dessus (apparaitre, disparaitre)
        TextView nomCinema = findViewById(R.id.nomCinema);
        TextView nomCinema2 = findViewById(R.id.nomCinema2);
        TextView nomCinema3 = findViewById(R.id.nomCinema3);
        TextView nomCinema4 = findViewById(R.id.nomCinema4);
        TextView nomCinema5 = findViewById(R.id.nomCinema5);
        TextView nomCinema6 = findViewById(R.id.nomCinema6);



        //event sur le spinner pour afficher tels ou tels films en fonction de la famille de cinéma séléctionnée

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: // Pathé
                        recyclerView.setVisibility(View.VISIBLE);
                        recyclerView2.setVisibility(View.VISIBLE);
                        recyclerView3.setVisibility(View.GONE);
                        recyclerView4.setVisibility(View.GONE);
                        recyclerView5.setVisibility(View.GONE);
                        recyclerView6.setVisibility(View.GONE);

                        nomCinema.setVisibility(View.VISIBLE);
                        nomCinema2.setVisibility(View.VISIBLE);
                        nomCinema3.setVisibility(View.GONE);
                        nomCinema4.setVisibility(View.GONE);
                        nomCinema5.setVisibility(View.GONE);
                        nomCinema6.setVisibility(View.GONE);
                        break;
                    case 1: // UGC
                        recyclerView.setVisibility(View.GONE);
                        recyclerView2.setVisibility(View.GONE);
                        recyclerView3.setVisibility(View.VISIBLE);
                        recyclerView4.setVisibility(View.VISIBLE);
                        recyclerView5.setVisibility(View.GONE);
                        recyclerView6.setVisibility(View.GONE);

                        nomCinema.setVisibility(View.GONE);
                        nomCinema2.setVisibility(View.GONE);
                        nomCinema3.setVisibility(View.VISIBLE);
                        nomCinema4.setVisibility(View.VISIBLE);
                        nomCinema5.setVisibility(View.GONE);
                        nomCinema6.setVisibility(View.GONE);
                        break;
                    case 2: // Gaumont
                        recyclerView.setVisibility(View.GONE);
                        recyclerView2.setVisibility(View.GONE);
                        recyclerView3.setVisibility(View.GONE);
                        recyclerView4.setVisibility(View.GONE);
                        recyclerView5.setVisibility(View.VISIBLE);
                        recyclerView6.setVisibility(View.VISIBLE);

                        nomCinema.setVisibility(View.GONE);
                        nomCinema2.setVisibility(View.GONE);
                        nomCinema3.setVisibility(View.GONE);
                        nomCinema4.setVisibility(View.GONE);
                        nomCinema5.setVisibility(View.VISIBLE);
                        nomCinema6.setVisibility(View.VISIBLE);
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Ne rien faire ici
            }
        });

        //ViewCompat A METTRE ici
    }
}
