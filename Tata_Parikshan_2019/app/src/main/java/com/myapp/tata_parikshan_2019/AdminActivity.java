package com.myapp.tata_parikshan_2019;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.provider.ContactsContract;
import android.view.Display;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceManager;
import androidx.room.Room;

import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.SearchView;
import android.widget.TextView;

public class AdminActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private SharedPrefernceConfig sharedPrefernceConfig;
    public static FragmentManager fragmentManager;
    private TextView title,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        PreferenceManager.setDefaultValues(this,R.xml.root_preferences,false);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(),ScannerActivity.class);
                intent.putExtra("checkerPage","1");
                startActivity(intent);
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        fragmentManager = getSupportFragmentManager();
        if (findViewById(R.id.admin_fragment)!=null){

            if (savedInstanceState!=null){
                return;
            }
            fragmentManager.beginTransaction().add(R.id.admin_fragment,new AdminHomeFragment(),"AdminHome").commit();
        }

        sharedPrefernceConfig = new SharedPrefernceConfig(getApplicationContext());

    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.admin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.action_settings:
                break;

            case R.id.more_item:
                Intent intent = new Intent(this,SettingsActivity.class);
                intent.putExtra("getIntent","2");
                startActivity(intent);
                break;

            case R.id.home:
                startActivity(new Intent(this,AdminActivity.class));
                break;

            case R.id.logout:
                sharedPrefernceConfig.writeLoginStatus(false);
                startActivity(new Intent(AdminActivity.this,MainActivity.class));
                finish();
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_trip) {
            fragmentManager.beginTransaction().replace(R.id.admin_fragment, new TripRecyclerView(),"tripRecycler")
                    .addToBackStack(null).commit();

        } else if (id == R.id.nav_vechile) {
            fragmentManager.beginTransaction().replace(R.id.admin_fragment,new VechileRecylerView(),"vechileRecycler").
                    addToBackStack(null).commit();
        }
        else if (id == R.id.nav_transporter){
            fragmentManager.beginTransaction().replace(R.id.admin_fragment,new TransporterRecyclerView(),"transporterRecycler").
                    addToBackStack("dminHomeFragment").commit();
        }

        else if (id == R.id.nav_trip_report){
            fragmentManager.beginTransaction().replace(R.id.admin_fragment,new fragment_trip_report(),"transporterRecycler").
                    addToBackStack("dminHomeFragment").commit();
        }

        else if (id == R.id.nav_scanned) {

            fragmentManager.beginTransaction().replace(R.id.admin_fragment,new ScannedResultsRecycler()).addToBackStack(null)
                    .commit();
        }

        else if (id == R.id.nav_account) {

            fragmentManager.beginTransaction().replace(R.id.admin_fragment,new MyAccount()).addToBackStack(null)
                    .commit();
        }

        else if (id == R.id.settings){
            Intent intent = new Intent(this,SettingsActivity.class);
            intent.putExtra("getIntent","1");
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void Read(View view){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String name = preferences.getString("name","ADMIN");
        String settings_email = preferences.getString("email","admin@gmail.com");

        if (name!=null){
            title = view.findViewById(R.id.nav_title);
            title.setText(name);
        }

            if (settings_email!=null){
            email = view.findViewById(R.id.nav_email);
            email.setText(settings_email);
        }
    }

}
