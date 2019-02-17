package com.plastic.plastico;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.Firebase;

public class NoBarcode extends AppCompatActivity {

    private EditText nBarcode;
    private Button nSendData;
    private TextView nKeyValue;


    private Firebase nRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_barcode);


        nRef = new Firebase("https://plasteco-3a8bc.firebaseio.com/objects");
        nBarcode = (EditText) findViewById(R.id.barcode);
        nSendData = (Button) findViewById(R.id.sendData);
        nKeyValue = (TextView) findViewById(R.id.keyValue);
        nSendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String barcode = nBarcode.getText().toString();
                String key = nKeyValue.getText().toString();
                Firebase nRefChild = nRef.child(key);
                nRef.push().setValue(barcode);
            }
        });
    }
}
