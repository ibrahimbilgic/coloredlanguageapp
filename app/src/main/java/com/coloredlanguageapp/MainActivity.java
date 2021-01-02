package com.coloredlanguageapp;

//27. 12. 20
// Created by 5 March Lovers..
// for potuk's eternal soul...

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;


public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Firebase messages... check
        FirebaseMessaging.getInstance().subscribeToTopic("first")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Done";
                        if (!task.isSuccessful()) {
                            msg = "Failed";
                        }

                    }
                });


        // BottomNav
        bottomNavigationView = findViewById(R.id.bottom_nav);

        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,new HomeFragment()).commit();
    }

    // Switch in fragments...
    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()){
                case R.id.home:
                    fragment = new HomeFragment();
                    break;

                case R.id.practice:
                    fragment = new PracticeFragment();
                    break;
                case R.id.collection:
                    fragment = new CollectionFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
            return true;
        }
    };
}