package com.plastic.plastico;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Date;

public class LogInRealActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText email;
    private EditText password;
    private Button submit;
    private Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_real);
        email= findViewById(R.id.email);
        password= findViewById(R.id.password);

        submit= findViewById(R.id.login);
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String em = email.getText().toString();
                String psw = password.getText().toString();
                if(!TextUtils.isEmpty(em)&& !TextUtils.isEmpty(psw)){
                    login(em,psw);

                }
                else{
                    Toast.makeText(LogInRealActivity.this, "please fill in your email and password",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
        ///
    }
    public void succes(){
        Intent test = new Intent(this,MainActivity.class);
        startActivity(test);
    }
    public void login(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            date = new Date();

                            FirebaseUser user = mAuth.getCurrentUser();
                            if(user.isEmailVerified()) {
                                Toast.makeText(LogInRealActivity.this, date.toString(),
                                        Toast.LENGTH_SHORT).show();
                                 user.getUid();
                                 succes();
                            }
                            else{
                                Toast.makeText(LogInRealActivity.this, "Please verify your email",
                                        Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(LogInRealActivity.this, task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }

                });

    }

    public void register(View view) {
        Intent reg = new Intent(this,RegisterActivity.class);
        startActivity(reg);
    }


}