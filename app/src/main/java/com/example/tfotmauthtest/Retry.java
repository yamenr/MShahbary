package com.example.tfotmauthtest;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Retry {
    int y=155,x=645,wdith,height;
    Bitmap retry;

    Retry(int screenX, int screenY, Resources res){
        retry = BitmapFactory.decodeResource(res,R.drawable.retry);
        this.wdith=200;
        this.height=100;
        retry = Bitmap.createScaledBitmap(retry, this.wdith,this.height,false);
    }
}
