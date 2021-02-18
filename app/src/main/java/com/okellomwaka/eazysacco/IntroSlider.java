package com.okellomwaka.eazysacco;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class IntroSlider extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_slider);
        getSupportActionBar().hide();
    }
}