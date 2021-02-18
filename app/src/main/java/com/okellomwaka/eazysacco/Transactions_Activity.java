package com.okellomwaka.eazysacco;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

public class Transactions_Activity extends AppCompatActivity {
    //declare sections pager adapter
   // private SectionsPagerAdapter sectionsPagerAdapter;
    //view pager
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions_);
        //find toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.

   //     sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        viewPager = (ViewPager) findViewById(R.id.viewpager);
  //      viewPager.setAdapter(sectionsPagerAdapter);

        //find the tablayout and set viewpager to it
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.bottom_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //declare a variable to store the menu ids
        int id = item.getItemId();
        //if to switch
/*
        if (id == R.id.nav_fragmentA){
            Toast.makeText(this, "Fragment Clicked", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_web){
            //Intent intent = new Intent(TabLayoutActivity.this, WebActivity.class);
           // startActivity(intent);
            Toast.makeText(this, "Fragment Clicked", Toast.LENGTH_SHORT).show();
        }
       

 */
        return super.onOptionsItemSelected(item);
    }

}