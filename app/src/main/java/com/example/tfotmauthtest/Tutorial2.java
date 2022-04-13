package com.example.tfotmauthtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Tutorial2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial2);
        getSupportActionBar().hide();
    }
    public void GotoPage3(View view) {
        Intent i = new Intent(this, Tutorial3.class);
        startActivity(i);

    }
    public void GotoPrevious(View view) {
        Intent i = new Intent(this, Tutorial1.class);
        startActivity(i);
    }
}