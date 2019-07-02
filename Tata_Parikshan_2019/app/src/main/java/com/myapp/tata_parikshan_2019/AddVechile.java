package com.myapp.tata_parikshan_2019;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddVechile extends Fragment {

    private static EditText vechileNo;
    private static Spinner vechileType;
    private static Button vechileSave;
    private String[] vchType;
    private String flag,passedID,signal="ok";
    private int backstackcount=0;
    private List<NewsItem> items;

    public AddVechile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_vechile, container, false);


        Spinner spinner = view.findViewById(R.id.add_vechile_type);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.vechileNames));

        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(myAdapter);

        FragmentManager fm = getFragmentManager();
        for (int entry =0;entry<fm.getBackStackEntryCount(); entry++){
            backstackcount = backstackcount + fm.getBackStackEntryAt(entry).getId();
        }


        vechileNo = view.findViewById(R.id.add_vechile_no);
        vechileType = view.findViewById(R.id.add_vechile_type);


        if (backstackcount != 0) {

            Bundle bundle = getArguments();
            passedID = bundle.getString("IDOutput2");


            if (passedID != null) {
                int index = 0;
                List<VechileTable> vechileTables = MainActivity.tataParikshanDatabase.myDao().findByVechID(passedID);
                for (VechileTable vch : vechileTables) {
                    vechileNo.setText(vch.getVechile_no().toString());
                    vchType = getResources().getStringArray(R.array.vechileNames);
                    for (int i = 0; i < vchType.length; i++) {
                        if (vch.getVechile_type().equals(vchType[i])) {
                            index = i;
                        }
                    }
                    vechileType.setSelection(index, true);
                }
            }
        }

        vechileSave = view.findViewById(R.id.vechileSave);
        vechileSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Date today = new Date();
                SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
                String dateToStr = format.format(today);

                final String VechileID = "VECH" + dateToStr;
                String Vnumber = vechileNo.getText().toString();
                String Vtype = vechileType.getSelectedItem().toString();

                if (TextUtils.isEmpty(Vnumber) || TextUtils.isEmpty(Vtype)) {
                    Toast.makeText(getActivity(), "Please fill all the details", Toast.LENGTH_SHORT).show();
                    signal = "no";
                } else {

                    if (!vechileNo.getText().toString().equals(vechileNo.getText().toString().toUpperCase()) ||
                            vechileNo.getText().length() != 9) {
                        vechileNo.setError("Please enter valid Vechile number");
                        vechileNo.setText("");
                        signal = "no";
                    }


                    if (passedID != null && signal == "ok") {

                        String checker = "";
                        List<VechileTable> vechileTableList = MainActivity.tataParikshanDatabase.myDao().findByVechID(passedID);
                        for (VechileTable vechileTable : vechileTableList){
                            if (vechileTable.getVechile_no().equals(vechileNo.getText().toString())){
                              checker = "false";
                            }
                        }

                        if (checker != "false") {
                            VechileTable vechileTableUpd = new VechileTable();
                            vechileTableUpd.setVechile_id(passedID);
                            vechileTableUpd.setVechile_no(vechileNo.getText().toString());
                            vechileTableUpd.setVechile_type(vechileType.getSelectedItem().toString());

                            TripTable trips = new TripTable();
                            List<TripTable> tripTables = MainActivity.tataParikshanDatabase.myDao().findByTripVechile(passedID);
                            for (TripTable table : tripTables) {

                                trips.setId(table.getId());
                                trips.setImage(table.getImage());
                                trips.setGrossWeight(table.getGrossWeight());
                                trips.setEndDateTime(table.getEndDateTime());
                                trips.setLoading_area(table.getLoading_area());
                                trips.setMaterial_description(table.getMaterial_description());
                                trips.setMaterial_type(table.getMaterial_type());
                                trips.setDestination(table.getDestination());
                                trips.setTransporterId(table.getTransporterId());
                                trips.setTrip_no(table.getTrip_no());
                                trips.setFlag(table.getFlag());
                                trips.setStartDateTime(table.getStartDateTime());
                                trips.setStatus(table.getStatus());
                                trips.setTareWeight(table.getTareWeight());
                                trips.setVechile_Id(passedID);

                                MainActivity.tataParikshanDatabase.myDao().updateTrip(trips);
                            }


                            MainActivity.tataParikshanDatabase.myDao().updateVechile(vechileTableUpd);
                            new vechileDialog().show(getFragmentManager(), "vechiledialog");
                        }

                    } else {

                        List<VechileTable> vechileTables2 = MainActivity.tataParikshanDatabase.myDao().getVechile();
                        for (VechileTable vech : vechileTables2) {
                            if (Vnumber.equals(vech.getVechile_no())) {
                                TripSaveDialog tripSaveDialog = new TripSaveDialog();
                                tripSaveDialog.show(getFragmentManager(), "VechileSave");
                                flag = "notrunnable";
                            }
                        }

                        if (flag == "notrunnable") {
                        } else {
                            VechileTable vechileTable = new VechileTable();
                            vechileTable.setVechile_id(VechileID);
                            vechileTable.setVechile_no(Vnumber);
                            vechileTable.setVechile_type(Vtype);

                            new vechileDialog().show(getFragmentManager(), "vechiledialog");

                            MainActivity.tataParikshanDatabase.myDao().addVechile(vechileTable);

                            vechileNo.setText("");
                            vechileType.setSelection(0, true);

                        }
                    }
                }
            }
        });

        return view;
    }


}
