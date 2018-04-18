package com.example.nout.guessthewords;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class FinishActivity extends AppCompatActivity {
    private DBHelper dbHelper=new DBHelper(this);


    private Button winnersButton;
    private Button exitButton;
    private Button newGameButton;
    private ListView winnersListView;
    private WebView winOrLoseGif;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);

        winnersButton = (Button) findViewById(R.id.showWin);
        winOrLoseGif = (WebView) findViewById(R.id.winOrLoseGif);
        exitButton = (Button) findViewById(R.id.exitButton);
        newGameButton = (Button) findViewById(R.id.newGameButton);
        winnersListView = (ListView) findViewById(R.id.winnersListView);

        setWinOrLoseGif();

        newGameButton.setVisibility(View.VISIBLE);
        winnersButton.setVisibility(View.VISIBLE);
        winnersListView.setVisibility(View.INVISIBLE);
        winnersButton.setClickable(true);

        //THIS IS FOR LISTVIEW HEADER
        LayoutInflater inflater = getLayoutInflater();
        ViewGroup header = (ViewGroup)inflater.inflate(R.layout.listview_header,winnersListView,false);
        winnersListView.addHeaderView(header);

        winnersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                winnersListView.setVisibility(View.VISIBLE);
                winnersButton.setClickable(false);

                List<String> list = dbHelper.getWinnersData();
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(FinishActivity.this,android.R.layout.simple_list_item_1,list){
                    @Override
                    // This is for Set text size 30 dip for listview each item
                    public View getView(int position, View convertView, ViewGroup parent) {
                        View view = super.getView(position, convertView, parent);
                        TextView tv = (TextView) view.findViewById(android.R.id.text1);
                        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                        tv.setBackgroundColor(Color.GRAY);
                        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        return view;
                    }
                };

                if(GameActivity.score > 100) {
                    dbHelper.addWinner();
                    list = dbHelper.getWinnersData();
                    adapter = new ArrayAdapter<String>(FinishActivity.this,android.R.layout.simple_list_item_1,list);

                    //SETTING ADAPTER TO LISTVIEW
                    winnersListView.setAdapter(adapter);
                    winnersListView.setBackgroundColor(Color.WHITE);
                    winOrLoseGif.setVisibility(View.INVISIBLE);
                    Log.d("Winners Data", " : " +list);
                }
                else{


                    winnersListView.setVisibility(View.VISIBLE);
                    winnersListView.setAdapter(adapter);
                    Log.d("Winners Data", " : " +list);
                    winOrLoseGif.setVisibility(View.INVISIBLE);
                }
            }
        });


        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });


        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameActivity.score=0;
                GameActivity.levelCount=1;
                FinishActivity.this.finish();
                Intent intent = new Intent(FinishActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }


    public void setWinOrLoseGif(){

        if(GameActivity.score > 100)
            winOrLoseGif.loadUrl("file:///android_asset/winn.gif");
        else
            winOrLoseGif.loadUrl("file:///android_asset/lose.gif");

    }
}
