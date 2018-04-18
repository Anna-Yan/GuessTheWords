package com.example.nout.guessthewords;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button scienceButton;
    private Button artButton;
    private Button animalButton;
    private Button soundOnOffButton;
    public static MediaPlayer mediaPlayer;
    public static boolean soundIsOn;
    public static String selectedCategory;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //startService(new Intent(this, MyService.class));
        soundOnOffButton = (Button) findViewById(R.id.soundButton);
        mediaPlayer=MediaPlayer.create(this,R.raw.start_sound);


        mediaPlayer.start();
        soundIsOn=true;
        mediaPlayer.setLooping(true);

        /*mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.start();
            }
        });*/

        scienceButton = (Button) findViewById(R.id.scienceButton);
        artButton = (Button) findViewById(R.id.artButton);
        animalButton = (Button) findViewById(R.id.animalButton);

        scienceButton.setOnClickListener(this);
        artButton.setOnClickListener(this);
        animalButton.setOnClickListener(this);
        soundOnOffButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.scienceButton:
                selectedCategory="Science & More";

                Intent intent = new Intent(this, StartActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;

            case R.id.artButton:
                selectedCategory="Art & Music";
                Intent intent1 = new Intent(this, StartActivity.class);
                startActivity(intent1);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;

            case R.id.animalButton:
                selectedCategory="Nature & Animals";
                Intent intent2 = new Intent(this, StartActivity.class);
                startActivity(intent2);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;

            case R.id.soundButton:

                playOrStopSound();

                break;


        }
    }


    public void playOrStopSound(){

        if(soundIsOn) {
            mediaPlayer.pause();
            soundIsOn = false;
            soundOnOffButton.setBackgroundResource(R.mipmap.off);
        }
        else{
            mediaPlayer.start();
            soundIsOn=true;
            soundOnOffButton.setBackgroundResource(R.mipmap.on);
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        MainActivity.mediaPlayer.stop();

    }
}
