package com.plastic.plastico;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import java.lang.Object;
import android.provider.MediaStore;
import android.content.Intent;
import android.graphics.Bitmap;
import android.widget.ImageView;

public class getbarcode extends AppCompatActivity {
    private ImageView thumbnail;
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
        }
    }
    private void checkcode(Bitmap scannedimg)
    {
       // FirebaseVisionImage qrcode = FirebaseVisionImage.fromBitmap(scannedimg);

    }



    }
