package com.plastic.plastico;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mAuth= FirebaseAuth.getInstance();
        FirebaseUser user= mAuth.getCurrentUser();
        Log.d("myapp",user.getUid());
        final DatabaseReference database_instance = FirebaseDatabase.getInstance().getReference();
        database_instance.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.hasChild(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                    // run some code
                }
                else{
                    Log.d("myapp","doesnt exit");
                    database_instance.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(0);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void login(View view){
        Intent test = new Intent(this,LogInRealActivity.class);
        startActivity(test);
    }

    public void barcode(View view) {
        Intent test = new Intent(this,NoBarcode.class);
        startActivity(test);
    }

    public void bar(View view)
        {
                Intent qrscan = new Intent(MainActivity.this,getbarcode.class);
                startActivity(qrscan);
        }
    public void objectDetail(View view)
    {
        Intent intent = new Intent(MainActivity.this,ObjectDetail.class);
        startActivity(intent);
    }
}
