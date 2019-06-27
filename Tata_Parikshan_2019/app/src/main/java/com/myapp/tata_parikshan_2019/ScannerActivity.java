package com.myapp.tata_parikshan_2019;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.net.Uri;
import android.net.UrlQuerySanitizer;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.Toast;

import com.google.zxing.Result;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.net.Uri.parse;

public class ScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private int TOTAL_COUNT,RESULTS_COUNT;
    private static final  int REQUEST_CAMERA = 1;
    private ZXingScannerView scannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);


        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

            if(checkPermission()){

                int a=0,b=0;
                List<TotalResults> totalResults1 = MainActivity.tataParikshanDatabase.myDao().getTotal();
                for (TotalResults results : totalResults1){
                    a=results.getId();
                    b=results.getTotal_scan();
                }

                TotalResults total = new TotalResults();
                total.setId(a);
                total.setTotal_scan(++b);

                MainActivity.tataParikshanDatabase.myDao().updateTotalResults(total);

                Toast.makeText(ScannerActivity.this,"Permission is granted",Toast.LENGTH_LONG).show();
            }

            else{
                TotalResults totalResults = new TotalResults();
                totalResults.setTotal_scan(1);
                MainActivity.tataParikshanDatabase.myDao().addTotal(totalResults);


                requestPermission();
            }
        }
    }

    private Boolean checkPermission(){

        return  (ContextCompat.checkSelfPermission(ScannerActivity.this, Manifest.permission.CAMERA))==
                PackageManager.PERMISSION_GRANTED;

    }

    private  void requestPermission(){
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},REQUEST_CAMERA);
    }

    private void onRequestPermissionResult(int requestCode, String permission[],int grantResult[]){

        switch (requestCode){

            case REQUEST_CAMERA:
                if (grantResult.length>0){
                    boolean cameraAccepted = grantResult[0] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted){
                        Toast.makeText(ScannerActivity.this,"Permission Granted",Toast.LENGTH_LONG).show();
                    }

                    else{
                        Toast.makeText(ScannerActivity.this,"Permission Denied",Toast.LENGTH_LONG).show();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                            if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)){
                                displayAlertMEssage("You need to allow Both permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
                                                }
                                            }
                                        });
                                return;
                            }
                        }
                    }
                }
                break;
        }
    }

    @Override
    public void  onResume(){

        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkPermission()){
                if (scannerView == null){
                    scannerView = new ZXingScannerView(this);
                    setContentView(scannerView);
                }
                scannerView.setResultHandler(this);
                scannerView.startCamera();
        }
            else {
                requestPermission();
            }
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        scannerView.stopCamera();
    }

    public void displayAlertMEssage(String message, DialogInterface.OnClickListener listener){

        new AlertDialog.Builder(ScannerActivity.this)
                .setMessage(message)
                .setPositiveButton("View", listener)
                .setNegativeButton("Cancel",null)
                .create()
                .show();
    }

    @Override
    public void handleResult(final Result result) {

        final String scanResult = result.getText();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Scan Result:");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                scannerView.resumeCameraPreview(ScannerActivity.this);

            }
        });

        builder.setNeutralButton("Visit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(scanResult));
                startActivity(intent);
            }
        });

        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String date = format.format(today);

        Date today1 = new Date();
        SimpleDateFormat format1 = new SimpleDateFormat("HH:mm:a");
        String date1 = format1.format(today1);
        int scan=RESULTS_COUNT++;
        Date currentTime = Calendar.getInstance().getTime();

        ScannerTable scanner3 = new ScannerTable();
        scanner3.setSid("SCAN"+RESULTS_COUNT);
        scanner3.setResults_Count(RESULTS_COUNT);
        scanner3.setResults(scanResult);
        scanner3.setDate(date);
        scanner3.setTime(date1);

        MainActivity.tataParikshanDatabase.myDao().addScanner(scanner3);


        builder.setMessage(scanResult);
        AlertDialog alert = builder.create();
        alert.show();
    }

}
