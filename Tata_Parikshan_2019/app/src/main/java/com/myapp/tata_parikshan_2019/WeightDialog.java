package com.myapp.tata_parikshan_2019;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.List;

public class WeightDialog extends AppCompatDialogFragment {

    private EditText add_wt;
    private String TripID,wt;
    private int check;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.layout_dialog,null);

        final Bundle bundle = getArguments();
        TripID = bundle.getString("tripNo");
        String str = bundle.getString("message");

         final Intent intent = new Intent(getActivity(),Supervisior_trip_details.class);

        builder.setView(view)
                .setTitle(""+str)
                .setPositiveButton("Set", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        add_wt = view.findViewById(R.id.add_weight);

                        List<TripTable> tripTables = MainActivity.tataParikshanDatabase.myDao().getTrip();
                        for (TripTable trip : tripTables){
                            if (trip.getTrip_no().equals(TripID)){
                                check = trip.getFlag();
                                wt = trip.getTareWeight();
                            }
                        }
                        if (check == 0) {
                            intent.putExtra("tripId", TripID);
                            intent.putExtra("WeightTare", add_wt.getText().toString());
                            intent.putExtra("WeightGross", "0");
                            intent.putExtra("check",bundle.getString("checkPage"));
                            startActivity(intent);
                        }
                        if (check == 2){
                            intent.putExtra("tripId", TripID);
                            intent.putExtra("WeightTare",wt);
                            intent.putExtra("WeightGross", add_wt.getText().toString());
                            intent.putExtra("check",bundle.getString("checkPage"));
                            startActivity(intent);
                        }
                    }
                });

        return builder.create();

    }
}
