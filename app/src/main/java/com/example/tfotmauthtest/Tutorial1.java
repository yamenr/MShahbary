package com.example.tfotmauthtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Tutorial1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial1);
        getSupportActionBar().hide();
    }
    public void GotoPage2(View view) {
        Intent i = new Intent(this, Tutorial2.class);
        startActivity(i);

    }
}