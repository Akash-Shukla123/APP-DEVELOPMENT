package com.myapp.tata_parikshan_2019;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class supervisiorHome extends AppCompatActivity {

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supervisior_home);

        Toolbar toolbar = findViewById(R.id.sup_toolbar2);
        setSupportActionBar(toolbar);

        FloatingActionButton fab2 = findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(),ScannerActivity.class);
                intent.putExtra("checkerPage","2");
                startActivity(intent);
            }
        });


        fragmentManager = getSupportFragmentManager();
        if (findViewById(R.id.sup_frame_container) != null) {

            if (savedInstanceState != null) {
                return;
            }
            fragmentManager.beginTransaction().add(R.id.sup_frame_container, new AddTrip(), "AdminHome").commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sup_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.sup_logout:
                startActivity(new Intent(supervisiorHome.this, MainActivity.class));
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);

    }
}

