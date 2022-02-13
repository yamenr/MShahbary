package com.example.tfotmauthtest;

import static com.example.tfotmauthtest.GameView.screenRatioX;
import static com.example.tfotmauthtest.GameView.screenRatioY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class slash {
    int x, y, width, height;
    Bitmap slash;
    private GameView gameView;
    slash(GameView gameView, int screenY, Resources res) {

        slash = BitmapFactory.decodeResource(res, R.drawable.slashh);

        width = slash.getWidth();
        height = slash.getHeight();

        width /= 1;
        height /= 1;

        width = (int) (width * screenRatioX);
        height = (int) (height * screenRatioY);

        slash = Bitmap.createScaledBitmap(slash, width, height, false);

    }

    Rect getCollisionShape () {
        return new Rect(x, y, x + width, y + height);
    }

}
