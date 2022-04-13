package com.example.tfotmauthtest;

import static com.example.tfotmauthtest.GameView.screenRatioX;
import static com.example.tfotmauthtest.GameView.screenRatioY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class Slash {
    int x, y, width, height;
    Bitmap slash;

    Slash (GameView gameView, int screenY, Resources res)
    {
        slash = BitmapFactory.decodeResource(res,R.drawable.slashh);

        width= slash.getWidth();
        height= slash.getHeight();
        this.width= 500;
        this.height= 600;


        slash =Bitmap.createScaledBitmap(slash,this.width,this.height,false);

        y=-height;


    }
    Bitmap getSlash(){

        return slash;

    }

    Rect getCollisionShape () {
        return new Rect(x, y, x + this.width, y + this.height);
    }

}
