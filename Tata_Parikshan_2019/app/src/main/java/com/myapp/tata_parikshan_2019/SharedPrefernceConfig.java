package com.myapp.tata_parikshan_2019;


import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefernceConfig {

    private SharedPreferences sharedPreferences;
    private Context context;

    SharedPrefernceConfig(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(context.getResources()
                .getString(R.string.login_prefernces), Context.MODE_PRIVATE);
    }

    public void writeLoginStatus(Boolean status){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(context.getResources().getString(R.string.login_status_prefernces),status);
        editor.commit();
    }

    public boolean readLoginStatus(){

        boolean status = false;
        status = sharedPreferences.getBoolean(context.getResources().getString(R.string.login_status_prefernces)
                ,false);

        return status;
    }
}
