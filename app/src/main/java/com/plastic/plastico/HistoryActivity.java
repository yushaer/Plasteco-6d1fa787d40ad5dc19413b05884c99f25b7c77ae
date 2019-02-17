package com.plastic.plastico;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public class HistoryActivity extends AppCompatActivity {
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FirebaseApp.initializeApp(this);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();


               myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                showInfo(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private FirebaseAuth mAuth;
    public void showInfo(DataSnapshot dataSnapshot){
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user= mAuth.getCurrentUser();
        Log.d("myapp",user.getUid());
        final DatabaseReference database_instance = FirebaseDatabase.getInstance().getReference();

        for(DataSnapshot ds: dataSnapshot.getChildren()){
            Log.d("Key:", ds.getKey());
            if(ds.getKey().equals("Users")){
                for(DataSnapshot usersSnapshot: ds.getChildren()){
                    Log.d("Key_snap:", usersSnapshot.getKey());
                    Log.d("Key_user:", user.getUid());
                    if(usersSnapshot.getKey().equals(user.getUid())){
                        for(DataSnapshot userSnapshot: usersSnapshot.getChildren()){
                            Log.d("User:", userSnapshot.getKey() + " + " + userSnapshot.getValue());
                            Log.d("datetime:", userSnapshot.child("datetime").getValue().toString());
                            User _user = new User();

                            _user.setDatetime(userSnapshot.child("datetime").getValue().toString());
                            _user.setId(userSnapshot.child("id").getValue().toString());
                            _user.setName(userSnapshot.child("name").getValue().toString());
                            _user.setWeight(userSnapshot.child("weight").getValue().toString());








                        }
                    }
                }
            }
        }
    }


}
