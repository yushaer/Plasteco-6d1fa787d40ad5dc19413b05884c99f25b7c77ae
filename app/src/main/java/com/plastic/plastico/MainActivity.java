package com.plastic.plastico;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.content.Intent;

import com.google.firebase.FirebaseApp;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent b = new Intent(MainActivity.this,NoBarcode.class);
        startActivity(b);

    }

    public void login(View view){
        Intent test = new Intent(this,LogInRealActivity.class);
        startActivity(test);
    }

    public void noBarcode(View view){
        Intent b = new Intent(MainActivity.this,noObjectBarcode.class);
        startActivity(b);
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
