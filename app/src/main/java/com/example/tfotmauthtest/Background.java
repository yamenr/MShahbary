package com.example.tfotmauthtest;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Background {
    int y=0,x=0;
    Bitmap background;

    Background(int screenX, int screenY, Resources res){
        background= BitmapFactory.decodeResource(res,R.drawable.backgroundd);
        background= Bitmap.createScaledBitmap(background, screenX,screenY,false);
    }
}
