package com.jsstech.uploadimagefromgallery;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.DexterBuilder;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    ImageView image;
    FloatingActionButton febBt;

    //Git lib for gallery
    // implementation 'com.karumi:dexter:6.0.2'
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        image = findViewById(R.id.img);
        febBt = findViewById(R.id.feb);

        febBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dexter.withActivity(MainActivity.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)

                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                              Intent intent=new Intent(Intent.ACTION_PICK);
                              intent.setType("image/*");
                              startActivityForResult(Intent.createChooser(intent,"Please select image"),1);

                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission,PermissionToken token) {
                            token.cancelPermissionRequest();
                            }
                        }).check();


//                ImagePicker.Companion.with(MainActivity.this)

//                        // .crop()	    			//Crop image(Optional), Check Customization for more option
////                        .cropOval()	    		//Allow dimmed layer to have a circle inside
////                        .cropFreeStyle()	    //Let the user to resize crop bounds
////                        .galleryOnly()          //We have to define what image provider we want to use
////                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
//
//                        .start();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,@Nullable Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == 1 && resultCode==RESULT_OK)
        {
            Uri imageUri = data.getData();
            try
            {
                InputStream inputStream=getContentResolver().openInputStream(imageUri);
                Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
                image.setImageBitmap(bitmap);

            }catch (Exception e){

            }

        }
    }
}