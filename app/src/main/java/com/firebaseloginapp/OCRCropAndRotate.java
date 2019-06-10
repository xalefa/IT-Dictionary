package com.firebaseloginapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.theartofdev.edmodo.cropper.CropImageView;


public class OCRCropAndRotate extends AppCompatActivity implements View.OnClickListener, Toolbar.OnMenuItemClickListener{
    private Toolbar toolbar;
    private FloatingActionButton mFab;
    public static Bitmap croppedImage;
    private String message;
    CropImageView cropImageView;

    ImageButton btnRL,btnRR;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ocrcrop_and_rotate);

        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();

        btnRL=findViewById(R.id.btn_rotate_left);
        btnRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cropImageView.rotateImage(-90);

            }
        });
        btnRR=findViewById(R.id.btn_rotate_right);
        btnRR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cropImageView.rotateImage(90);
            }
        });
       // toolbar = (Toolbar) findViewById(R.id.toolbar);
         //  setSupportActionBar(toolbar);
       // ViewCompat.setElevation(toolbar,10);
       //toolbar.setOnMenuItemClickListener(this);
        Intent intent = getIntent();
        message = intent.getStringExtra(OCRMain.EXTRA_MESSAGE);
        cropImageView = (CropImageView) findViewById(R.id.cropImageViewRo);

        cropImageView.setImageUriAsync(Uri.parse(message));
        mFab = (FloatingActionButton) findViewById(R.id.nextStep);
        mFab.setOnClickListener(this);

    }



    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.nextStep){
            cropImageView.setOnCropImageCompleteListener(new CropImageView.OnCropImageCompleteListener() {
                @Override
                public void onCropImageComplete(CropImageView view, CropImageView.CropResult result) {
                    croppedImage = result.getBitmap();
                    Intent intent = new Intent(OCRCropAndRotate.this, OCRBinarization.class);
                    startActivity(intent);
                }
            });
            cropImageView.getCroppedImageAsync();
        }


    }

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rotate, menu);
        return super.onCreateOptionsMenu(menu);
    }*/


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btn_rotate_left:
                cropImageView.rotateImage(-90);
                break;
            case R.id.btn_rotate_right:
                cropImageView.rotateImage(90);
                break;
        }
        return false;
    }

}
