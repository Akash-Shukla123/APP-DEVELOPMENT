package com.myapp.tata_parikshan_2019;


import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class AdminHomeFragment extends Fragment {

    private CardView cardTransporter,cardVechile;
    public AdminHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_home, container, false);

        /*cardTrip = view.findViewById(R.id.add_trip);
        cardTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               AdminActivity.fragmentManager.beginTransaction().replace(R.id.admin_fragment,
                 new AddTrip(),"AddTrip").addToBackStack(null).commit();
            }
        });*/

        cardTransporter = view.findViewById(R.id.add_transporter);
        cardTransporter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdminActivity.fragmentManager.beginTransaction().replace(R.id.admin_fragment,
                        new AddTransporters(),"AddTransporters").addToBackStack(null).commit();
            }
        });

        cardVechile = view.findViewById(R.id.add_vechile);
        cardVechile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdminActivity.fragmentManager.beginTransaction().replace(R.id.admin_fragment,
                        new AddVechile(),"AddVechile").addToBackStack(null).commit();
            }
        });

        return view;
    }
}
