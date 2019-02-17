package com.plastic.plastico;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;

import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.io.IOException;
import java.io.File;
import java.net.URI;
import java.util.Date;
import java.lang.Object;
import android.provider.MediaStore;
import android.content.Intent;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.TextView;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

//Firebase Imports
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetector;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions;
import com.google.firebase.ml.vision.*;
import android.content.Context;
import static android.content.Context.CAMERA_SERVICE;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata;
//import com.google.firebase.ml.vision.common.FirebaseVisionBarcodeDetector;

// 0/
import java.text.SimpleDateFormat;
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
    private Uri qruri;
    public void TakePicture(View view) {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null)
        {
            File qrFile = null;
            try{
                qrFile = createQRimage();
            }
            catch(IOException e)
            {
                System.out.println("Cannot create image file");
            }
            if (qrFile!= null)
            {
                qruri = FileProvider.getUriForFile(this,"com.plastic.plastico",qrFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, qruri);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }
    //FUnction to get image directory
    String lastqrpath;
    private File createQRimage() throws IOException{
        String time = new SimpleDateFormat("yyyyMMdd__HHmmss").format(new  Date());
        String imageFileName = "QRPIX_"+ time;
        File directory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image =  File.createTempFile(imageFileName,".jpg",directory);
        lastqrpath =  image.getAbsolutePath();
        return image;
    }
    //Onactivityresult
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            //Bundle extras = data.getExtras();
            //Bitmap imageBitmap = (Bitmap) extras.get("data");
            //thumbnail = findViewById(R.id.ScanThumb);
            //thumbnail.setImageBitmap(imageBitmap);
            this.checkcode(getApplicationContext());
        }
    }
    //Function to check the barcode
    private void checkcode(Context context)
    {
        qrcodedisplay = findViewById(R.id.qrcodenum);

        //Get image
        //FirebaseVisionImage qrcode = FirebaseVisionImage.fromBitmap(scannedimg);
        FirebaseVisionImage image;
        try {
            image = FirebaseVisionImage.fromFilePath(context, qruri);

        FirebaseVisionBarcodeDetector detector = FirebaseVision.getInstance().getVisionBarcodeDetector();

        qrcodedisplay.setText("OOOF");
        //process image
        Task<List<FirebaseVisionBarcode>> result = detector.detectInImage(image)
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
                });}
        catch (IOException e) {
        e.printStackTrace();
    }
    }



}
