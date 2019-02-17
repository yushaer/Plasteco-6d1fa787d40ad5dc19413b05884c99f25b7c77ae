package com.plastic.plastico;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.View;
import java.lang.Object;
import android.provider.MediaStore;
import android.content.Intent;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

//Firebase Imports
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetector;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions;
import com.google.firebase.ml.vision.*;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata;
//import com.google.firebase.ml.vision.common.FirebaseVisionBarcodeDetector;

// 0/
import java.util.List;


public class getbarcode extends AppCompatActivity {
    private ImageView thumbnail;
    private TextView qrcodedisplay;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_barcode);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
    //take a picture
    public void TakePicture(View view) {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null)
        {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            thumbnail = findViewById(R.id.ScanThumb);
            thumbnail.setImageBitmap(imageBitmap);
            this.checkcode(imageBitmap);
            //echo: Delete Code
        }
    }
    private void checkcode(Bitmap scannedimg)
    {
        qrcodedisplay = findViewById(R.id.qrcodenum);
        FirebaseVisionImage qrcode = FirebaseVisionImage.fromBitmap(scannedimg);
        FirebaseVisionBarcodeDetector detector = FirebaseVision.getInstance().getVisionBarcodeDetector();
        qrcodedisplay.setText("OOOF");
        Task<List<FirebaseVisionBarcode>> result = detector.detectInImage(qrcode)
                .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionBarcode>>() {
                    @Override
                    public void onSuccess(List<FirebaseVisionBarcode> barcodes) {
                        if(barcodes.size()!=0)
                        {
                            FirebaseVisionBarcode qrcode = barcodes.get(0);
                            String temp  = qrcode.getRawValue();
                            qrcodedisplay.setText(temp);

                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        qrcodedisplay.setText("OOOF");
                    }
                });
    }



    }
