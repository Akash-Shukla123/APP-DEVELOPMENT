package com.myapp.tata_parikshan_2019;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddTransporters extends Fragment {


   private static EditText nameTransporter,phoneTransporter,idProofNo,postalCode
           ,permanentAddress,driving_licence;
   private static Spinner idProof_type;
   private static Button btnSaveTransporter;
   private int backstackcount=0,index;
   private String transporterPassID,signal="ok";

    public AddTransporters() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_transporters, container, false);

        Spinner spinner = view.findViewById(R.id.add_id_proof_type);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.names));

        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(myAdapter);

        FragmentManager fm3 = getFragmentManager();
        for (int entry =0;entry<fm3.getBackStackEntryCount(); entry++){
            backstackcount = backstackcount + fm3.getBackStackEntryAt(entry).getId();
        }

        nameTransporter = view.findViewById(R.id.add_name);
        phoneTransporter = view.findViewById(R.id.add_phone);
        idProof_type = view.findViewById(R.id.add_id_proof_type);
        idProofNo = view.findViewById(R.id.add_id_proof_no);
        postalCode = view.findViewById(R.id.add_postal_code);
        permanentAddress = view.findViewById(R.id.add_address);
        driving_licence = view.findViewById(R.id.add_driving_licence);


        if (backstackcount != 0){

            Bundle bundle = getArguments();
            transporterPassID = bundle.getString("IDOutput3");

            List<TransporterTable> transporterTables = MainActivity.tataParikshanDatabase.myDao().findByIds(transporterPassID);
            for (TransporterTable trnp: transporterTables){

                nameTransporter.setText(trnp.getTransporter_name());
                phoneTransporter.setText(trnp.getTransporter_phone_number());
                String [] trnpType = getResources().getStringArray(R.array.vechileNames);
                for (int i = 0; i < trnpType.length; i++) {
                    if (trnp.getTransporter_id_proof_type().equals(trnpType[i])) {
                        index = i;
                    }
                }
                idProof_type.setSelection(index, true);
                idProofNo.setText(trnp.getTransporter_id_proof_number());
                postalCode.setText(trnp.getTransporter_postalcode());
                permanentAddress.setText(trnp.getTransporter_address());
                driving_licence.setText(trnp.getTransporter_driving_licence());
            }

        }


        idProof_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = adapterView.getItemAtPosition(i).toString();
                if (selectedItem.equals("ADHAAR Card")){
                    setEditTextMaxLength(idProofNo,10);
                    idProofNo.setInputType(InputType.TYPE_CLASS_NUMBER);
                }
                if (selectedItem.equals("Voter ID")){
                    setEditTextMaxLength(idProofNo,10);
                    idProofNo.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
                }
                if (selectedItem.equals("PAN Card")){
                   setEditTextMaxLength(idProofNo,10);
                    idProofNo.setInputType(InputType.TYPE_CLASS_TEXT |InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
                }
                if (selectedItem.equals("PIO Card")){
                    setEditTextMaxLength(idProofNo,6);
                    idProofNo.setInputType(InputType.TYPE_CLASS_TEXT |InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        btnSaveTransporter = view.findViewById(R.id.saveTransporter);
        btnSaveTransporter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    if (TextUtils.isEmpty(nameTransporter.getText()) || TextUtils.isEmpty(phoneTransporter.getText()) ||
                            TextUtils.isEmpty(idProof_type.getSelectedItem().toString()) || TextUtils.isEmpty(idProofNo.getText())
                            || TextUtils.isEmpty(postalCode.getText()) || TextUtils.isEmpty(driving_licence.getText())
                            || TextUtils.isEmpty(permanentAddress.getText())) {

                        Toast.makeText(getActivity(), "Please fill all the details.", Toast.LENGTH_LONG).show();
                        signal = "no";
                    } else {

                        Date today = new Date();
                        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
                        String dateToStr = format.format(today);

                        final String transporter_id = "TRNP"+dateToStr;
                        String name = nameTransporter.getText().toString();
                        String phone = phoneTransporter.getText().toString();
                        String idProofType = idProof_type.getSelectedItem().toString();

                        String idproof = idProofNo.getText().toString();
                        String postal = postalCode.getText().toString();
                        String address = permanentAddress.getText().toString();
                        String drlicence = driving_licence.getText().toString();

                        if (phone.length() != 10) {
                            phoneTransporter.setError("enter valid phone number");
                            phoneTransporter.setText("");
                            signal="No";
                        }

                        if (idProofType.equals("ADHAAR Card")) {
                            if (idproof.length() != 10) {
                                idProofNo.setError("Your Id Proof No. must be of 10 digit for ADHAAR Card");
                                idProofNo.setText("");
                                signal="No";
                            }

                        }
                        if (idProofType.equals("PAN Card")) {

                            if (idproof.length() != 10) {
                                idProofNo.setError("Your Id Proof No. must be of 10 digit for PAN Card");
                                idProofNo.setText("");
                                signal="No";
                            } else if (!idProofNo.getText().toString().equals(idProofNo.getText().toString()
                                    .toUpperCase())) {
                                idProofNo.setError("enter valid pan card number");
                                idProofNo.setText("");
                                signal="No";
                            }

                        }

                        if (idProofType.equals("VOTER ID")) {

                            if (idproof.length() != 10) {
                                idProofNo.setError("Your Id Proof No. must be of 10 digit for Voter ID Card");
                                idProofNo.setText("");
                                signal="No";
                            } else if (!idProofNo.getText().toString().equals(idProofNo.getText().toString()
                                    .toUpperCase())) {
                                idProofNo.setError("enter valid Voter ID card number");
                                idProofNo.setText("");
                                signal="No";
                            }

                        }

                        if (idProofType.equals("PIO Card")) {

                            if (idproof.length() != 6) {
                                idProofNo.setError("Your Id Proof No. must be of 10 digit for PIO Card");
                                idProofNo.setText("");
                                signal="No";
                            } else if (!idProofNo.getText().toString().equals(idProofNo.getText().toString()
                                    .toUpperCase())) {
                                idProofNo.setError("enter valid PIO card number");
                                idProofNo.setText("");
                                signal="No";
                            }

                        }

                        if (drlicence.length() != 16 || !driving_licence.getText().toString().equals(
                                driving_licence.getText().toString().toUpperCase())) {
                            driving_licence.setError("Please enter valid driving licence");
                            driving_licence.setText("");
                            signal="No";
                        }


                        if (transporterPassID != null && signal=="ok") {

                                TransporterTable transporterTableupd = new TransporterTable();

                                transporterTableupd.setTransporter_id(transporterPassID);
                                transporterTableupd.setTransporter_name(name);
                                transporterTableupd.setTransporter_phone_number(phone);
                                transporterTableupd.setTransporter_id_proof_number(idproof);
                                transporterTableupd.setTransporter_id_proof_type(idProofType);
                                transporterTableupd.setTransporter_driving_licence(drlicence);
                                transporterTableupd.setTransporter_postalcode(postal);
                                transporterTableupd.setTransporter_address(address);

                                vechileDialog vechileDialog = new vechileDialog();
                                vechileDialog.show(getFragmentManager(), "transporterDialog");

                                MainActivity.tataParikshanDatabase.myDao().updateTransporter(transporterTableupd);

                        }
                        if (signal == "ok"){

                            TransporterTable transporterTable = new TransporterTable();
                            transporterTable.setTransporter_id(transporter_id);
                            transporterTable.setTransporter_name(name);
                            transporterTable.setTransporter_phone_number(phone);
                            transporterTable.setTransporter_id_proof_number(idproof);
                            transporterTable.setTransporter_id_proof_type(idProofType);
                            transporterTable.setTransporter_driving_licence(drlicence);
                            transporterTable.setTransporter_postalcode(postal);
                            transporterTable.setTransporter_address(address);

                            Bundle args = new Bundle();
                            args.putString("transporterID", transporter_id);
                            TransporterDialog transporterDialog = new TransporterDialog();
                            transporterDialog.setArguments(args);
                            transporterDialog.show(getFragmentManager(), "TransporterDialog");

                            MainActivity.tataParikshanDatabase.myDao().addTransporter(transporterTable);

                            nameTransporter.setText("");
                            phoneTransporter.setText("");
                            driving_licence.setText("");
                            idProof_type.setSelection(0, true);
                            idProofNo.setText("");
                            postalCode.setText("");
                            permanentAddress.setText("");
                        }
                    }

                }
        });

        return view;
    }

    public void  setEditTextMaxLength (EditText editTextMaxLength,int length){
        InputFilter[] FilterArray = new InputFilter[1];
        FilterArray[0] = new InputFilter.LengthFilter(length);
        editTextMaxLength.setFilters(FilterArray);
    }

}
