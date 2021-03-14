package com.okellomwaka.eazysacco;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceControl;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

public class Transactions_Activity extends AppCompatActivity {
    //declare sections pager adapter
    private SectionsPagerAdapter sectionsPagerAdapter;
    //view pager
    private ViewPager viewPager;

    private TextView bal,account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions_);
        Intent i = getIntent();
        String acc = i.getStringExtra("account");
        String amount = i.getStringExtra("amount");
        TabLayout tabLayout = findViewById(R.id.tabLayout);

        //find toolbar
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    //    setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
       sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(),
               Transactions_Activity.this,acc,tabLayout.getTabCount());
        // Set up the ViewPager with the sections adapter.
        viewPager =  findViewById(R.id.viewpager);
        bal = findViewById(R.id.AccountBalance);
        account = findViewById(R.id.accname_balance);
        if(acc != null || amount!=null) {
            account.setText(acc);
            bal.setText(amount);
        }
        viewPager.setAdapter(sectionsPagerAdapter);
        //find the tablayout and set viewpager to it

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