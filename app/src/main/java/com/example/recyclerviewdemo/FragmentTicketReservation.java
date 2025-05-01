package com.example.recyclerviewdemo;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.imageview.ShapeableImageView;

public class FragmentTicketReservation extends Fragment {

    public FragmentTicketReservation() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ticket_reservation, container, false);

        // Gestion du bouton "Réserver mon ticket"
        view.findViewById(R.id.validationTicket).setOnClickListener(v -> {
            // Lorsque le bouton est cliqué, on démarre l'activité de sélection des places
            Intent intent = new Intent(getActivity(), ReservationPlaces.class);

            // Passer l'horaire sélectionné à l'activité
            String horaire = getArguments().getString("horaire");
            intent.putExtra("horaire", horaire);

            // Démarrer l'activité
            startActivity(intent);
        });

        //la croix pour fermer le fragment
        ShapeableImageView croixFragment = view.findViewById(R.id.croix_fragment);
        croixFragment.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();

            // Cache le conteneur du fragment
            View fragmentContainer = requireActivity().findViewById(R.id.fragmentTicketReservation);
            fragmentContainer.setVisibility(View.GONE);
        });

        return view;
    }


    //event du bouton réserver mon ticket qui amène sur la page de séléction de places
}
