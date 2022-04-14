package com.example.tfotmauthtest;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;

import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class GameView extends SurfaceView implements  Runnable {
    private static final String TAG = "GameView";
    private SharedPreferences prefs;
    private FirebaseServices fbs;
    private Thread thread;
    private Random random;
    public static float screenRatioX, screenRatioY;
    // OBJECTS
    private SoundPlayer sound;
    private Background background1, background2;
    private Paint paint;
    Character character;
    private Slash slash;
    private Virus[] viruses;
    private Mask[] masks;
    private Vaccine vaccine;
    Retry retry;
    private Win win;
    Hearts heart1;
    Hearts heart2;
    Hearts heart3;
    //BOOLEAN
    boolean isPlaying = true;
    boolean isGameOver=false;
    boolean isGameFinished=false;
    private boolean isCollected=false;
    private boolean unlockvaccine=false;
    private boolean unlockslash=false;
    boolean activateslash=false;
    boolean TimerAlreadyExists=false;
    private boolean Retryisdrawn=false;
    // INT
    int jumpcounter=0;
    int screenX, screenY;
    private int  hearts=4;
    private int extrahearts=0;
    private int score=0;
    private int viruslvl=0;
    private int washit=-1;
    private int heartsrequire=0;

    public GameView(GameActivity activity, int screenX, int screenY) { // note: I need explanation for the usage of this.
        super(activity);
        prefs = activity.getSharedPreferences("game", Context.MODE_PRIVATE);
        sound = new SoundPlayer(activity);
        fbs = FirebaseServices.getInstance();
        this.screenX = screenX;
        this.screenY = screenY;
        screenRatioX = 1920f / screenX;
        screenRatioY = 1020f / screenY;
        retry= new Retry(screenX,screenY,getResources());
        background1 = new Background(screenX, screenY, getResources());
        background2 = new Background(screenX, screenY, getResources());
        background2.x = screenX;
        paint = new Paint();
        paint.setTextSize(30);
        character = new Character(this, screenY, getResources());
        viruses=new Virus[] {new Virus(getResources()), new Virus(getResources())};
        masks=new Mask[] {new Mask(getResources()), new Mask(getResources())};
        vaccine= new Vaccine(this,screenY,getResources());
        heart1 = new Hearts(this, screenY, getResources());
        heart2 = new Hearts(this, screenY, getResources());
        heart3 = new Hearts(this, screenY, getResources());
        slash= new Slash(this,screenY,getResources());
        random=new Random();
        hearts=4;
        extrahearts=0;
        viruslvl=0;
        washit=-1;
        this.win= new Win(screenX, screenY, getResources());
         jumpcounter=0;
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
    @Override
    public boolean performClick() {
        super.performClick();
        return true;
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
                // BACKGROUND SECTION

                canvas.drawBitmap(background1.background, background1.x, background1.y, paint);
                canvas.drawBitmap(background1.background, background2.x, background2.y, paint);
                // HEARTS SECTION

                    canvas.drawBitmap(heart1.getHeart(), heart1.x, heart1.y, paint);
                    canvas.drawBitmap(heart2.getHeart(), heart2.x + 130, heart2.y, paint);
                    canvas.drawBitmap(heart3.getHeart(), heart3.x + 260, heart3.y, paint);


                    // CHARACTER SECTION
                if (character.isJumping&&!isGameOver)
                    canvas.drawBitmap(character.CharacterJump(), character.x, character.y, paint);
                if(!character.isJumping&&!isGameOver)
                canvas.drawBitmap(character.getCharacter(), character.x, character.y, paint);
                // SLASH SECTION
                if (unlockslash)
                {
                    canvas.drawBitmap(slash.getSlash(), slash.x, slash.y, paint);
                    //sound.playSlashSound();
                    Log.d("GameView", "slash is drawn");
                }







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
                    Retryisdrawn=true;
                    // TODO: add user score to database (Firebase - Firestore)
                    String username = fbs.getAuth().getCurrentUser().getEmail();
                    String photo = "";
                    User user= new User(username, photo, score); // get user
                    fbs.getFire().collection("restaurants")
                            .add(user)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error adding document", e);
                                }
                            });

                   return;
                }
                if (isGameFinished) { // When game is finished
                    Log.d("GameView", "Win1");
                    isPlaying = false;
                    canvas.drawBitmap(win.win, win.x, win.y, paint);
                    Log.d("GameView", "win is drawn");
                    getHolder().unlockCanvasAndPost(canvas);
                    Log.d("GameView", "finished drawing win");

                    return;
                }
                canvas.drawText("Score: " + score, 100,150, paint);
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
            jumpcounter++;
            if(jumpcounter==1)
            sound.playJumpSound();
            character.y -= 5 * screenRatioY;
            character.x+=5*screenRatioX;



        }
        if(character.y>=350&&character.y<=400&&!character.isJumping&& character.x>=200) // when the character touches the ground he steps back
        {
            character.x-=5* screenRatioX;
        }
        if(character.x>=screenX) // if the character reaches the right edge of the screen he goes back to the left edge
            character.x=30;
            if(character.y<30)
            {
            character.y -= 2 * screenRatioY; // this will make the charcter fall from max height
                character.isJumping=false;
            }
            if(!character.isJumping)
                jumpcounter=0;
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
            // Virus touches slash
            if(Rect.intersects(virus.getCollisionShape(),slash.getCollisionShape())){
                Log.d("GameView", "virus touched slash");
                virus.x=-500;
            }
            if (Rect.intersects(virus.getCollisionShape(),character.getCollisionShape())) // virus touches the character
            {
                Log.d("GameView", "Virus touched character ");
                virus.x = -500;

                if(character.isJumping!=true) {
                    Log.d("GameView", "Virus touched character and enterd here(1) ");
                    sound.playWrongSound();
                    virus.x = -500;
                    score-=5;

                    Log.d("GameView", "Virus touched character and enterd here(2) ");


                }


                if(character.isJumping==true) {
                    Log.d("GameView", "Virus touched character and enterd here(1) ");
                    sound.playCollectSound();
                    virus.x = -500;
                    score+=5;

                    Log.d("GameView", "Virus touched character and enterd here(2) ");


                }
                if(hearts >=0&&!character.isJumping) {
                    washit+=1;
                 //   viruslvl+=1;
                   // hearts-=viruslvl;
                    hearts--;

                    Log.d("GameView", "hearts are equal to 2(1) ");
                    if(washit==0) {
                        Log.d("GameView", "hearts are equal to 2(2) ");
                        heart3.x = -500;
                    }
                    else if(heart3.x==-500&&washit>1) {
                        Log.d("GameView", "hearts are equal to 1 ");
                        heart2.x = -500;
                    }






                }
                else if(hearts<0)  {
                    Log.d("GameView", "hearts are equal to 0 or less (1) ");

                    if(heart1.x==-500&&!character.isJumping)
                     isGameOver = true;
                    if(heart2.x==-500) {
                        Log.d("GameView", "hearts are equal to 0 ");
                        heart1.x = -500;
                        break;
                    }
                    Log.d("GameView", "hearts are equal to 0 or less (2) ");


                }








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
                sound.playCollectSound();
                mask.x=-600;
                score+=5;
                extrahearts++;
               // if(extrahearts==10)
                  //  hearts++;

                //   MaskTimer();
                Log.d("GameView", "Mask  finished touching character ");
            }

        }

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        // VACCINE SECTION
        if(score>=100)
        {
            Log.d("GameView", "Points are over 100 ");
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
        if(score>=100)
        {
            unlockslash=true;
            Log.d("GameView", "unlocked slash");
            if(unlockslash&&activateslash)
            {
                Log.d("GameView", "unlocked & activated slash");

                 slash.y= 80;
                 slash.x+=5;
            }
        }
        // SCORE SECTION


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
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()){
//            case MotionEvent.ACTION_DOWN:
////                Log.d("GameViewTouch", "eventX" + String.valueOf(event.getX()));
////                Log.d("GameViewTouch", "retryX" +String.valueOf(retry.x));
////                Log.d("GameViewTouch", "eventY" + String.valueOf(event.getY()));
////                Log.d("GameViewTouch", "retryY" + String.valueOf(retry.y));
////                Log.d("GameViewTouch", String.valueOf(isGameOver));
//                if(isGameOver&&event.getX()>=retry.x&&event.getX()<=retry.x+200&&event.getY()>=retry.y&&event.getY()<=retry.y+200)
//                {
//                    Log.d("GameView", "clicked retry");
//                    run();
//                }
//              else  if (event.getX() < (screenX/2) ){
//                    character.isJumping=true;
//                }
//
//
//                break;
//            case MotionEvent.ACTION_UP:
//                character.isJumping=false;
//                if (heart1.x==-500 ){
//                    activateslash=true;
//                    Log.d("GameView", "activated slash");
//                }
//
//           //     if(event.getX()>screenX/2)
//           //      activateslash=true;
//                break;
//        }
//        return true;

//    }
}
