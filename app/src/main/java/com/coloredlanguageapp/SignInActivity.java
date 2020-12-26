package com.coloredlanguageapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.preference.Preference;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class SignInActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener{
    private EditText email,password;
    private TextView forgotPassword;
    private Button signIn;

    private FirebaseAuth firebaseAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        forgotPassword = findViewById(R.id.forgotPassword);

        firebaseAuth = FirebaseAuth.getInstance();

        checkInternetConnection();

        signIn = findViewById(R.id.signin);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn.setEnabled(true);
                if(!email.getText().toString().isEmpty()){
                    if(!password.getText().toString().isEmpty()){
                        firebaseAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(firebaseAuth.getCurrentUser()!=null){
                                    saveName();
                                    PreferenceUtils.saveEmail(email.getText().toString(),SignInActivity.this);
                                }
                                else{
                                    if(task.getException() instanceof FirebaseAuthInvalidCredentialsException){
                                        Toast.makeText(SignInActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                    else if(task.getException() instanceof FirebaseAuthInvalidUserException){
                                        Toast.makeText(SignInActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                            }
                        });
                    }
                    else{
                        email.setError("Şifre Girmelisin");
                    }
                }
                else{
                    email.setError("Email alanını boş geçemezsin");
                }
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText resetMail = new EditText(v.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Şifreni sıfırlamak istiyor musun?");
                passwordResetDialog.setMessage("Şifre sıfırlama işlemleri için Email adresini gir");
                passwordResetDialog.setView(resetMail);

                passwordResetDialog.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //extract the emial and send reset link
                        String mail = resetMail.getText().toString();
                        firebaseAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(SignInActivity.this, "Mail Adresini Kontrol Et!", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SignInActivity.this, "Link Gönderilemedi!"+ e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                passwordResetDialog.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //close the dialog
                    }
                });
                passwordResetDialog.create().show();
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
            signIn.setEnabled(false);
        }
        else{
            signIn.setEnabled(true);
        }
    }
    private void changeActivity() {
        Toast.makeText(this, "İnternet bağlantını kontrol et!", Toast.LENGTH_SHORT).show();
    }

    private void saveName(){
        myRef.child("Users")
                .child(firebaseAuth.getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String name = snapshot.child("name").getValue().toString();
                        PreferenceUtils.saveName(name,SignInActivity.this);
                        Intent cardInt = new Intent(SignInActivity.this,CardActivity.class);
                        startActivity(cardInt);
                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        //
                    }
                });
    }
}