package com.example.tfotmauthtest;

import static com.example.tfotmauthtest.GameView.screenRatioX;
import static com.example.tfotmauthtest.GameView.screenRatioY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Hearts {
    int x=0,y=10,width,height;
    Bitmap heart;
    Hearts(GameView gameView, int screenY, Resources res)
    {
        heart = BitmapFactory.decodeResource(res,R.drawable.heart);

        width= heart.getWidth();
        height= heart.getHeight();
        width/=6;
        height/=6;
        width*=(int) screenRatioX;
        height*=(int) screenRatioY;

        heart =Bitmap.createScaledBitmap(heart,width,height,false);




    }
    Bitmap getHeart(){

        return heart;

    }
}
