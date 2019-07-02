package com.myapp.tata_parikshan_2019;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddTrip extends Fragment{


    private static EditText destination,materialDescription,loadingArea;
    private AutoCompleteTextView transporterId,vechileNo;
    private TextView transporterView;
    private static Spinner material_Type;
    private static Button TripSave;
    public static final int REQUEST_CODE = 11;
    private String infodateTime,dateToStr,flag1,flag2,info,getBundle,tareGet,grossGet,signal="ok",start,end,stat;
    private DateTimePicker.OnFragmentInteractionListener mListener;
    private int count=1,backstackcount=0,index,tripTableId,flagGet;
    private byte[] byteArray,updArray;


    public AddTrip() {
        // Required empty public constructor
    }

    public static AddTrip newInstance() {
        AddTrip fragment = new AddTrip();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_trip, container, false);

        Spinner spinner = view.findViewById(R.id.add_material_type);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.materialNames));

        myAdapter.setDropDownViewResource(android.R.layout.select_dialog_item);

        spinner.setAdapter(myAdapter);

        FragmentManager fm1 = getFragmentManager();
        for (int entry =0;entry<fm1.getBackStackEntryCount(); entry++){
            backstackcount = backstackcount + fm1.getBackStackEntryAt(entry).getId();
        }

        transporterId = view.findViewById(R.id.add_TripTransporter);
        vechileNo = view.findViewById(R.id.add_Tripvechile_no);
        destination = view.findViewById(R.id.add_Destination);
        materialDescription = view.findViewById(R.id.add_material_desc);
        loadingArea = view.findViewById(R.id.add_loading_area);
        material_Type = view.findViewById(R.id.add_material_type);
        transporterView = view.findViewById(R.id.viewTransporterName);


        List<TransporterTable> tableList = MainActivity.tataParikshanDatabase.myDao().getTransportes();
        final List<String> Transnames = new ArrayList<String>();
        for (TransporterTable transporters: tableList){
            String transporter_info = transporters.getTransporter_id();
            Transnames.add(transporter_info);
        }
        String TransnamesArray[] = new String[Transnames.size()];
        String[] TRANSPORTERS = Transnames.toArray(TransnamesArray);

        ArrayAdapter<String> TransarrayAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1,TRANSPORTERS);
        transporterId.setAdapter(TransarrayAdapter);


        transporterId.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                String t_Id = transporterId.getText().toString();
                List<TransporterTable> transporterTables = MainActivity.tataParikshanDatabase.myDao().
                        findByIds(t_Id);
                String infoTransporterName="";
                for (TransporterTable trans : transporterTables){
                    infoTransporterName = trans.getTransporter_name();
                }
                transporterView.setText(infoTransporterName.toString());
            }
        });



        List<VechileTable> vechile = MainActivity.tataParikshanDatabase.myDao().getVechile();
        final List<String> names = new ArrayList<String>();
        for (VechileTable vch : vechile){
            String info = vch.getVechile_no();
            names.add(info);
        }
        String namesArray[] = new String[names.size()];
        String[] VECHILES = names.toArray(namesArray);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1,VECHILES);
        vechileNo.setAdapter(arrayAdapter);


        Date today = new Date();
        SimpleDateFormat format2 = new SimpleDateFormat("yyyyMMdd");
        String date = format2.format(today);

        final SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
        dateToStr = format.format(today);

        String datecount="",countTrp="";

            List<TripTable> tripTables = MainActivity.tataParikshanDatabase.myDao().getLastTripNo();
            for (TripTable tp : tripTables) {
                info = tp.getTrip_no();
            }

        if (info == null || info == ""){}
        else {
            char arr[] = new char[40];
            info.getChars(4, 13, arr, 0);

            for (int i = 1; i < 9; i++) {
                datecount = datecount + arr[i];
            }
            for (int i = 0; i < 1; i++) {
                countTrp = countTrp + arr[i];
            }

            if (!datecount.equals(date)) {
                count = 0;
            } else {

                while (count != Integer.parseInt(countTrp)) {
                    count++;
                }
            }
            count++;
        }



        if (backstackcount != 0){
            Bundle bundle = getArguments();
            getBundle = bundle.getString("IDOutput1").toString();

            if (getBundle!=null){
                int trp_id=0;
                List<TripTable> list = MainActivity.tataParikshanDatabase.myDao().getTrip();
                for (TripTable table : list){
                    if (getBundle.equals(table.getTrip_no())) {
                        trp_id = table.getId();
                    }
                }

                List<TripTable> tripTableList = MainActivity.tataParikshanDatabase.myDao().findByTrip(trp_id);
                for (TripTable tp: tripTableList) {
                    tripTableId = tp.getId();
                    transporterId.setText(tp.getTransporterId());
                    String vech="";
                    List<VechileTable> vechileTables = MainActivity.tataParikshanDatabase.myDao().findByVechID(tp.getVechile_Id());
                    for (VechileTable vechileTable: vechileTables){
                        vech = vechileTable.getVechile_no();
                    }
                    vechileNo.setText(vech);
                    destination.setText(tp.getDestination());
                    materialDescription.setText(tp.getMaterial_description());
                    loadingArea.setText(tp.getLoading_area());
                    String[] material2 = getResources().getStringArray(R.array.materialNames);
                    for (int i = 0; i < material2.length; i++) {
                        if (tp.getMaterial_type().equals(material2[i])) {
                            index = i;
                        }
                    }
                    material_Type.setSelection(index, true);
                    updArray = tp.getImage();
                    flagGet = tp.getFlag();
                    start = tp.getStartDateTime();
                    end = tp.getEndDateTime();
                    tareGet = tp.getTareWeight();
                    grossGet = tp.getGrossWeight();
                    stat = tp.getStatus();
                }
            }
        }

        TripSave = view.findViewById(R.id.tripSave);
        TripSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getInfo = "";
                String tripId = "TRIP" + count + dateToStr;
                String tpID = transporterId.getText().toString();
                String number = vechileNo.getText().toString();
                String dest = destination.getText().toString();
                String materialDesc = materialDescription.getText().toString();
                String loadArea = loadingArea.getText().toString();
                String material = material_Type.getSelectedItem().toString();

                List<TransporterTable> transporterTables = MainActivity.tataParikshanDatabase.myDao().findByIds(tpID);
                for (TransporterTable trp : transporterTables) {
                    getInfo = getInfo + trp.getTransporter_name();
                }

                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                BitMatrix bitMatrix = null;
                try {
                    bitMatrix = multiFormatWriter.encode(tripId, BarcodeFormat.QR_CODE, 500, 500);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);

                    // converting bitmap to byte
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 50, byteArrayOutputStream);
                    byteArray = byteArrayOutputStream.toByteArray();

                } catch (WriterException e) {
                    e.printStackTrace();
                }

                if (TextUtils.isEmpty(tpID) || TextUtils.isEmpty(number) || TextUtils.isEmpty(dest) ||
                        TextUtils.isEmpty(materialDesc) || TextUtils.isEmpty(loadArea) || TextUtils.isEmpty(materialDesc)) {
                    Toast.makeText(getActivity(), "Please Fill all the details.", Toast.LENGTH_SHORT).show();
                    signal = "no";
                } else {
                    String table1id = "", table2id = "";
                    List<TransporterTable> transporterTables1 = MainActivity.tataParikshanDatabase.myDao().findByIds(tpID);
                    for (TransporterTable transporter : transporterTables1) {
                        table1id = transporter.getTransporter_id();
                    }
                    List<VechileTable> vechileTableList = MainActivity.tataParikshanDatabase.myDao().findByVechID(number);
                    for (VechileTable vechileTable : vechileTableList) {
                        table2id = vechileTable.getVechile_id();
                    }

                    if (table1id == null || table2id == null) {
                        Alertadded alertadded = new Alertadded();
                        alertadded.show(getFragmentManager(), "alert");

                        transporterId.setText("");
                        vechileNo.setText("");
                        destination.setText("");
                        materialDescription.setText("");
                        loadingArea.setText("");
                        material_Type.setSelection(0, true);

                        signal = "no";
                    }


                    if (tpID.length() != 18 || !tpID.equals(tpID.toUpperCase())) {
                        transporterId.setError("Please Fill appropriate Transporter ID");
                        transporterId.setText("");
                        signal = "no";
                    }


                    if (number.length() != 9 || !vechileNo.getText().toString().
                            equals(vechileNo.getText().toString().toUpperCase())) {
                        vechileNo.setError("Fill Appropriate details.");
                        vechileNo.setText("");
                        signal = "no";
                    }

                    if (getBundle != null && signal == "ok") {

                        TripTable tripTableupd = new TripTable();
                        tripTableupd.setId(tripTableId);
                        tripTableupd.setTrip_no(getBundle);
                        tripTableupd.setTransporterId(tpID);
                        String vech="";
                        List<VechileTable> vechileTables = MainActivity.tataParikshanDatabase.myDao().findByVechNo(number);
                        for (VechileTable vechileTable: vechileTables){
                            vech = vechileTable.getVechile_id();
                        }
                        tripTableupd.setVechile_Id(vech);
                        tripTableupd.setDestination(dest);
                        tripTableupd.setMaterial_description(materialDesc);
                        tripTableupd.setLoading_area(loadArea);
                        tripTableupd.setMaterial_type(material);
                        tripTableupd.setImage(updArray);
                        tripTableupd.setStartDateTime(start);
                        tripTableupd.setEndDateTime(end);
                        tripTableupd.setTareWeight(tareGet);
                        tripTableupd.setGrossWeight(grossGet);
                        tripTableupd.setStatus(stat);
                        tripTableupd.setFlag(flagGet);

                        vechileDialog vechileDialog = new vechileDialog();
                        vechileDialog.show(getFragmentManager(), "TripUpdate");

                        MainActivity.tataParikshanDatabase.myDao().updateTrip(tripTableupd);

                    }

                    String vechiles="";
                    List<VechileTable> vechileTables3 = MainActivity.tataParikshanDatabase.myDao().findByVechNo(number);
                    for (VechileTable vechileTable3: vechileTables3){
                        vechiles = vechileTable3.getVechile_id();
                    }

                    List<TripTable> tripTableList = MainActivity.tataParikshanDatabase.myDao().findByTripDetails(tpID,vechiles);
                    for (TripTable tpTb: tripTableList){
                        if (tpTb.getFlag() != 4){
                            signal = "no";
                            Toast.makeText(getContext(),"Trip is not ended with this transporter and vechile",Toast.LENGTH_LONG).show();

                            transporterId.setText("");
                            transporterView.setText("");
                            vechileNo.setText("");
                            destination.setText("");
                            materialDescription.setText("");
                            loadingArea.setText("");
                            material_Type.setSelection(0, true);
                        }
                    }

                        if (flag1 != "notrunnable" && flag2 != "notrunnable" && signal == "ok") {

                            TripTable tripTable = new TripTable();

                            tripTable.setTrip_no(tripId);
                            tripTable.setTransporterId(tpID);
                            String vechId="";
                            List<VechileTable> vechileTables2 = MainActivity.tataParikshanDatabase.myDao().findByVechNo(number);
                            for (VechileTable vechileTable2: vechileTables2){
                                vechId = vechileTable2.getVechile_id();
                            }

                            Log.i("found2222",""+vechId);

                            tripTable.setVechile_Id(vechId);
                            tripTable.setDestination(dest);
                            tripTable.setMaterial_description(materialDesc);
                            tripTable.setLoading_area(loadArea);
                            tripTable.setMaterial_type(material);
                            tripTable.setImage(byteArray);
                            tripTable.setStartDateTime(dateToStr);
                            tripTable.setEndDateTime("00000000000000");
                            tripTable.setTareWeight("0");
                            tripTable.setGrossWeight("0");
                            tripTable.setStatus("At Tare Position");
                            tripTable.setFlag(0);

                            MainActivity.tataParikshanDatabase.myDao().addTrip(tripTable);

                            transporterId.setText("");
                            vechileNo.setText("");
                            destination.setText("");
                            materialDescription.setText("");
                            loadingArea.setText("");
                            material_Type.setSelection(0, true);

                            Bundle args = new Bundle();
                            args.putString("tripID", tripId);

                            QrCodeGenerator qrCodeGenerator = new QrCodeGenerator();
                            qrCodeGenerator.setArguments(args);
                            getFragmentManager().beginTransaction().replace(R.id.sup_frame_container, qrCodeGenerator)
                                    .addToBackStack(null).commit();

                        }
                    }
                }
        });

        return view;
    }

}
