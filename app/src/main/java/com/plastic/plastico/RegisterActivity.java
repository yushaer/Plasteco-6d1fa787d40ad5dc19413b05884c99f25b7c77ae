package com.plastic.plastico;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private EditText password_confirm;
    private Button register;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        email= findViewById(R.id.email);
        password = findViewById(R.id.password);
        password_confirm= findViewById(R.id.password_confirm);
        register= findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String em = email.getText().toString();
                String psw = password.getText().toString();
                String psw_confirm = password_confirm.getText().toString();
                if(!TextUtils.isEmpty(em)&& !TextUtils.isEmpty(psw) && !TextUtils.isEmpty(psw_confirm)){
                    if(psw.equals(psw_confirm)){
                        Toast.makeText(RegisterActivity.this, "Yes it works",
                                Toast.LENGTH_SHORT).show();
                        register(em,psw);
                    }

                }
                else{
                    Toast.makeText(RegisterActivity.this, "please fill in your email and password",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    public void register(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = mAuth.getCurrentUser();
                           user.sendEmailVerification();
                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }

}
