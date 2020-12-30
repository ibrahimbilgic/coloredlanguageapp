package com.coloredlanguageapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SignUpActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener{
    private EditText userName,email,password;
    private Button signUp;
    private FirebaseAuth firebaseAuth;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        userName = findViewById(R.id.userName);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        signUp = findViewById(R.id.signup);

        checkInternetConnection();

        firebaseAuth = FirebaseAuth.getInstance();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp.setEnabled(true);
                if(!userName.getText().toString().isEmpty()){
                    if(!email.getText().toString().isEmpty()){
                        if(!password.getText().toString().isEmpty()){
                            if(password.getText().toString().length()>=6){
                                if(isValidEmail(email.getText().toString())){
                                    createAccount(userName.getText().toString(),email.getText().toString(),password.getText().toString());
                                }
                                else{
                                    email.setError("Geçersiz Email düzeni");
                                }
                            }
                            else{
                                password.setError("Şifren 6 haneden büyük olmalı!");
                            }

                        }
                        else{
                            password.setError("Şifre girmelisin!");
                        }

                    }
                    else{
                        email.setError("Email adresini boş bırakamazsın!");
                    }
                }
                else{
                    userName.setError("Sana nasıl hitap edeceğim?");
                }
            }
        });
    }

    //Email check
    public static boolean isValidEmail(String textFieldEmail){
        final Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+" +
                        "(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)" +
                        "*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\" +
                        "[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@" +
                        "(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+" +
                        "[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4]" +
                        "[0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|" +
                        "[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\" +
                        "[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");

        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(textFieldEmail);
        if(matcher.matches()){
            return true;
        }
        else{
            return false;
        }

    }

    //Create account - new User
    public void createAccount(String userName, String email, String password){
        //Create New User
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task){
                        if(task.isSuccessful()){
                            saveUser();
                            PreferenceUtils.saveEmail(email,SignUpActivity.this);
                            PreferenceUtils.saveName(userName,SignUpActivity.this);
                            Intent MainIntent = new Intent(SignUpActivity.this,MainActivity.class);
                            MainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(MainIntent);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if(e instanceof FirebaseAuthUserCollisionException){
                    Toast.makeText(SignUpActivity.this, "Bu Email Kullanılıyor. Lütfen Giriş Yap!", Toast.LENGTH_SHORT).show();
                }

            }
        });
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
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if(!isConnected){
            changeActivity();
            signUp.setEnabled(false);
        }
        else{
            signUp.setEnabled(true);
        }
    }
    private void changeActivity() {
        Toast.makeText(this, "İnternet bağlantını kontrol et!", Toast.LENGTH_SHORT).show();
    }
    private void saveUser(){
        Map<String,String> yeniUser = new HashMap<>();
        yeniUser.put("email",email.getText().toString());
        yeniUser.put("name",userName.getText().toString());

        myRef.child("Users")
                .child(firebaseAuth.getCurrentUser().getUid())
                .setValue(yeniUser);

    }

}

