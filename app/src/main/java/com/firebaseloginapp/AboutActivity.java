package com.firebaseloginapp;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class AboutActivity extends AppCompatActivity {
ImageButton twana,sava,gwllan,bryar,yasin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();


        twana=findViewById(R.id.twanaInfo);
        twana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(AboutActivity.this,"Name: Twana Nariman Qadir \n student in UHD IT Department,\n Level 3",Toast.LENGTH_LONG).show();
            }
        });

        sava=findViewById(R.id.savaInfo);
        sava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AboutActivity.this,"Name: Sava Ali \n student in UHD IT Department,\n Level 3",Toast.LENGTH_LONG).show();
            }
        });

        yasin=findViewById(R.id.yasinInfo);
        yasin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AboutActivity.this,"Name: Yasin Lwqman \n student in UHD IT Department,\n Level 3",Toast.LENGTH_LONG).show();
            }
        });


        gwllan=findViewById(R.id.gwllanInfo);
        gwllan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AboutActivity.this,"Name: Gwllan Kawa \n student in UHD IT Department,\n Level 3",Toast.LENGTH_LONG).show();
            }
        });

        bryar=findViewById(R.id.bryarInfo);
        bryar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AboutActivity.this,"Name: Bryar Taha \n student in UHD IT Department,\n Level 3",Toast.LENGTH_LONG).show();
            }
        });



    }
}
