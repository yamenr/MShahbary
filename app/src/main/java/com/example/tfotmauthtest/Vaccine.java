package com.example.tfotmauthtest;

import static com.example.tfotmauthtest.GameView.screenRatioX;
import static com.example.tfotmauthtest.GameView.screenRatioY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class Vaccine {
    public int speed=20;
    int x=0,y,width,height;
    Bitmap vaccine;
    Vaccine (Resources res)
    {
        vaccine = BitmapFactory.decodeResource(res,R.drawable.covidkiller);

        width= vaccine.getWidth();
        height= vaccine.getHeight();
        width/=3;
        height/=3;
        width*=(int) screenRatioX;
        height*=(int) screenRatioY;

        vaccine =Bitmap.createScaledBitmap(vaccine,width,height,false);

        y=-height;


    }
    Bitmap getVaccine(){

        return vaccine;

    }

    Rect getCollisionShape (){
        return new Rect(x,y,x+width,y+height);
    }
}
