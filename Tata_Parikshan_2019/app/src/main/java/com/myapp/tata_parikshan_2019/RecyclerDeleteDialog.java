package com.myapp.tata_parikshan_2019;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import java.util.List;

public class RecyclerDeleteDialog extends DialogFragment {
String info="";
    public Dialog onCreateDialog(Bundle savedInstanceState){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Are you sure you want to delete ?");

        Bundle myArgs = getArguments();
        final String myValue = myArgs.getString("DeleteId");
        final String delete_id = myArgs.getString("DeleteId");

        char array[] = new char[30];
        myValue.getChars(0,4,array,0);

        for (int i=0;i<4;i++){
            info = info+array[i];
        }

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (info.equals("TRIP")){

                    int a= 0;
                    List<TripTable> tripTables = MainActivity.tataParikshanDatabase.myDao().getTrip();
                    for (TripTable trps: tripTables){
                        if (trps.getTrip_no().equals(delete_id)){
                             a = trps.getId();
                        }
                    }
                    TripTable tripTable = new TripTable();
                    tripTable.setId(a);
                    MainActivity.tataParikshanDatabase.myDao().deleteTrip(tripTable);

                    AdminActivity.fragmentManager.beginTransaction().replace(R.id.admin_fragment,new
                            TripRecyclerView()).addToBackStack(null).commit();
                }

                else if (info.equals("VECH")){

                    VechileTable vechileTable = new VechileTable();
                    vechileTable.setVechile_id(delete_id);
                    MainActivity.tataParikshanDatabase.myDao().deleteVechile(vechileTable);

                    AdminActivity.fragmentManager.beginTransaction().replace(R.id.admin_fragment,new
                            VechileRecylerView()).addToBackStack(null).commit();
                }

                else if (info.equals("TRNP")){

                    TransporterTable transporterTable = new TransporterTable();
                    transporterTable.setTransporter_id(delete_id);
                    MainActivity.tataParikshanDatabase.myDao().deleteTransporter(transporterTable);

                    AdminActivity.fragmentManager.beginTransaction().replace(R.id.admin_fragment,new
                            TransporterRecyclerView()).addToBackStack(null).commit();
                }

                else if (info.equals("SCAN")){
                    ScannerTable scannerTable = new ScannerTable();
                    scannerTable.setSid(delete_id);
                    MainActivity.tataParikshanDatabase.myDao().deleteScanner(scannerTable);

                    AdminActivity.fragmentManager.beginTransaction().replace(R.id.admin_fragment,new
                            ScannedResultsRecycler()).addToBackStack(null).commit();
                }

            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Fragment fragmentTransaction = getParentFragment();
            }
        });

        return builder.create();
    }

}
