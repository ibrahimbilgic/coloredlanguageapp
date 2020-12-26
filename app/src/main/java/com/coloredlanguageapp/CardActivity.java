package com.coloredlanguageapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class CardActivity extends AppCompatActivity {
    private TextView hello,basicAct;
    private FirebaseAuth firebaseAuth;
    private Button settings;
    private BottomNavigationView bottomNavigationView;
    public static boolean cardActTrue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        cardActTrue = true;

        hello = findViewById(R.id.hello);
        settings = findViewById(R.id.settings);
        basicAct = findViewById(R.id.BasicAct);

        basicAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToBasicAct = new Intent(CardActivity.this,BasicActivity.class);
                startActivity(goToBasicAct);
            }
        });


        if(PreferenceUtils.isNightModeSystemCheck(CardActivity.this)){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        }
        else{
            if(PreferenceUtils.isNightModeSystemCheck(CardActivity.this)){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
            else if(PreferenceUtils.isNightModeCheck(CardActivity.this)){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
            else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        }

        //initialize and assign variable
        BottomNavigationView bottomNavigationView =findViewById(R.id.bottom_nav);

        //set home selected
        bottomNavigationView.setSelectedItemId(R.id.home);
        // Perform ItemSelectedListener

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        return true;

                    case R.id.practice:
                        startActivity(new Intent(getApplicationContext(),PracticeActivity.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;

                    case R.id.collection:
                        startActivity(new Intent(getApplicationContext(),CollectionActivity.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                }
                return false;
            }
        });

        String name = PreferenceUtils.getName(this);
        int unicode = 0x1F60E;
        String emoji = getEmoji(unicode);

        hello.setText("Hoş Geldin "+name+" "+emoji);

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CardActivity.this,SettingsActivity.class);
                startActivity(intent);
            }
        });

    }
    private String getEmoji(int unicode){
        return new String(Character.toChars(unicode));
    }
}