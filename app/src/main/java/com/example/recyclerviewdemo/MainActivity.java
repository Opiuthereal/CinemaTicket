package com.example.recyclerviewdemo;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import com.example.recyclerviewdemo.DataMainActivity;

public class MainActivity extends AppCompatActivity {


    // Déclaration globale des TextView pour ne pas les redéclarer dans chaque méthode
    TextView nomCinema, nomCinema2, nomCinema21;
    TextView nomCinema3, nomCinema4, nomCinema41;
    TextView nomCinema5, nomCinema6, nomCinema61;

    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        //Intent intent = new Intent(MainActivity.this, DatabaseActivity.class);
        //startActivity(intent);

        Spinner spinner = findViewById(R.id.spinnerCinema);

        String[] items = {"Cinéma Pathé", "Cinéma UGC", "Cinéma Gaumont"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                R.layout.item_spinner,         // ton layout perso
                R.id.text_spinner_item,       // le TextView ID que tu viens de créer
                items
        );

        spinner.setAdapter(adapter);



        //----------------------------------------------------------------------------------------------------
        //PATHE


        // Pathé
        setupRecyclerView(R.id.recyclerViewFilms, "Pathé Wepler");
        setupRecyclerView(R.id.recyclerViewFilms2, "Pathé Palace");
        setupRecyclerView(R.id.recyclerViewFilms21, "Pathé Boulogne");

        // UGC
        setupRecyclerView(R.id.recyclerViewFilms3, "UGC Ciné Cité Paris 19");
        setupRecyclerView(R.id.recyclerViewFilms4, "UGC Issy Les Moulinaux");
        setupRecyclerView(R.id.recyclerViewFilms41, "UGC Opéra");

        // Gaumont
        setupRecyclerView(R.id.recyclerViewFilms5, "Gaumont");
        setupRecyclerView(R.id.recyclerViewFilms6, "Gaumont Parnasse");
        setupRecyclerView(R.id.recyclerViewFilms61, "Gaumont Saint-Denis");

        //-----------------------------------------------------------------------------------------------------------------------

        // Récupération des TextView une seule fois
        nomCinema = findViewById(R.id.nomCinema);
        nomCinema2 = findViewById(R.id.nomCinema2);
        nomCinema21 = findViewById(R.id.nomCinema21);
        nomCinema3 = findViewById(R.id.nomCinema3);
        nomCinema4 = findViewById(R.id.nomCinema4);
        nomCinema41 = findViewById(R.id.nomCinema41);
        nomCinema5 = findViewById(R.id.nomCinema5);
        nomCinema6 = findViewById(R.id.nomCinema6);
        nomCinema61 = findViewById(R.id.nomCinema61);


        View.OnClickListener cinemaClickListener = v -> {
            TextView clickedView = (TextView) v;
            String cinemaChoisi = clickedView.getText().toString();

            SharedPreferences sharedPreferences = getSharedPreferences("FilmInfo", Context.MODE_PRIVATE);
            sharedPreferences.edit().putString("cinema", cinemaChoisi).apply();

        };


        // Et tu l'appliques à tous tes TextView de cinéma
        nomCinema.setOnClickListener(cinemaClickListener);
        nomCinema2.setOnClickListener(cinemaClickListener);
        nomCinema21.setOnClickListener(cinemaClickListener);
        nomCinema3.setOnClickListener(cinemaClickListener);
        nomCinema4.setOnClickListener(cinemaClickListener);
        nomCinema41.setOnClickListener(cinemaClickListener);
        nomCinema5.setOnClickListener(cinemaClickListener);
        nomCinema6.setOnClickListener(cinemaClickListener);
        nomCinema61.setOnClickListener(cinemaClickListener);


        // ----------------------------------------------------------------------------------------------

        spinner = findViewById(R.id.spinnerCinema);

