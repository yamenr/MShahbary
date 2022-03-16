package com.example.tfotmauthtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Selection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);
        getSupportActionBar().hide();
    }

    public void Start(View view) {
        Intent i = new Intent(this, GameActivity.class);
        startActivity(i);

    }

    public void GotoLogin(View view) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void GotoTutorial(View view) {
        Intent i = new Intent(this, Tutorial1.class) ;
        startActivity(i);
    }
}