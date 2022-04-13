package com.example.tfotmauthtest;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Handler;
import android.view.Display;
import android.view.View;

public class AbeerView extends View {
    int screenWidth, screenHeight, newWidth,newHeight;
    int grassX=300;
    int AbeerX,AbeerY,AbeerFrame=0;
    Bitmap grass;
    Bitmap abeer[]=new Bitmap[2];
    Handler handler;
    Runnable runnable;
    final long UPDATE_MILLIS=50;
    public AbeerView(Context context) {
        super(context);

        grass= BitmapFactory.decodeResource(getResources(),R.drawable.backgroundd);
        abeer[0]=BitmapFactory.decodeResource(getResources(),R.drawable.walk1);
        abeer[1]=BitmapFactory.decodeResource(getResources(),R.drawable.walk2);

        Display display = ((Activity)getContext()).getWindowManager().getDefaultDisplay();
        Point size= new Point();
        display.getSize(size);
        screenWidth=size.x;
        screenHeight= size.y;
      // float
        newHeight=screenHeight;
        newWidth=(int) (100*screenHeight+2000); // not sure


        grass=Bitmap.createScaledBitmap(grass,newWidth,newHeight,false);
        AbeerX=screenWidth/2-200;
        AbeerY=screenHeight-400;
        handler= new Handler();
        runnable= new Runnable(){
            @Override
            public void run(){
                invalidate();
            }

        };



    }
    @Override
    protected void  onDraw (Canvas canvas){
        super.onDraw(canvas);

        grassX-=10;
        if(grassX<-newWidth)
        {
            grassX=100;
        }
        canvas.drawBitmap(grass,grassX,0,null);
        if(0<screenWidth-newWidth){
            canvas.drawBitmap(grass,grassX+newWidth,0,null);

        }

        if(AbeerFrame==2){
            AbeerFrame=0;

        }
        canvas.drawBitmap(abeer[AbeerFrame],AbeerX,AbeerY,null);
        handler.postDelayed(runnable,UPDATE_MILLIS);
    }
}
