package com.firebaseloginapp.AccountActivity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import com.firebaseloginapp.R;

import java.text.DateFormat;
import java.util.Date;

public class WelcomeSecreen extends AppCompatActivity {
    private final int SPLASH_DISPLY_LENGTH=3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_secreen);


        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntActi=new Intent(WelcomeSecreen.this,LoginActivity.class);
                WelcomeSecreen.this.startActivity(mainIntActi);
                WelcomeSecreen.this.finish();

            }
        },SPLASH_DISPLY_LENGTH);
    }
}
