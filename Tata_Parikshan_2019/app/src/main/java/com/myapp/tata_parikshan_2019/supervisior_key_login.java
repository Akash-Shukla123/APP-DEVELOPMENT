package com.myapp.tata_parikshan_2019;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.goodiebag.pinview.Pinview;

public class supervisior_key_login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supervisior_key_login);

        Pinview pinview = findViewById(R.id.pinView);
        pinview.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override
            public void onDataEntered(Pinview pinview, boolean fromUser) {

                if (pinview.getValue().equals("123456")){
                    startActivity(new Intent(supervisior_key_login.this,supervisiorHome.class));
                    finish();
                }
            }
        });
    }
}
