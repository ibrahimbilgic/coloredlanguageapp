package com.coloredlanguageapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

// 22.11.20

public class SplashActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener{
    private Button signIn;
    private Button signUp;
    private TextView textView;
    private SignInButton signInButton;
    private GoogleSignInClient googleSignInClient;
    private FirebaseAuth firebaseAuth;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


       checkInternetConnection(); //Check Connection

        if(PreferenceUtils.getEmail(this)!=null && PreferenceUtils.getEmail(this)!=""){
            Intent MainIntent = new Intent(SplashActivity.this,MainActivity.class);
            startActivity(MainIntent);
            finish();
        }
        else{
            // if user's gmail doesn't exist, then..
            signIn = findViewById(R.id.signIn);
            signUp = findViewById(R.id.signUp);
            textView = findViewById(R.id.textView);
            signInButton = findViewById(R.id.btn_signInGoogle); // sign in with google button


            int unicode = 0x1F44B;
            String emoji = getEmoji(unicode);

            textView.setText("Hoş geldin "+emoji);

            textView.setTranslationY(100);
            signIn.setTranslationY(50);
            signUp.setTranslationY(40);
            signInButton.setTranslationY(30);

            int v = 0;
            signIn.setAlpha(v);
            signUp.setAlpha(v);
            signInButton.setAlpha(v);

            signUp.setVisibility(View.VISIBLE);
            signIn.setVisibility(View.VISIBLE);
            signInButton.setVisibility(View.VISIBLE);

            textView.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(200).start();
            signUp.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(1000).start();
            signIn.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(2000).start();

            signInButton.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(2500).start();

            /*
            /// Sign in With Google
            GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(
                    GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();

            googleSignInClient = GoogleSignIn.getClient(SplashActivity.this,googleSignInOptions);

            signInButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = googleSignInClient.getSignInIntent();
                    startActivityForResult(intent,100);
                }
            });

            firebaseAuth = FirebaseAuth.getInstance();
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

            if(firebaseUser != null){
                startActivity(new Intent(SplashActivity.this,MainActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }*/


            signIn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    signIn.setEnabled(true);
                    if(checkInternetConnection()){
                        Intent signIntent = new Intent(SplashActivity.this,SignInActivity.class);
                        startActivity(signIntent);
                    }
                }
            });

            signUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    signUp.setEnabled(true);
                    if(checkInternetConnection()){
                        Intent signUpIntent = new Intent(SplashActivity.this,SignUpActivity.class);
                        startActivity(signUpIntent);
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

    ///// GoogleSignIn
    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100){

            Task<GoogleSignInAccount> googleSignInAccountTask = GoogleSignIn
                    .getSignedInAccountFromIntent(data);

            if(googleSignInAccountTask.isSuccessful()){
                Toast.makeText(getApplicationContext(),"Succesfull",Toast.LENGTH_LONG).show();

                try {
                    GoogleSignInAccount googleSignInAccount = googleSignInAccountTask
                            .getResult(ApiException.class);

                    if(googleSignInAccount != null){
                        AuthCredential authCredential = GoogleAuthProvider
                                .getCredential(googleSignInAccount.getIdToken(),null); ///kontrol et

                        firebaseAuth.signInWithCredential(authCredential)
                                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if(task.isSuccessful()){
                                            startActivity(new Intent(SplashActivity.this,MainActivity.class)
                                                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                            Toast.makeText(SplashActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                        }
                                        else{
                                            Toast.makeText(SplashActivity.this,"Failed",Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                    }
                } catch (ApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }*/
}