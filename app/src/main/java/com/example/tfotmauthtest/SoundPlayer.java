package com.example.tfotmauthtest;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

public class SoundPlayer {
    private static SoundPool soundPool;
    final int SOUND_POOL_MAX=3;
    private static int openingSound;
    private static int slashSound;
    private static int wrongSound;
    private static int jumpSound;
    private static int collectSound;
    public  SoundPlayer(Context context)
    {
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP){
            AudioAttributes audioAttributes= new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .build();
            soundPool=new SoundPool.Builder()
                    .setMaxStreams(SOUND_POOL_MAX)
                    .setAudioAttributes(audioAttributes)
                    .build();
        } else {
            soundPool = new SoundPool(SOUND_POOL_MAX, AudioManager.STREAM_MUSIC, 0);
        }
        openingSound= soundPool.load(context,R.raw.water,1);
        slashSound= soundPool.load(context,R.raw.slashsound,1);
        wrongSound= soundPool.load(context,R.raw.wrong,1);
        jumpSound= soundPool.load(context,R.raw.jumpsound,1);
        collectSound= soundPool.load(context,R.raw.collect,1);

    }



    public void playOpeningSound(){
        soundPool.play(openingSound,1,1,1,0,1); // check
    }
    public void playSlashSound(){
        soundPool.play(slashSound,1,1,1,0,1); // check
    }
    public void playWrongSound(){
        soundPool.play(wrongSound,1,1,1,0,1); // check
    }
    public void playJumpSound(){
        soundPool.play(jumpSound,1,1,1,0,1); // check
    }
    public void playCollectSound(){
        soundPool.play(collectSound,1,1,1,0,1); // check
    }
}
