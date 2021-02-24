package com.okellomwaka.eazysacco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class NavigationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

    }

    //Loading Create New Sacco
    public  void  load_Add_Sacco( View v){
        Intent intent = new Intent(NavigationActivity.this, CreateNewSaccoActivity.class);
          startActivity(intent);
    }

    //Loading Create New Sacco
    public  void  View_Sacco( View v){
        Intent intent = new Intent(NavigationActivity.this, SaccoList.class);
        startActivity(intent);
    }

    //Loading Create New Sacco
    public  void  Load_Transactions( View v){
        Intent intent = new Intent(NavigationActivity.this, Transactions_Activity.class);
        startActivity(intent);
    }

    //Loading Create New Sacco
    public  void  Load_Deposit( View v){
        Intent intent = new Intent(NavigationActivity.this, DepositActivity.class);
        startActivity(intent);
    }

    //Loading Create New Sacco
    public  void  Load_Profile( View v){
        Intent intent = new Intent(NavigationActivity.this, MyProfiles.class);
        startActivity(intent);
    }
}