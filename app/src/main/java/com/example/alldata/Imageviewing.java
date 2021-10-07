package com.example.alldata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;

import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import android.util.Log;
import android.view.View;

import android.widget.Toast;

import com.example.alldata.ZoomableImageView.ZoomableImageView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


public class Imageviewing extends AppCompatActivity {
    private FloatingActionButton fab;
    public static final int externalstoragecode=69;
    OutputStream outputStream;
    ZoomableImageView touch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageviewing);
        fab = findViewById(R.id.fab);
        touch = (ZoomableImageView)findViewById(R.id.IMAGEID);

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();
        
        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if (type.startsWith("image/")) {
                fab.setVisibility(View.VISIBLE);
                handleSendImage(intent); // Handle single image being sent

            }
        } else if (Intent.ACTION_SEND_MULTIPLE.equals(action) && type != null) {
            if (type.startsWith("image/")) {
                //handleSendMultipleImages(intent);
                Toast.makeText(this, "work in progress for multiple images", Toast.LENGTH_SHORT).show();
            }
        } else {
            fab.setVisibility(View.INVISIBLE);
            inflateoptionmenu();
            Bundle bundle = getIntent().getExtras();
            String imageuri = bundle.getString("Bitmap");
            Bitmap bitmap = BitmapFactory.decodeFile(imageuri);
            touch.setImageBitmap(bitmap);
        }

    }


    private void inflateoptionmenu() {

    }

    private void handleSendImage(Intent intent) {
        Uri imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
        if (imageUri != null) {
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                touch.setImageBitmap(bitmap);
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(ContextCompat.checkSelfPermission(Imageviewing.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
                            savetoexternalstorage(bitmap);
                            Intent in = new Intent(Imageviewing.this,MainActivity.class);
                            startActivity(in);
                            finish();
                        }else{
                            demandpermission();
                        }

                        //save photo in extarnal memory bye i am going to play BGMI
                    }
                });
            }catch (Exception e){
                Toast.makeText(this, "kuch to gadbad hai daya", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void demandpermission() {
        ActivityCompat.requestPermissions(Imageviewing.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},externalstoragecode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,@NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==externalstoragecode){
            if(grantResults.length >0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "hun dobara save button dab ", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Permission nahi milegi ko kaise chalega", Toast.LENGTH_SHORT).show();
            }

        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void savetoexternalstorage(Bitmap bitmap) {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("MSJR_Photos", Context.MODE_PRIVATE);
        String fname = System.currentTimeMillis() +".jpg";
        File mypath=new File(directory,fname);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "kuch to gadbad hai daya", Toast.LENGTH_SHORT).show();
            }
        }

    }

}