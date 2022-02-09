package com.example.tfotmauthtest;

import static com.example.tfotmauthtest.GameView.screenRatioX;
import static com.example.tfotmauthtest.GameView.screenRatioY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class Virus {    public int speed=20;
    public boolean wasHit = false;
    int x=0,y,width,height, VirusCounter =1;
    Bitmap virus1;
    Virus (Resources res)
    {
        virus1 = BitmapFactory.decodeResource(res,R.drawable.covid19);

        width= virus1.getWidth();
        height= virus1.getHeight();
        width/=6;
        height/=6;
        width*=(int) screenRatioX;
        height*=(int) screenRatioY;

        virus1 =Bitmap.createScaledBitmap(virus1,width,height,false);

        y=-height;


    }
    Bitmap getVirus(){

        return virus1;

    }

    Rect getCollisionShape (){
        return new Rect(x,y,x+width,y+height);
    }
}
