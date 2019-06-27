package com.myapp.tata_parikshan_2019;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

public class TransporterDialog extends DialogFragment{
    private static final int REQUEST_CODE = 1111;

    public Dialog onCreateDialog(Bundle savedInstanceState){

        Bundle myArgs = getArguments();
        String myValue = myArgs.getString("transporterID");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Transporter Id is " + myValue);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Fragment fragmentTransaction = getParentFragment();
            }
        });

        return builder.create();
    }

}
