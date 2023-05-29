package com.example.eventi_20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Splash_screen extends AppCompatActivity {
    Handler h =new  Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(Splash_screen.this , Sign_in.class);
                startActivity(i);
                finish();
            }
        },3000);
    }
}