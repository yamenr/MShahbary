package com.example.tfotmauthtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Tutorial3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial3);
        getSupportActionBar().hide();
    }
    public void GotoPage4(View view) {
        Intent i = new Intent(this, Tutorial4.class);
        startActivity(i);

    }
    public void GotoPrevious(View view) {
        Intent i = new Intent(this, Tutorial2.class);
        startActivity(i);
    }
}