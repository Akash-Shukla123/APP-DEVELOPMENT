package com.myapp.tata_parikshan_2019;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.room.InvalidationTracker;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.List;
import java.util.regex.Pattern;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    RelativeLayout relay1,relay2;

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {

            relay1.setVisibility(View.VISIBLE);
            relay2.setVisibility(View.VISIBLE);
        }
    };

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$");
    private SharedPrefernceConfig sharedPrefernceConfig;
    private EditText Username,Password;
    private Button loginBtn,LoginSupervisior;
    public static TataParikshanDatabase tataParikshanDatabase;
    private long backpressedTime;
    private Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPrefernceConfig = new SharedPrefernceConfig(getApplicationContext());

        relay1 = findViewById(R.id.rellay1);
        relay2= findViewById(R.id.rellay2);
        handler.postDelayed(runnable,1000);
        Username = findViewById(R.id.admin_username);
        Password = findViewById(R.id.admin_password);


        if (sharedPrefernceConfig.readLoginStatus()){
            startActivity(new Intent(MainActivity.this,AdminActivity.class));
            finish();
        }

        tataParikshanDatabase = Room.databaseBuilder(getApplicationContext(),TataParikshanDatabase.class,
                "tataParikshanDb").allowMainThreadQueries().fallbackToDestructiveMigration().build();


        loginBtn = findViewById(R.id.LoginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(PasswordChecker().equals(false) || UsernameChecker().equals(false)){
                    Username.setText("");
                    Password.setText("");
                }

                else if (!Username.getText().toString().equals(new AdminTable().getUsername()) ||
                        !Password.getText().toString().equals(new AdminTable().getPassword())){

                    Toast.makeText(MainActivity.this,"Login Failed. Please Try Again",
                            Toast.LENGTH_SHORT).show();
                    Username.setText("");
                    Password.setText("");
                }

                else{
                    startActivity(new Intent(MainActivity.this,AdminActivity.class));
                    sharedPrefernceConfig.writeLoginStatus(true);
                    finish();
                }

            }
        });

        LoginSupervisior = findViewById(R.id.LoginSupervisior);
        LoginSupervisior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),supervisior_key_login.class));
            }
        });

    }

    private Boolean UsernameChecker(){

        String usernameInput = Username.getText().toString().trim();

        if(usernameInput.isEmpty()){
            Username.setError("Field can't be empty");
            return false;
        }

        else {
            Username.setError(null);
            return true;
        }
    }

    private Boolean PasswordChecker(){

        String passwordInput = Password.getText().toString().trim();

        if(passwordInput.isEmpty()){
            Password.setError("Field can't be empty");
            return false;
        }

        else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()){
            Password.setError("Password is too weak");
            return false;
        }

        else {
            Password.setError(null);
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        if (backpressedTime + 4000 > System.currentTimeMillis()){
            backToast.cancel();
            super.onBackPressed();
            return;
        }
        else {
            backToast = Toast.makeText(getApplicationContext(),"Press back again to exit",Toast.LENGTH_SHORT);
            backToast.show();
        }
        backpressedTime = System.currentTimeMillis();
    }
}
