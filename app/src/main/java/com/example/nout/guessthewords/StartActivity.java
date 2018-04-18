package com.example.nout.guessthewords;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by NOUT on 3/16/2017.
 */

public class StartActivity extends AppCompatActivity implements View.OnClickListener {

    private Button startButton;
    private Button closeButton;

    public static EditText userInput;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        startButton = (Button) findViewById(R.id.startButton);
        closeButton = (Button) findViewById(R.id.closeButton);


        userInput = (EditText) findViewById(R.id.userInput);

        startButton.setOnClickListener(this);
        closeButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.startButton:

                    Intent intent = new Intent(StartActivity.this, GameActivity.class);
                    startActivity(intent);
                    // Animation anim = AnimationUtils.loadAnimation(this,android.R.anim.slide_out_right);
                    overridePendingTransition(android.R.anim.slide_out_right, android.R.anim.slide_in_left);
                    MainActivity.mediaPlayer.stop();
                    finish();

                break;

            case R.id.closeButton:
                Intent intent2 = new Intent();
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                overridePendingTransition(android.R.anim.fade_out, android.R.anim.fade_out);
                finish();
                break;


        }
    }
}
