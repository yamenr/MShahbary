package com.example.tfotmauthtest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class GameView extends SurfaceView implements  Runnable {

    private Thread thread;
    private Random random;
    public static float screenRatioX, screenRatioY;
    // OBJECTS
    private Background background1, background2;
    private Paint paint;
    private Character character;
    private slash slash;
    private Virus[] viruses;
    private Mask[] masks;
    private Vaccine vaccine;
    private Retry retry;
    private Win win;
    //BOOLEAN
    boolean isPlaying = true;
    private boolean isGameOver=false;
    private boolean isGameFinished=false;
    private boolean isCollected=false;
    private boolean unlockvaccine=false;
    private boolean unlockslash=false;
    private boolean activateslash=false;
    boolean TimerAlreadyExists=false;
    // INT
    private int screenX, screenY;
    private int  hearts=5;
    private int extrahearts=1;
    private int score=0;
    private int viruslvl=0;
    private int heartsrequire=0;

    public GameView(GameActivity activity, int screenX, int screenY) { // note: I need explanation for the usage of this.
        super(activity);
        this.screenX = screenX;
        this.screenY = screenY;
        screenRatioX = 1920f / screenX;
        screenRatioY = 1020f / screenY;
        retry= new Retry(screenX,screenY,getResources());
        background1 = new Background(screenX, screenY, getResources());
        background2 = new Background(screenX, screenY, getResources());
        background2.x = screenX;
        paint = new Paint();
        character = new Character(this, screenY, getResources());
        viruses=new Virus[] {new Virus(getResources()), new Virus(getResources())};
        masks=new Mask[] {new Mask(getResources()), new Mask(getResources())};
        vaccine= new Vaccine(this,screenY,getResources());
        slash= new slash(this,screenY,getResources());
        random=new Random();
        hearts=3;
    }

    @Override
    public void run() {
        while (isPlaying) {
                Log.d("GameView", "run: Entered else");
                update();
                draw();
                sleep();
        }

    }
    private void MaskTimer(){

        long duration;
        if(TimerAlreadyExists)
            duration= TimeUnit.SECONDS.toMillis(0+((extrahearts-1)*5)); // if there is a timer already working means that there is a current mask being used so it adds 5+ secs to each mask added on top thats why we substract 1 which is the first mask that gives 20 secs.
        else duration= TimeUnit.SECONDS.toMillis(20); // If there is no mask the first mask you get gives u 20secs otherwise adds 5 secs to each mask gained.

        new CountDownTimer(duration, 1000) {
            @Override
            public void onTick(long l) {
                String sDuration=String.format(Locale.ENGLISH,"%02d:%02",TimeUnit.MILLISECONDS.toMinutes(l),TimeUnit.MILLISECONDS.toSeconds(l)-TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(l)));
                //textView.setText(sDuration); No Use
                TimerAlreadyExists=true;

            }

            @Override
            public void onFinish() {
                extrahearts =0;
                TimerAlreadyExists=false;
                //textView.setVisibility(View.GONE);

            }
        }.start();
    }

    private void sleep() {
        try {
            Thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void draw() {
        if (getHolder().getSurface().isValid()) {

            Canvas canvas = getHolder().lockCanvas();
            if (canvas != null) {
                Log.d("GameView", "draw: I am not null");
                canvas.drawBitmap(background1.background, background1.x, background1.y, paint);
                canvas.drawBitmap(background1.background, background2.x, background2.y, paint);
                if(!character.isJumping&&!isGameOver)
                canvas.drawBitmap(character.getCharacter(), character.x, character.y, paint);
                if (character.isJumping&&!isGameOver)
                    canvas.drawBitmap(character.CharacterJump(), character.x, character.y, paint);






                for (Virus virus : viruses) {

                    canvas.drawBitmap(virus.getVirus(), virus.x, virus.y, paint);
                }
                for (Mask mask : masks) {
                    canvas.drawBitmap(mask.getMask(), mask.x, mask.y, paint);
                }
                if (unlockvaccine) // if this happens means that the player gained enough score to finish the level, as a result the vaccine bitmap appears on the screen and can be collected
                {
                    canvas.drawBitmap(vaccine.getVaccine(), vaccine.x, vaccine.y, paint);
                }
                if (isGameOver) { // When game is over

                    isPlaying = false;
                    canvas.drawBitmap(character.getDead(), character.x, character.y, paint);
                    canvas.drawBitmap(retry.retry, retry.x, retry.y, paint);
                    getHolder().unlockCanvasAndPost(canvas);


                    return;
                }
                if (isGameFinished) { // When game is finished

                    isPlaying = false;

                    canvas.drawBitmap(win.win, win.x, win.y, paint);
                    getHolder().unlockCanvasAndPost(canvas);


                    return;
                }

                getHolder().unlockCanvasAndPost(canvas);
            }
            else{
                Log.d("GameView", "draw: canvas is null");
            }

        }
    }

    private void update() {
        // BACKGROUND  SECTION
        background2.x -= 10 * screenRatioX;
        background1.x -= 10 * screenRatioX;
        if (background1.x + background1.background.getWidth() < 0) {
            background1.x = screenX;
        }
        if (background2.x + background2.background.getWidth() < 0) {
            background2.x = screenX;
        }
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        // CHARACTER SECTION

        if (character.isJumping)           // movement when jumping
        {
            character.y -= 5 * screenRatioY;
            character.x+=5*screenRatioX;
        }
        if(character.y>=350&&character.y<=400&&!character.isJumping&& character.x>=200) // when the character touches the ground he steps back
        {
            character.x-=5*screenRatioX;
        }
        if(character.x>=screenX) // if the character reaches the right edge of the screen he goes back to the left edge
            character.x=30;
            if(character.y<30)
            {
            character.y -= 2 * screenRatioY; // this will make the charcter fall from max height
                character.isJumping=false;
            }
            if(!character.isJumping)
                character.y-=1* screenRatioY; // to make the character fall
            if(character.y<350&&character.isJumping==false) // in case the character got below -300 on y axis it returns him on -300 on y axis
                character.y=350;

        if(character.y<50) // this is to limit his jump
            character.isJumping=false;
      /*  if (character.y < screenY / 2) //original: (character.y<0) the changes were to make the character jump height limit to the middle of the screen.
           character.y = 0;  I see no use for this yet */
      /*  if (character.y > screenY - character.height)
            character.y = screenY - character.height; // END: jump */

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        // VIRUS SECTION
        for (Virus virus: viruses){
            if (Rect.intersects(virus.getCollisionShape(),character.getCollisionShape())) // virus touches the character
            {
                Log.d("GameView", "Virus touched character ");

                if(character.isJumping==true) {
                    Log.d("GameView", "Virus touched character and enterd here(1) ");
                    virus.x = -500;
                    score+=10;

                    Log.d("GameView", "Virus touched character and enterd here(2) ");


                }
                if(hearts >=1&&!character.isJumping) {
                    Log.d("GameView", "hearts are equal to 0 or less(3) ");
                    virus.x = -500;
                    viruslvl+=1;
                    hearts-=viruslvl;

                    Log.d("GameView", "hearts are equal to 0 or less(4) ");


                }
                else if(hearts<=0)  {
                    Log.d("GameView", "hearts are equal to 0 or less (1) ");
                    isGameOver = true;
                    Log.d("GameView", "hearts are equal to 0 or less (2) ");
                    //  return;

                }


           /*      if(character.isJumping==false) {
                     Log.d("GameView", "Character isn't jumping and we reached here ");
                   if (extrahearts >= 1) {
                        Log.d("GameView", "it Crashed when hearts>0 and jumping is false ");
                        extrahearts = extrahearts - 4; // since every 5 masks are identical to 1 heart substracting 4 will make it work : for example: u have 10 masks which should save u from 3 hits 10-4 = 6 will save u from 2 hits 6-4=2 should save u from another hit 2-4=-2
                    //    MaskTimer();
                        if (extrahearts <= 0)  // according to the previous example if we got a negative result and we collect another mask after losing all masks it should save us but in the same case i mentioned in the example it won't work as it will add 1 mask to the current number of maks : 1+ -2 =-1 meaning it won't protect us
                        {
                            Log.d("GameView", "it Crashed when extra hearts=0 ");
                            extrahearts = 0;}


                    }  Needs some edit mai2
                     hearts--;
                 }*/





            }
            else Log.d("GameView", "Virus  didn't touch character ");

            virus.x-=virus.speed; // this will make the virus move towards the character's direction
            if (virus.x+virus.width<0) // if this happens means the virus is off the screen form the left
            {

                int bound = (int) (5 * screenRatioX);
                virus.speed= random.nextInt(bound);

                if (virus.speed<10* screenRatioX) // the 10 is the minimum speed the multiplying is for the speed to be suited for all screen sizes
                    virus.speed= (int) (5 * screenRatioX);

                virus.x=screenX;
                 virus.y=400;
                virus.wasHit=false;
                // Virus touches character

                // Virus touches slash
                if(Rect.intersects(virus.getCollisionShape(),slash.getCollisionShape())){
                    virus.x=-500;
                }
            }

        }

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        //MASK SECTION

        for (Mask mask: masks){
             mask.x-=mask.speed;
            if (mask.x+mask.width<0)
            {
                mask.x=screenX;
                  mask.y=300;

            }
            if (Rect.intersects(mask.getCollisionShape(),character.getCollisionShape()))
            {
                Log.d("GameView", "Mask  did touch character ");
                mask.x=-600;
                score+=10;
                extrahearts++;
               // if(extrahearts==10)
                    hearts++;

                //   MaskTimer();
                Log.d("GameView", "Mask  finished touching character ");
            }

        }

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        // VACCINE SECTION
        if(score>=50)
        {
            Log.d("GameView", "Points are over 50 ");
            if(Rect.intersects(character.getCollisionShape(),vaccine.getCollisionShape())) {
                Log.d("GameView", "Player touched vaccine ");
                isGameFinished=true; // this when you finish the level
            }

            unlockvaccine = true;
            vaccine.x=1020;
            vaccine.y=220;
          //  vaccine.x-=vaccine.speed;
            //here im trying to set the vaccine at the right side of the screen


        }


        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        // SLASH  SECTION
        if(score>=250)
        {
            unlockslash=true;
            if(unlockslash&&activateslash)
            {
                 slash.x= character.x;
                 slash.y= character.y;
                 slash.x-=45;
            }
        }
        Log.d("GameView", "update: Finished function");
    }

    public void resume() {
        isPlaying = true;
        thread = new Thread(this);
        thread.start();

    }

    public void pause() {
        try {
            isPlaying = false;
            thread.join();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (event.getX() < (screenX/2) ){
                    character.isJumping=true;
                }
                if(isGameOver&&event.getX()==retry.x&&getY()==retry.y)
                {
                    Log.d("GameView", "clicked retry");
                    run();
                }
                break;
            case MotionEvent.ACTION_UP:
                character.isJumping=false;

                if(event.getX()>screenX/2)
                 activateslash=true;
                break;
        }
        return true;

    }
}
