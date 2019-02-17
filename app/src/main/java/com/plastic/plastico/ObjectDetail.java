package com.plastic.plastico;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.vision.barcode.Barcode;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Random;


public class ObjectDetail extends AppCompatActivity {
    private DatabaseReference myRef;
    private FirebaseDatabase mFirebaseDatabase;
    private final  String input_id =  "07084781119"; // TO BE DELETED
    private ObjectInformation objectInformation;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FirebaseApp.initializeApp(this);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        if(getIntent().hasExtra("QRCode")) {

            final String QRcode = getIntent().getExtras().getString("QRCode");

                    myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    showInfo(dataSnapshot,QRcode);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }




    }

    private final double conversion = 6.15384;
    public void showInfo(DataSnapshot dataSnapshot,String qr){
            for(DataSnapshot ds : dataSnapshot.getChildren()){

            Log.d("title______", ds.getKey());
            if(ds.getKey().equals("objects")) {

                objectInformation = new ObjectInformation();
                // name input
                objectInformation.setName(ds.child(qr).getValue(ObjectInformation.class).getName());
                TextView fieldName = (TextView) findViewById(R.id.obName);
                fieldName.setText(objectInformation.getName());

                // id input
                objectInformation.setId(ds.child(qr).getValue(ObjectInformation.class).getId());
                TextView fieldId = (TextView) findViewById(R.id.ID);
                fieldId.setText("id: " + objectInformation.getId());

                // type input
                objectInformation.setType(ds.child(qr).getValue(ObjectInformation.class).getType());
                TextView fieldType = (TextView) findViewById(R.id.obType);
                fieldType.setText("Type: " + objectInformation.getType());

                // pollution input
                objectInformation.setWeight(ds.child(qr).getValue(ObjectInformation.class).getWeight());
                TextView fieldPollution = (TextView) findViewById(R.id.obPollution);
                fieldPollution.setText("Pollution(g): " + String.valueOf(objectInformation.getWeight() * conversion) + " CO2");
            }
        }

    }

    public void cancelAction(View view){
        Intent intent = new Intent(ObjectDetail.this, MainActivity.class);
        startActivity(intent);
    }


    public void retakeAction(View view){
        Intent intent = new Intent(ObjectDetail.this, getbarcode.class);
        startActivity(intent);
    }

    public void confirmBtn(View view){
        mAuth= FirebaseAuth.getInstance();
        FirebaseUser user= mAuth.getCurrentUser();
        Log.d("myapp",user.getUid());
        final DatabaseReference database_instance = FirebaseDatabase.getInstance().getReference();
        Random rand = new Random(System.currentTimeMillis());
        int key = rand.nextInt();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        database_instance.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(String.valueOf(key)).setValue(0);
        database_instance.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(String.valueOf(key)).setValue(0);
        database_instance.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(String.valueOf(key)).child("name").setValue(objectInformation.getName());
        database_instance.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(String.valueOf(key)).child("id").setValue(objectInformation.getId());
        database_instance.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(String.valueOf(key)).child("datetime").setValue(dateFormat.format(date));
        database_instance.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(String.valueOf(key)).child("weight").setValue(objectInformation.getWeight());

        try{
            Thread.sleep(500);
        }catch(Exception e){}

        Intent intent = new Intent(ObjectDetail.this, MainActivity.class);
        startActivity(intent);

    }


}
