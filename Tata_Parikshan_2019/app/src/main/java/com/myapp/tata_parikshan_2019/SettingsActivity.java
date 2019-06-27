package com.myapp.tata_parikshan_2019;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragment;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.PreferenceScreen;

import java.util.regex.Pattern;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        Intent intent = getIntent();
        String activityIntent = intent.getStringExtra("getIntent");
        //String activityIntent2 = intent.getStringExtra("gentIntent2");

        if (activityIntent.equals("1")) {

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }

        if (activityIntent.equals("2")){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new MoreFragment())
                    .commit();
        }

        Toolbar toolbar = findViewById(R.id.toolbarSettings);
        setSupportActionBar(toolbar);

        View view = new View(getApplicationContext());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    public static class MoreFragment extends PreferenceFragmentCompat{

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.more_preferences, rootKey);

            final Preference appVersion = findPreference("app_version");
            appVersion.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    AppVersionDialog appVersionDialog = new AppVersionDialog();
                    appVersionDialog.show(getFragmentManager(),"AppVersion");
                    return true;
                }
            });
        }
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {

        private static final Pattern PASSWORD_PATTERN =
                Pattern.compile("^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$");
        private String check = "false";
        private String flag1 = "1", flag2 = "2", flag3 = "3", flag4="4";

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);

            Preference transporter = findPreference("delete_Transporter");
            transporter.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {

                    Bundle bundle = new Bundle();
                    bundle.putString("flag",flag1);
                    DeleteAllDialog deleteAllDialog = new DeleteAllDialog();
                    deleteAllDialog.setArguments(bundle);
                    deleteAllDialog.show(getFragmentManager(),"deleteTransporters");

                    return true;
                }
            });


            Preference vechile = findPreference("delete_vechile");
            vechile.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {

                    Bundle bundle = new Bundle();
                    bundle.putString("flag",flag2);
                    DeleteAllDialog deleteAllDialog = new DeleteAllDialog();
                    deleteAllDialog.setArguments(bundle);
                    deleteAllDialog.show(getFragmentManager(),"deleteVechile");

                    return true;
                }
            });

            Preference trip = findPreference("delete_trip");
            trip.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {

                    Bundle bundle = new Bundle();
                    bundle.putString("flag",flag3);
                    DeleteAllDialog deleteAllDialog = new DeleteAllDialog();
                    deleteAllDialog.setArguments(bundle);
                    deleteAllDialog.show(getFragmentManager(),"deleteTrip");

                    return true;
                }
            });

            Preference scan = findPreference("delete_scan");
            scan.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {

                    Bundle bundle = new Bundle();
                    bundle.putString("flag",flag4);
                    DeleteAllDialog deleteAllDialog = new DeleteAllDialog();
                    deleteAllDialog.setArguments(bundle);
                    deleteAllDialog.show(getFragmentManager(),"deleteScan");

                    return true;
                }
            });

        }

    }

}