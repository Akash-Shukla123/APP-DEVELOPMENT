package com.myapp.tata_parikshan_2019;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class QrCodeGenerator extends Fragment {

    private ImageView imgQrCode;
    private TextView txtQr;
    private Bitmap bitmap;
    private byte[] byteArray;

    public QrCodeGenerator() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_qr_code_generator, container, false);

        Bundle bundle = getArguments();
        String myID = bundle.getString("tripID");

        String tpID = bundle.getString("TripID");

        imgQrCode = view.findViewById(R.id.qrCode);
        txtQr = view.findViewById(R.id.qrcodeText);

        if (myID!=null){

            txtQr.setText("Trip ID : " +myID);

            int trp_id=0;
            List<TripTable> list = MainActivity.tataParikshanDatabase.myDao().getTrip();
            for (TripTable table : list){
                if (myID.equals(table.getTrip_no())) {
                    trp_id = table.getId();
                }
            }

            List<TripTable> tripTables = MainActivity.tataParikshanDatabase.myDao().findByTrip(trp_id);
            for (TripTable trpImg : tripTables){
                byteArray = trpImg.getImage();
            }

            bitmap = BitmapFactory.decodeByteArray(byteArray,0,byteArray.length);
            imgQrCode.setImageBitmap(bitmap);

        }

        if (tpID!=null){
            txtQr.setText("Trip Id : "+tpID);

            int trp_id=0;
            List<TripTable> list = MainActivity.tataParikshanDatabase.myDao().getTrip();
            for (TripTable table : list){
                if (tpID.equals(table.getTrip_no())) {
                    trp_id = table.getId();
                }
            }
            List<TripTable> tripTables = MainActivity.tataParikshanDatabase.myDao().findByTrip(trp_id);
            for (TripTable img : tripTables){
                byteArray = img.getImage();
                bitmap = BitmapFactory.decodeByteArray(byteArray,0,byteArray.length);

                imgQrCode.setImageBitmap(bitmap);
            }
        }

        return view;
    }

}
