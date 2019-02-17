package com.plastic.plastico;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.solver.widgets.Snapshot;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private int weight = 0;
    private TextView nDisplayWeight;
    private TextView nDisplayPCF;
    private final double conversion = 6.15384;

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
                if (snapshot.hasChild("Users")) {
                    if(snapshot.child("Users").hasChild(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                        Log.d("myapp11","Exsists");
                    }
                    else{
                        Log.d("myapp12","doesn't exists");
                        database_instance.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(0);
                    }
                    weight = getWeight(snapshot);
                    Log.d("weight: " , String.valueOf(weight));
                    nDisplayWeight = (TextView) findViewById(R.id.displayWeight);
                    nDisplayWeight.setText("Weight: " + String.valueOf(weight) +"g");
                    nDisplayPCF = (TextView) findViewById(R.id.displayPCF);
                    nDisplayPCF.setText("Total Carbon Emissions: " + String.valueOf(weight*conversion)+"g");
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

    public void history(View view)
    {
        Intent intent = new Intent(MainActivity.this,HistoryActivity.class);
        startActivity(intent);
    }


    public int getWeight(DataSnapshot dataSnapshot){
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user= mAuth.getCurrentUser();
        Log.d("myapp",user.getUid());
        final DatabaseReference database_instance = FirebaseDatabase.getInstance().getReference();
        int weight = 0;
        for(DataSnapshot ds: dataSnapshot.getChildren()){
            Log.d("Key:", ds.getKey());
            if(ds.getKey().equals("Users")){
                for(DataSnapshot usersSnapshot: ds.getChildren()){
                    Log.d("Key_snap:", usersSnapshot.getKey());
                    Log.d("Key_user:", user.getUid());
                    if(usersSnapshot.getKey().equals(user.getUid())){
                        for(DataSnapshot userSnapshot: usersSnapshot.getChildren()){
                            User _user = new User();
                            _user.setWeight(userSnapshot.child("weight").getValue().toString());
                            weight += Integer.parseInt(_user.getWeight());

                        }
                    }
                }
            }
        }
        return weight;
    }

}
