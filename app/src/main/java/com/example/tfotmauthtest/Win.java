package com.example.tfotmauthtest;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Win {
    int y=155,x=645,wdith,height;
    Bitmap win;

    Win(int screenX, int screenY, Resources res){
        win = BitmapFactory.decodeResource(res,R.drawable.win);
        this.wdith=200;
        this.height=100;
        win = Bitmap.createScaledBitmap(win, this.wdith,this.height,false);
    }

    Bitmap getWin(){

        return win;

    }
}
