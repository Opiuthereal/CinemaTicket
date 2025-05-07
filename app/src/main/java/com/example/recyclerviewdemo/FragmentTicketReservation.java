package com.example.recyclerviewdemo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.imageview.ShapeableImageView;

public class FragmentTicketReservation extends Fragment {

    private TextView cinemaTextView;

    public FragmentTicketReservation() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ticket_reservation, container, false);

        // Initialiser le TextView
        cinemaTextView = view.findViewById(R.id.cinemaSelection);

        // Récupérer les noms des cinémas depuis SharedPreferences
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("FilmInfo", Context.MODE_PRIVATE);



        // Mise à jour avec les bons IDs venant de FilmDetail
        TextView titreTextView = view.findViewById(R.id.titreFilm);
        TextView jourTextView = view.findViewById(R.id.jourFilmTicket);
        TextView heureTextView = view.findViewById(R.id.heureFilmTicket);
        TextView versionTextView = view.findViewById(R.id.vostHoraire1Film);
        ShapeableImageView imageView = view.findViewById(R.id.ImageFilmResumeTicket);

        // Récupération des données depuis SharedPreferences
        String titre = sharedPreferences.getString("titre", "Titre inconnu");
        String jour = sharedPreferences.getString("jour", "Jour inconnu");
        String heure = sharedPreferences.getString("heure", "Heure inconnue");
        String version = sharedPreferences.getString("version", "Version inconnue");
        String cinema = sharedPreferences.getString("idCinema", "Nom Cinéma");

        // Mise à jour des vues avec les données récupérées
        titreTextView.setText(titre);
        jourTextView.setText(jour);
        heureTextView.setText(heure);
        versionTextView.setText(version);
        cinemaTextView.setText(cinema); // Mise à jour du TextView du cinéma

        // Récupérer et afficher l'image si nécessaire
        int imageResId = sharedPreferences.getInt("imageResId", -1);
        if (imageResId != -1) {
            imageView.setImageResource(imageResId);
        } else {
            imageView.setImageResource(R.drawable.ic_launcher_foreground); // Image par défaut
        }

        // Bouton "Réserver mon ticket"
        view.findViewById(R.id.validationTicket).setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ReservationPlaces.class);
            intent.putExtra("titre", titre);
            intent.putExtra("jour", jour);
            intent.putExtra("heure", heure);
            intent.putExtra("version", version);
            intent.putExtra("idCinema", cinema);
            startActivity(intent);
        });

        // Croix pour fermer le fragment
        ShapeableImageView croixFragment = view.findViewById(R.id.croix_fragment);
        croixFragment.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
            View fragmentContainer = requireActivity().findViewById(R.id.fragmentTicketReservation);
            fragmentContainer.setVisibility(View.GONE);
        });

        return view;
    }
}
