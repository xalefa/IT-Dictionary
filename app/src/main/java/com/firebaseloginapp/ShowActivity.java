package com.firebaseloginapp;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.firebaseloginapp.AccountActivity.MainActivity;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;


public class ShowActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    MenuItem menuSetting;
    Toolbar toolbar;
    DBHelper dbHelper;

    String setText;

DictionaryFragment dictionaryFragment;
BookmarkFragment bookmarkFragment;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
         toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        dbHelper=new DBHelper(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        dictionaryFragment=new DictionaryFragment();
        bookmarkFragment=BookmarkFragment.getNewInstance(dbHelper);
        goToFragment(dictionaryFragment,true);

        dictionaryFragment.setOnFragmnetListener(new FragmentListener(){
            @Override
            public void onItemClick(String value){
             //   Toast.makeText(ShowActivity.this,value,Toast.LENGTH_SHORT).show();
                String id= GlobalSave.getState(ShowActivity.this,"dic_type");
                int dicType= id ==null? R.id.action_en_ku:Integer.valueOf(id);
            goToFragment(DetailFragment.getNewInstance(value,dbHelper,dicType),false);
            }
        });

        bookmarkFragment.setOnFragmnetListener(new FragmentListener() {
            @Override
            public void onItemClick(String value) {
             //   Toast.makeText(ShowActivity.this,value,Toast.LENGTH_SHORT).show();
                String id= GlobalSave.getState(ShowActivity.this,"dic_type");
                int dicType= id ==null? R.id.action_en_ku:Integer.valueOf(id);
                goToFragment(DetailFragment.getNewInstance(value,dbHelper,dicType),false);
            }
        });
////////////////////////////////////
        final EditText edit_search=findViewById(R.id.edit_search);

        Intent intent = getIntent();
        setText=  intent.getStringExtra("getTextVoice");
        //edit_search.setText("");
        edit_search.setText(setText);

        edit_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                dictionaryFragment.filterValue(s.toString().toLowerCase());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


/////////////////////////////////////
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.show, menu);
        menuSetting=menu.findItem(R.id.action_settings);

        //svae
       String id= GlobalSave.getState(this,"dic_type");
       if(id != null) {
           onOptionsItemSelected(menu.findItem(Integer.valueOf(id)));
       }else {
           ArrayList<String> source=dbHelper.getWord(R.id.action_en_ku);
          dictionaryFragment.resetDataSource(source);
       }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(R.id.action_settings == id) return true;
        GlobalSave.saveState(this,"dic_type",String.valueOf(id));
        ArrayList<String> source=dbHelper.getWord(id);

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_en_ku) {
            dictionaryFragment.resetDataSource(source);
        menuSetting.setIcon(getDrawable(R.drawable.en_ku));
        }else if(id == R.id.action_ku_en){
            dictionaryFragment.resetDataSource(source);
            menuSetting.setIcon(getDrawable(R.drawable.ku_en));
        }else if(id == R.id.action_en_en){
            dictionaryFragment.resetDataSource(source);
            menuSetting.setIcon(getDrawable(R.drawable.en_en));
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_bookmakr) {
       goToFragment(bookmarkFragment, false);

        }else if(id==R.id.nav_account){
            Intent goAcountActi = new Intent(this, MainActivity.class);
            startActivity(goAcountActi);
        }else if(id==R.id.nav_rate){
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=com.kurdappdev.kurdkey")));
                }catch (ActivityNotFoundException ex){
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://play.google.com/store/apps/details?id=com.kurdappdev.kurdkey")));
                }
        }else if(id ==R.id.nav_share){
            Intent myIntentShare=new Intent(Intent.ACTION_SEND);
            myIntentShare.setType("text/plain");
            String shareBoday="IT Dictionary app for android translate EN-KU ,KU-EN, and EN-EN \n https://play.google.com/store/apps/details?id=com.kurdappdev.kurdkey";
            String shareSub="IT Dictionary ";
            myIntentShare.putExtra(Intent.EXTRA_SUBJECT,shareBoday);
            myIntentShare.putExtra(Intent.EXTRA_TEXT,shareSub);
            startActivity(Intent.createChooser(myIntentShare,"Share using"));
        }else if(id==R.id.nav_help){
            Intent goAcountActi = new Intent(this, HelpActicity.class);
            startActivity(goAcountActi);

        }else if (id==R.id.nav_info){
            Intent goAcountActi = new Intent(this, AboutActivity.class);
            startActivity(goAcountActi);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    void goToFragment(Fragment fragment,boolean isTop){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.fragment_container, fragment);
        if(!isTop) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }
}
