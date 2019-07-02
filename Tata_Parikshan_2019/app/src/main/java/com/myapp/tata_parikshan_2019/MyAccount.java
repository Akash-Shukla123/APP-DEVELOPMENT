package com.myapp.tata_parikshan_2019;


import android.animation.ValueAnimator;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyAccount extends Fragment {

    private TextView trCount, VechCount, TransCount,title,email;
    private int index1, index2, index3;

    public MyAccount() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_account, container, false);

        trCount = view.findViewById(R.id.trip_count);
        VechCount = view.findViewById(R.id.vechile_count);
        TransCount = view.findViewById(R.id.transporter_count);

        List<TripTable> tripTables = MainActivity.tataParikshanDatabase.myDao().getTrip();
        for (TripTable trp : tripTables) {
            index1++;
        }
        startCountAnimationTrip(index1);

        List<VechileTable> vechileTables = MainActivity.tataParikshanDatabase.myDao().getVechile();
        for (VechileTable vech : vechileTables) {
            index2++;
        }

        startCountAnimationVechile(index2);

        List<TransporterTable> transporterTables = MainActivity.tataParikshanDatabase.myDao().getTransportes();
        for (TransporterTable trsp : transporterTables) {
            index3++;
        }
        startCountAnimationTransporter(index3);

        Read(view);

        return view;
    }

    private void startCountAnimationTrip(int value) {
        final ValueAnimator animator = ValueAnimator.ofInt(0, value);
        animator.setDuration(500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                trCount.setText(valueAnimator.getAnimatedValue().toString());
            }
        });
        animator.start();
    }

    private void startCountAnimationVechile(int value1){
        final ValueAnimator animator = ValueAnimator.ofInt(0,value1);
        animator.setDuration(500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                VechCount.setText(valueAnimator.getAnimatedValue().toString());
            }
        });
        animator.start();
    }

    private void startCountAnimationTransporter(int value2){
        final ValueAnimator animator = ValueAnimator.ofInt(0,value2);
        animator.setDuration(500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                TransCount.setText(valueAnimator.getAnimatedValue().toString());
            }
        });
        animator.start();
    }

    public void Read(View view){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String name = preferences.getString("name","set your name");
        String settings_email = preferences.getString("email","Set Email Id");

        if (name!=null){
            title = view.findViewById(R.id.acc_title);
            title.setText(name);
        }

        if (settings_email!=null){
            email = view.findViewById(R.id.acc_email);
            email.setText(settings_email);
        }
    }


}
