package com.example.nout.guessthewords;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private GridLayout gridLay;
    private LinearLayout Linlay;
    private List<TextView> wordsList = new ArrayList<>();
    private Button[] buttonArray = new Button[26];
    private List<String> words = new ArrayList<String>();
    private List<String> words1 = new ArrayList<String>(Arrays.asList("earth", "jupiter", "mars", "mercury", "neptune", "saturn", "uranus", "venus","baikal","victoria","michigan","amazon","danube","niagara"));
    private List<String> words2 = new ArrayList<String>(Arrays.asList("picasso","rembrandt","michelangelo","dali","jackson","lennon","beyonce","madonna","beethoven","mozart"));
    private List<String> words3 = new ArrayList<String>(Arrays.asList("starfish", "lion", "dog", "cat", "bear", "fox","swan","apple","apricot","banana","avocado","cherry", "dragon", "summer","winter","spring","autumn","cloud","rose","peony","snowflake","sunflower"));

    private char[] letters = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    private TextView levelView;
    private TextView scoreView;
    private TextView categoryView;
    private TextView playerNameView;
    private Button backButton;
    private String word;
    private String tar[];
    private String letter = "";
    private int count = 0;
    public static int score = 0;
    public static int levelCount = 1;
    private Random r = new Random();
    private  Button soundOnOffButton;
    private Dialog backDialog;
    private Button dialogCancelButton;
    private Button dialogAgreeButton;
    private Animation animRotate,animZoomIn, animZoomOut;
    private  WebView levelWeb;
    private int width;
    private int height;
    private Handler h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;
        Log.d("SCREEEEEEEEEEEEEEN",""+ width+","+height);

        levelWeb = (WebView) findViewById(R.id.webView);

        animRotate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
        animZoomIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in);
        animZoomOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_out);


        levelWeb.setX(width/2);
        levelWeb.setY(height/2);
        //levelAnimation();

        soundOnOffButton = (Button) findViewById(R.id.soundButton);
        soundOnOffButton.setOnClickListener(this);


        MainActivity.mediaPlayer = MediaPlayer.create(this, R.raw.game_sound);
        soundPlayingControl();

        backDialog = new Dialog(this);
        backDialog.setContentView(R.layout.back_dialog);

        dialogCancelButton=(Button)backDialog.findViewById(R.id.dialog_cancel);
        dialogAgreeButton=(Button)backDialog.findViewById(R.id.dialog_agree);
        dialogAgreeButton.setOnClickListener(this);
        dialogCancelButton.setOnClickListener(this);

        backButton = (Button) findViewById(R.id.buttonexit);
        backButton.setOnClickListener(this);


        Linlay = (LinearLayout) findViewById(R.id.linLay);
        gridLay = (GridLayout) findViewById(R.id.gridLay);

        levelView = (TextView) findViewById(R.id.levelView);
        scoreView = (TextView) findViewById(R.id.scoreView);
        categoryView = (TextView) findViewById(R.id.categoryView);
        playerNameView = (TextView) findViewById(R.id.playerNameTV);


        setLevelScoreCategory();
        chooseWordCategory();
        generateWord();
        generateButtons();


       /* h = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if(msg.what){

                }
                else {

                }
            }
        };*/
    }

    public void pauseGuessedWord(){
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {

                    try {
                        java.util.concurrent.TimeUnit.SECONDS.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

        });
        t1.start();
    }

    public void setLevelScoreCategory() {

        levelView.setText("Level: " + levelCount);
        scoreView.setText("Score: " + score);
        categoryView.setText("Category: " + MainActivity.selectedCategory);
        playerNameView.setText("Player: "+ StartActivity.userInput.getText());
    }

    public void chooseWordCategory(){

        words.clear();
        if(MainActivity.selectedCategory=="Science & More"){
           words=words1;
        }
        if(MainActivity.selectedCategory=="Art & Music"){
            words=words2;
        }
        if(MainActivity.selectedCategory=="Nature & Animals"){
            words=words3;
        }
    }


    public void generateWord() {
        int random = r.nextInt(words.size());
        word = words.get(random);
        Log.d("WORDDDDDDD","AMEN ANGAM TARBER "+word);
        words.remove(word);

       // word = words[(int) (0 + Math.random() * words.length)];
        tar = new String[word.length()];

        wordsList.clear();
        for (int i = 0; i < word.length(); i++) {

            tar[i] = word.substring(i, i + 1);

            wordsList.add(i, new EditText(this));
            wordsList.get(i).setBackgroundResource(R.mipmap.word_frame);
            //wordsList.get(i).setBackgroundColor(Color.parseColor("#FFFFFF"));
            wordsList.get(i).setFocusable(false);
            wordsList.get(i).setTextSize(20);
            wordsList.get(i).setId(i);
            wordsList.get(i).setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            Linlay.addView(wordsList.get(i), width/word.length(), width/word.length());

        }
    }


    public void generateButtons() {

        for (int i = 0; i < 26; i++) {

            buttonArray[i] = new Button(this);
            buttonArray[i].setText(letters[i] + "");
            buttonArray[i].setClickable(true);
            buttonArray[i].setOnClickListener(this);
            buttonArray[i].setBackgroundColor(Color.rgb((int) (Math.random() * 255), (int) (Math.random() * 255), ((int) (Math.random() * 255))));
            buttonArray[i].getBackground().setAlpha(80);
            buttonArray[i].setTextColor(Color.WHITE);
            buttonArray[i].setTextSize(25);
            buttonArray[i].setId(i);

            gridLay.setColumnCount(7);
            gridLay.addView(buttonArray[i],width/7,width/7);



        }
        Log.d("BUTTON SIZE="," "+ width/7);
    }

    int ifLetterIsTrue=0;

    public void checkWord() {

        for (int i = 0; i < word.length(); i++) {
            if (letter.equals(tar[i])) {

                wordsList.get(i).setText(tar[i].toUpperCase());
                wordsList.get(i).setBackgroundColor(Color.WHITE);
                scoreView.setText("Score:" + score);
                count++;
                score += 10;
                ifLetterIsTrue++;
            }
        }
    }

    public void CheckWrongWord(){

            if(ifLetterIsTrue==0){
                score-=10;
                scoreView.setText("Score:" + score);

            }
        }



    @Override
    public void onClick(View v) {
            for (int i = 0; i < buttonArray.length; i++) {
                if (v.getId() == i) {

                    buttonArray[i].setBackgroundColor(Color.TRANSPARENT);
                    buttonArray[i].setClickable(false);
                    letter = buttonArray[i].getText().toString().toLowerCase();
                    Log.d("YOU TYPE THIS LETTER", "=" + letter);
                    checkWord();
                    CheckWrongWord();
                    ifLetterIsTrue = 0;



                    if (count == word.length() && levelCount < 3) {
                        // THREAD SLEEP
                        
                        levelCount++;
                        count = 0;
                        setLevelScoreCategory();
                        Toast.makeText(GameActivity.this, "LEVEL: " + levelCount, Toast.LENGTH_LONG).show();

                        //levelAnimation();

                        Linlay.removeAllViews();
                        generateWord();

                        gridLay.removeAllViews();
                        generateButtons();



                    }

                    if (count == word.length() && levelCount == 3 && score >= 100) {

                        MainActivity.mediaPlayer.stop();
                        MediaPlayer mediaPlayer1=MediaPlayer.create(this,R.raw.win);
                        mediaPlayer1.start();
                        Intent intent = new Intent(GameActivity.this, FinishActivity.class);
                        startActivity(intent);
                        GameActivity.this.finish();

                    }

                    if (count == word.length() && levelCount == 3 && score < 100) {

                        MainActivity.mediaPlayer.stop();
                        MediaPlayer mediaPlayer2=MediaPlayer.create(this,R.raw.lose);
                        mediaPlayer2.start();
                        Intent intent = new Intent(GameActivity.this, FinishActivity.class);
                        startActivity(intent);
                        GameActivity.this.finish();

                    }

                }
            }

            if (v.getId() == R.id.buttonexit) {


                backDialog.setTitle("ARE YOU SURE?");
                backDialog.show();
            }
            if (v.getId() == dialogCancelButton.getId()) {
                backDialog.hide();
            }
            if (v.getId() == dialogAgreeButton.getId()) {
                GameActivity.this.finish();
                StartActivity.userInput.setText(null);
                levelCount = 1;
                score = 0;

                MainActivity.mediaPlayer.stop();
                MainActivity.mediaPlayer = MediaPlayer.create(this, R.raw.start_sound);
                MainActivity.mediaPlayer.start();
                MainActivity.mediaPlayer.setLooping(true);
            }

            if (v.getId() == R.id.soundButton) {

                playOrStopSound();

            }

        }

    public void soundPlayingControl() {
        if (MainActivity.soundIsOn == true) {
            MainActivity.mediaPlayer.start();
            MainActivity.mediaPlayer.setLooping(true);
        } else {
            soundOnOffButton.setBackgroundResource(R.mipmap.off);
        }
    }

    public void playOrStopSound(){

        if(MainActivity.soundIsOn) {
            MainActivity.mediaPlayer.pause();
            MainActivity.soundIsOn = false;
            soundOnOffButton.setBackgroundResource(R.mipmap.off);
        }
        else{
            MainActivity.mediaPlayer.start();
            MainActivity.soundIsOn=true;
            soundOnOffButton.setBackgroundResource(R.mipmap.on);
        }

    }

    public void levelAnimation(){

        levelWeb.setVisibility(View.VISIBLE);
        levelWeb.setBackgroundColor(Color.TRANSPARENT);
        levelWeb.loadUrl("file:///android_asset/level"+levelCount+".gif");
        levelWeb.startAnimation(animZoomIn);

        levelWeb.setVisibility(View.INVISIBLE);
       // levelWeb.setAnimation(animZoomOut);


    }


    @Override
    protected void onStop() {
        super.onStop();
        MainActivity.mediaPlayer.stop();

    }
}
