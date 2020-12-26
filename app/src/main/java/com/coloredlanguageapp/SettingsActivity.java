package com.coloredlanguageapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class SettingsActivity extends AppCompatActivity {
    private TextView logout;
    private FirebaseAuth firebaseAuth;
    private Switch aSwitch,bSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        logout = findViewById(R.id.logout);
        firebaseAuth = FirebaseAuth.getInstance();
        aSwitch = findViewById(R.id.switchNightMode);
        bSwitch = findViewById(R.id.switchUseDeviceSet);

        checkNightModeActivated();

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    PreferenceUtils.isNightMode(true,SettingsActivity.this);
                    bSwitch.setChecked(false);
                    recreate();
                }
                else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    PreferenceUtils.isNightMode(false,SettingsActivity.this);
                    recreate();
                }
            }
        });

        bSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                    PreferenceUtils.isNightModeSystem(true,SettingsActivity.this);
                    aSwitch.setChecked(false);
                    recreate();
                }
                else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    PreferenceUtils.isNightModeSystem(false,SettingsActivity.this);
                    recreate();
                }
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(SettingsActivity.this,R.style.Theme_AppCompat_Light_Dialog)
                        .setTitle("Çıkış Yap")
                        .setMessage("Çıkış yapmak istiyor musun?")
                        .setPositiveButton("Çık", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String empty = "";
                                PreferenceUtils.saveEmail(empty,SettingsActivity.this);
                                PreferenceUtils.saveName(empty,SettingsActivity.this);
                                firebaseAuth.getInstance().signOut();
                                Intent logoutIntent = new Intent(SettingsActivity.this,MainActivity.class);
                                startActivity(logoutIntent);
                                finish();
                            }
                        }).setNegativeButton("İptal",null)
                        .show();
            }
        });
    }

    public void checkNightModeActivated(){
        if(PreferenceUtils.isNightModeSystemCheck(SettingsActivity.this)){
            bSwitch.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        }
        else{
            if(PreferenceUtils.isNightModeSystemCheck(SettingsActivity.this)){
                bSwitch.setChecked(false);
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
            else if(PreferenceUtils.isNightModeCheck(SettingsActivity.this)){
                aSwitch.setChecked(true);
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
            else{
                aSwitch.setChecked(false);
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        }

    }

}