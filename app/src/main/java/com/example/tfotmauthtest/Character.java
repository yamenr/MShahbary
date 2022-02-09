package com.example.tfotmauthtest;

import static com.example.tfotmauthtest.GameView.screenRatioX;
import static com.example.tfotmauthtest.GameView.screenRatioY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class Character {
    public boolean isJumping = false;
    int x,y,width,height, jumpCounter =0,shootCounter=1;
    Bitmap Character0,Character1, Character2,Character3, slash1, slash2,shoot3,shoot4,shoot5,dead;
    private GameView gameView;
    Character(GameView gameView, int screenY, Resources res){
        this.gameView= gameView;
        Character0 = BitmapFactory.decodeResource(res,R.drawable.og );
        Character1 = BitmapFactory.decodeResource(res,R.drawable.jump2 );
        Character2 = BitmapFactory.decodeResource(res,R.drawable.jump3 );
        Character3 = BitmapFactory.decodeResource(res,R.drawable.jump4 );

        width= Character1.getWidth();
        height= Character1.getHeight();

        width/=4;
        height/=4;
        width*=(int)screenRatioX;
        height*=(int)screenRatioY;
        Character1 = Bitmap.createScaledBitmap(Character1,width,height,false);
        Character2 = Bitmap.createScaledBitmap(Character2,width,height,false);

        //shooting , slashing whatever you call it :P
        slash1 =BitmapFactory.decodeResource(res,R.drawable.walk1);
        slash2 =BitmapFactory.decodeResource(res,R.drawable.walk2);

        slash1 = Bitmap.createScaledBitmap(slash1,width,height,false);
        slash2 = Bitmap.createScaledBitmap(slash2,width,height,false);


        dead =BitmapFactory.decodeResource(res,R.drawable.walk1);
        dead=Bitmap.createScaledBitmap(dead,width,height,false);


        y=screenY/2; // need to change
        x= (int)(64 * screenRatioX);

    }
    Bitmap getCharacter(){

        return Character0;

    }
    Bitmap CharacterJump(){

        if(jumpCounter ==1) {
            jumpCounter++;
            return Character1;
        }
        if(jumpCounter ==2) {
            jumpCounter++;
            return Character2;
        }
        jumpCounter=1;
        return Character3;

    }
    Rect getCollisionShape (){
        return new Rect(x,y,x+width,y+height);
    }

    Bitmap getDead() {
        return dead;
    }
}
