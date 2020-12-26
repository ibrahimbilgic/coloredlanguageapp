package com.coloredlanguageapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.muddzdev.styleabletoast.StyleableToast;

import static com.coloredlanguageapp.CardActivity.cardActTrue;


// 22.11.20

public class MainActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener{
    private Button signIn;
    private Button signUp;
    private TextView textView;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       checkInternetConnection();

        if(PreferenceUtils.getEmail(this)!=null && PreferenceUtils.getEmail(this)!=""){
            Intent CardIntent = new Intent(MainActivity.this,CardActivity.class);
            startActivity(CardIntent);
            finish();
        }
        else{
            signIn = findViewById(R.id.signIn);
            signUp = findViewById(R.id.signUp);
            textView = findViewById(R.id.textView);

            int unicode = 0x1F44B;
            String emoji = getEmoji(unicode);

            textView.setText("Hoş geldin "+emoji);

            textView.setTranslationY(100);
            signIn.setTranslationY(50);
            signUp.setTranslationY(40);

            int v = 0;
            signIn.setAlpha(v);
            signUp.setAlpha(v);

            signUp.setVisibility(View.VISIBLE);
            signIn.setVisibility(View.VISIBLE);

            textView.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(200).start();
            signUp.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(1000).start();
            signIn.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(2000).start();

            signIn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    signIn.setEnabled(true);
                    if(checkInternetConnection()){
                        Intent signIntent = new Intent(MainActivity.this,SignInActivity.class);
                        startActivity(signIntent);
                        if(cardActTrue){
                            finish();
                        }

                    }

                }
            });

            signUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    signUp.setEnabled(true);
                    if(checkInternetConnection()){
                        Intent signUpIntent = new Intent(MainActivity.this,SignUpActivity.class);
                        startActivity(signUpIntent);
                        if(cardActTrue){
                            finish();
                        }
                    }
                }
            });
        }
    }

    private boolean checkInternetConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        if(!isConnected){
            changeActivity();
        }
        return isConnected;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //register intent filter
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);

        ConnectivityReceiver connectivityReceiver = new ConnectivityReceiver();
        registerReceiver(connectivityReceiver,intentFilter);

        MyApp.getInstance().setConnectivityListener(this);
    }


    //Add an Emoji func.
    public String getEmoji(int unicode){
        return new String(Character.toChars(unicode));

    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if(!isConnected){
            changeActivity();
            signIn.setEnabled(false);
            signUp.setEnabled(false);
        }
        else{
            signIn.setEnabled(true);
            signUp.setEnabled(true);
        }
    }

    private void changeActivity() {
        Toast.makeText(this, "İnternet bağlantını kontrol et!", Toast.LENGTH_SHORT).show();
    }
}