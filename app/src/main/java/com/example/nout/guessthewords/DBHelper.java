package com.example.nout.guessthewords;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NOUT on 16.02.2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final String DBNAME="myDB";
    public static final int DBVERSION=1;
    public static final String TBNAME ="winnersTB";
    private SQLiteDatabase db;
    private int ID=1;
    private String name;
    private String score;


    public DBHelper(Context context) {
        super(context, TBNAME, null, DBVERSION);
    }


    public void addWinner(){

        ContentValues cv=new ContentValues();
        cv.put("name",StartActivity.userInput.getText().toString());
        cv.put("score",GameActivity.score);

        db=getWritableDatabase();
        db.insert(TBNAME,null,cv);

    }



    public List<String> getWinnersData(){

        List<String> winnersList = new ArrayList<>();
        db= getReadableDatabase();
        Cursor c=db.query(TBNAME,null,null,null,null,null,"score DESC");

            while(c.moveToNext()){
                //ID=c.getInt(0);
                name=c.getString(1);
                score=c.getString(2);
                winnersList.add(ID+". \t"+name+"\t - "+score);
                ID++;
            }

        c.close();
        return winnersList;
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
    String queryCreateTable = "CREATE TABLE " + TBNAME + " (id integer primary key autoincrement, name text, score text )";
    db.execSQL(queryCreateTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