        // Listener du spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                afficherFilms(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Ne rien faire
            }
        });

        spinner.setSelection(0); // optionnel, selon ton besoin
        afficherFilms(0); // affichage initial
    }


    private void setupRecyclerView(int recyclerViewId, String cinemaName) {
        RecyclerView recyclerView = findViewById(recyclerViewId);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        DataMainActivity.addAffiche(this, cinemaName, films -> {
            MyAdapter adapter = new MyAdapter(this, films);
            recyclerView.setAdapter(adapter);
        });
    }

    // Méthode à l'extérieur de onCreate
    private void afficherFilms(int position) {
        RecyclerView recyclerView = findViewById(R.id.recyclerViewFilms);
        RecyclerView recyclerView2 = findViewById(R.id.recyclerViewFilms2);
        RecyclerView recyclerView21 = findViewById(R.id.recyclerViewFilms21);
        RecyclerView recyclerView3 = findViewById(R.id.recyclerViewFilms3);
        RecyclerView recyclerView4 = findViewById(R.id.recyclerViewFilms4);
        RecyclerView recyclerView41 = findViewById(R.id.recyclerViewFilms41);
        RecyclerView recyclerView5 = findViewById(R.id.recyclerViewFilms5);
        RecyclerView recyclerView6 = findViewById(R.id.recyclerViewFilms6);
        RecyclerView recyclerView61 = findViewById(R.id.recyclerViewFilms61);

        switch (position) {
            case 0: // Pathé
                recyclerView.setVisibility(View.VISIBLE);
                recyclerView2.setVisibility(View.VISIBLE);
                recyclerView21.setVisibility(View.VISIBLE);
                recyclerView3.setVisibility(View.GONE);
                recyclerView4.setVisibility(View.GONE);
                recyclerView41.setVisibility(View.GONE);
                recyclerView5.setVisibility(View.GONE);
                recyclerView6.setVisibility(View.GONE);
                recyclerView61.setVisibility(View.GONE);

                nomCinema.setVisibility(View.VISIBLE);
                nomCinema2.setVisibility(View.VISIBLE);
                nomCinema21.setVisibility(View.VISIBLE);
                nomCinema3.setVisibility(View.GONE);
                nomCinema4.setVisibility(View.GONE);
                nomCinema41.setVisibility(View.GONE);
                nomCinema5.setVisibility(View.GONE);
                nomCinema6.setVisibility(View.GONE);
                nomCinema61.setVisibility(View.GONE);
                break;
            case 1: // UGC
                recyclerView.setVisibility(View.GONE);
                recyclerView2.setVisibility(View.GONE);
                recyclerView21.setVisibility(View.GONE);
                recyclerView3.setVisibility(View.VISIBLE);
                recyclerView4.setVisibility(View.VISIBLE);
                recyclerView41.setVisibility(View.VISIBLE);
                recyclerView5.setVisibility(View.GONE);
                recyclerView6.setVisibility(View.GONE);
                recyclerView61.setVisibility(View.GONE);

                nomCinema.setVisibility(View.GONE);
                nomCinema2.setVisibility(View.GONE);
                nomCinema21.setVisibility(View.GONE);
                nomCinema3.setVisibility(View.VISIBLE);
                nomCinema4.setVisibility(View.VISIBLE);
                nomCinema41.setVisibility(View.VISIBLE);
                nomCinema5.setVisibility(View.GONE);
                nomCinema6.setVisibility(View.GONE);
                nomCinema61.setVisibility(View.GONE);
                break;
            case 2: // Gaumont
                recyclerView.setVisibility(View.GONE);
                recyclerView2.setVisibility(View.GONE);
                recyclerView21.setVisibility(View.GONE);
                recyclerView3.setVisibility(View.GONE);
                recyclerView4.setVisibility(View.GONE);
                recyclerView41.setVisibility(View.GONE);
                recyclerView5.setVisibility(View.VISIBLE);
                recyclerView6.setVisibility(View.VISIBLE);
                recyclerView61.setVisibility(View.VISIBLE);

                nomCinema.setVisibility(View.GONE);
                nomCinema2.setVisibility(View.GONE);
                nomCinema21.setVisibility(View.GONE);
                nomCinema3.setVisibility(View.GONE);
                nomCinema4.setVisibility(View.GONE);
                nomCinema41.setVisibility(View.GONE);
                nomCinema5.setVisibility(View.VISIBLE);
                nomCinema6.setVisibility(View.VISIBLE);
                nomCinema61.setVisibility(View.VISIBLE);
                break;
        }
    }

        //ViewCompat A METTRE ici
}
