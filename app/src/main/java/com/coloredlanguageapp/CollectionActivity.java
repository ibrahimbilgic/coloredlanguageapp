package com.coloredlanguageapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CollectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);

        //initialize and assign variable
        BottomNavigationView bottomNavigationView =findViewById(R.id.bottom_nav);

        //set home selected
        bottomNavigationView.setSelectedItemId(R.id.collection);
        // Perform ItemSelectedListener


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),CardActivity.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;

                    case R.id.practice:
                        startActivity(new Intent(getApplicationContext(),PracticeActivity.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;

                    case R.id.collection:
                        return true;
                }
                return false;
            }
        });
    }
}