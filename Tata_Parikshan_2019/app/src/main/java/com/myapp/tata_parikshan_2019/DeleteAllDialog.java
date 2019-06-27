package com.myapp.tata_parikshan_2019;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import java.util.List;

public class DeleteAllDialog extends DialogFragment {

    public Dialog onCreateDialog(Bundle savedInstanceState){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Are you sure want to delete all ? ");

        Bundle bundle = getArguments();
        final String flag = bundle.getString("flag");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (flag == "1"){
                    TransporterTable table = new TransporterTable();
                    List<TransporterTable> transporterTables = MainActivity.tataParikshanDatabase.myDao().getTransportes();
                    for (TransporterTable trp : transporterTables){
                        table.setTransporter_id(trp.getTransporter_id());
                        MainActivity.tataParikshanDatabase.myDao().deleteTransporter(table);
                    }
                }

                if (flag == "2"){

                    VechileTable table = new VechileTable();
                    List<VechileTable> vechileTables = MainActivity.tataParikshanDatabase.myDao().getVechile();
                    for (VechileTable vech : vechileTables){
                        table.setVechile_id(vech.getVechile_id());
                        MainActivity.tataParikshanDatabase.myDao().deleteVechile(table);
                    }

                }

                if (flag == "3"){
                    TripTable table = new TripTable();
                    List<TripTable> tripTables = MainActivity.tataParikshanDatabase.myDao().getTrip();
                    for (TripTable trip : tripTables){
                        table.setId(trip.getId());
                        MainActivity.tataParikshanDatabase.myDao().deleteTrip(table);
                    }
                }

                if (flag == "4"){
                    ScannerTable scannerTable = new ScannerTable();
                    List<ScannerTable> scannerTableList = MainActivity.tataParikshanDatabase.myDao().getScanner();
                    for (ScannerTable scT : scannerTableList){
                        scannerTable.setSid(scT.getSid());
                        MainActivity.tataParikshanDatabase.myDao().deleteScanner(scannerTable);
                    }
                }

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Fragment fragmentTransaction = getParentFragment();
            }
        });

        return builder.create();
    }
}
