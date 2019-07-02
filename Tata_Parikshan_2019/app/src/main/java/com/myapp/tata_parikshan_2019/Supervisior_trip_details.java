package com.myapp.tata_parikshan_2019;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Supervisior_trip_details extends AppCompatActivity {

    private TextView TransporterName,VechileNo,VechileType,DrivingNo,TareWeight,
    GrossWeight,material,loading,destination,TripStatus;
    private int ID,RESULTS_COUNT;
    private String WeightTare,WeightGross,TripId,TransporterID,materialDesc,dateTime;
    private byte[] imageGet;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supervisior_trip_details);

        Toolbar toolbar = findViewById(R.id.sup_toolbar3);
        setSupportActionBar(toolbar);


        intent = getIntent();
        TripId = intent.getStringExtra("tripId");
        WeightTare = intent.getStringExtra("WeightTare");
        WeightGross = intent.getStringExtra("WeightGross");


        TransporterName = findViewById(R.id.TripTransporter);
        VechileNo = findViewById(R.id.TripVechileNo);
        VechileType = findViewById(R.id.TripVechType);
        DrivingNo = findViewById(R.id.TripDR);
        TareWeight = findViewById(R.id.TripTareWt);
        GrossWeight = findViewById(R.id.TripGross);
        material = findViewById(R.id.TripMaterial);
        loading = findViewById(R.id.TripLoading);
        destination = findViewById(R.id.TripDest);
        TripStatus = findViewById(R.id.TripStatus);

        List<TripTable> tripTables = MainActivity.tataParikshanDatabase.myDao().getTrip();

        for (TripTable trp: tripTables){

            if (trp.getTrip_no().equals(TripId)){

                List<TransporterTable> transporterTables = MainActivity.tataParikshanDatabase.myDao().findByIds(trp.getTransporterId());
                for (TransporterTable trnp : transporterTables) {
                   TransporterName.setText(trnp.getTransporter_name());
                   DrivingNo.setText(trnp.getTransporter_driving_licence());
                }

                List<VechileTable> vechileTables = MainActivity.tataParikshanDatabase.myDao().getVechile();
                for (VechileTable vech : vechileTables) {
                    String vechile="";
                    List<VechileTable> byVechNo = MainActivity.tataParikshanDatabase.myDao().findByVechNo(vech.getVechile_no());
                    for (VechileTable vechileTable: byVechNo){
                        vechile = vechileTable.getVechile_id();
                    }
                    if (vechile.equals(trp.getVechile_Id())) {
                        VechileNo.setText(vech.getVechile_no());
                        VechileType.setText(vech.getVechile_type());
                    }
                }

                TareWeight.setText(WeightTare);
                GrossWeight.setText(WeightGross);
                material.setText(trp.getMaterial_type());
                loading.setText(trp.getLoading_area());
                destination.setText(trp.getDestination());

                ID = trp.getId();
                TransporterID = trp.getTransporterId();
                materialDesc = trp.getMaterial_description();
                imageGet = trp.getImage();
                dateTime = trp.getStartDateTime();

                TripStatus.setText(trp.getStatus());
            }

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sup_trip_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.submit:

                int flag = 0;
                List<TripTable> tables = MainActivity.tataParikshanDatabase.myDao().getTrip();
                for (TripTable trip : tables){
                    if (trip.getTrip_no().equals(TripId)) {
                        flag = trip.getFlag();
                    }
                }

                if (flag == 0) {

                    Date today = new Date();
                    SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
                    String dateToStr = format.format(today);

                    TripTable tripTable = new TripTable();
                    tripTable.setId(ID);
                    tripTable.setTrip_no(TripId);
                    tripTable.setTransporterId(TransporterID);
                    String vech_submit="";
                    List<VechileTable> vechileTables = MainActivity.tataParikshanDatabase.myDao().findByVechNo(VechileNo.getText().toString());
                    for (VechileTable vechileTable: vechileTables){
                        vech_submit = vechileTable.getVechile_id();
                    }
                    tripTable.setVechile_Id(vech_submit);
                    tripTable.setDestination(destination.getText().toString());
                    tripTable.setMaterial_type(material.getText().toString());
                    tripTable.setMaterial_description(materialDesc);
                    tripTable.setLoading_area(loading.getText().toString());
                    tripTable.setEndDateTime("00000000000000");
                    tripTable.setFlag(1);
                    tripTable.setImage(imageGet);
                    tripTable.setTareWeight(WeightTare.toString());
                    tripTable.setGrossWeight("0");
                    tripTable.setStatus("At Loading Position");
                    tripTable.setStartDateTime(dateToStr);

                    MainActivity.tataParikshanDatabase.myDao().updateTrip(tripTable);

                    if (intent.getStringExtra("check").equals("1")){
                        startActivity(new Intent(this,AdminActivity.class));
                        finish();
                    }
                    else if (intent.getStringExtra("check").equals("2")){
                        startActivity(new Intent(this,supervisiorHome.class));
                        finish();
                    }

                }

                if (flag == 1){
                    Date today = new Date();
                    SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
                    String dateToStr = format.format(today);

                    TripTable tripTable = new TripTable();
                    tripTable.setId(ID);
                    tripTable.setTrip_no(TripId);
                    tripTable.setTransporterId(TransporterID);
                    String vech_submit="";
                    List<VechileTable> vechileTables = MainActivity.tataParikshanDatabase.myDao().findByVechNo(VechileNo.getText().toString());
                    for (VechileTable vechileTable: vechileTables){
                        vech_submit = vechileTable.getVechile_id();
                    }
                    tripTable.setVechile_Id(vech_submit);
                    tripTable.setDestination(destination.getText().toString());
                    tripTable.setMaterial_type(material.getText().toString());
                    tripTable.setMaterial_description(materialDesc);
                    tripTable.setLoading_area(loading.getText().toString());
                    tripTable.setEndDateTime("00000000000000");
                    tripTable.setFlag(2);
                    tripTable.setImage(imageGet);
                    tripTable.setTareWeight(WeightTare.toString());
                    tripTable.setGrossWeight("0");
                    tripTable.setStatus("At Gross Weight Position");
                    tripTable.setStartDateTime(dateToStr);

                    MainActivity.tataParikshanDatabase.myDao().updateTrip(tripTable);

                    if (intent.getStringExtra("check").equals("1")){
                        startActivity(new Intent(this,AdminActivity.class));
                        finish();
                    }
                    else if (intent.getStringExtra("check").equals("2")){
                        startActivity(new Intent(this,supervisiorHome.class));
                        finish();
                    }
                }

                if (WeightGross != "0" && flag == 2){

                    Date today = new Date();
                    SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
                    String dateToStr = format.format(today);

                    TripTable tripTable = new TripTable();
                    tripTable.setId(ID);
                    tripTable.setTrip_no(TripId);
                    tripTable.setTransporterId(TransporterID);
                    String vech_submit="";
                    List<VechileTable> vechileTables = MainActivity.tataParikshanDatabase.myDao().findByVechNo(VechileNo.getText().toString());
                    for (VechileTable vechileTable: vechileTables){
                        vech_submit = vechileTable.getVechile_id();
                    }
                    tripTable.setVechile_Id(vech_submit);
                    tripTable.setDestination(destination.getText().toString());
                    tripTable.setMaterial_type(material.getText().toString());
                    tripTable.setMaterial_description(materialDesc);
                    tripTable.setLoading_area(loading.getText().toString());
                    tripTable.setEndDateTime("00000000000000");
                    tripTable.setFlag(3);
                    tripTable.setImage(imageGet);
                    tripTable.setTareWeight(WeightTare.toString());
                    tripTable.setGrossWeight(WeightGross.toString());
                    tripTable.setStatus("On the way");
                    tripTable.setStartDateTime(dateToStr);

                    MainActivity.tataParikshanDatabase.myDao().updateTrip(tripTable);

                    if (intent.getStringExtra("check").equals("1")){
                        startActivity(new Intent(this,AdminActivity.class));
                        finish();
                    }
                    else if (intent.getStringExtra("check").equals("2")){
                        startActivity(new Intent(this,supervisiorHome.class));
                        finish();
                    }

                }

                if (flag == 3){

                    Date today = new Date();
                    SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
                    String dateToStr = format.format(today);

                    TripTable tripTable = new TripTable();
                    tripTable.setId(ID);
                    tripTable.setTrip_no(TripId);
                    tripTable.setTransporterId(TransporterID);
                    String vech_submit="";
                    List<VechileTable> vechileTables = MainActivity.tataParikshanDatabase.myDao().findByVechNo(VechileNo.getText().toString());
                    for (VechileTable vechileTable: vechileTables){
                        vech_submit = vechileTable.getVechile_id();
                    }
                    tripTable.setVechile_Id(vech_submit);
                    tripTable.setDestination(destination.getText().toString());
                    tripTable.setMaterial_type(material.getText().toString());
                    tripTable.setMaterial_description(materialDesc);
                    tripTable.setLoading_area(loading.getText().toString());
                    tripTable.setEndDateTime(dateToStr);
                    tripTable.setFlag(4);
                    tripTable.setImage(imageGet);
                    tripTable.setTareWeight(WeightTare.toString());
                    tripTable.setGrossWeight(WeightGross.toString());
                    tripTable.setStatus("Trip Ended");
                    tripTable.setStartDateTime(dateTime);

                    MainActivity.tataParikshanDatabase.myDao().updateTrip(tripTable);

                    if (intent.getStringExtra("check").equals("1")){
                        startActivity(new Intent(this,AdminActivity.class));
                        finish();
                    }
                    else if (intent.getStringExtra("check").equals("2")){
                        startActivity(new Intent(this,supervisiorHome.class));
                        finish();
                    }
                }

                if (flag == 4){
                    if (intent.getStringExtra("check").equals("1")){
                        startActivity(new Intent(this,AdminActivity.class));
                        finish();
                    }
                    else if (intent.getStringExtra("check").equals("2")){
                        startActivity(new Intent(this,supervisiorHome.class));
                        finish();
                    }
                }
                break;

        }

        return super.onOptionsItemSelected(item);
    }
}
