package com.example.tfotmauthtest;

import static com.example.tfotmauthtest.GameView.screenRatioX;
import static com.example.tfotmauthtest.GameView.screenRatioY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class Mask {
    public int speed=5;
    public boolean wasCollected = false;
    int x=0,y,width,height;
    Bitmap mask1,mask2,dead;
    Mask(Resources res)
    {

        mask1= BitmapFactory.decodeResource(res,R.drawable.maskk);
        mask1= BitmapFactory.decodeResource(res,R.drawable.maskk);
        width= mask1.getWidth();
        height=mask1.getHeight();
        width/=6;
        height/=6;
        width*=(int) screenRatioX;
        height*=(int) screenRatioY;

        mask1=Bitmap.createScaledBitmap(mask1,width,height,false);
        mask2=Bitmap.createScaledBitmap(mask1,width,height,false);



        y=-height;


    }
    Bitmap getMask(){
        return mask1;
    }

    Rect getCollisionShape (){
        return new Rect(x,y,x+width,y+height);
    }

}


