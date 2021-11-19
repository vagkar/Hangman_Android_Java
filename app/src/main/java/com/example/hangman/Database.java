package com.example.hangman;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper
{
    private String databaseName = null;
    private String tableName = "players";
    private Context myContext= null;

    public Database(Context context, String dName)
    {
        super(context, dName, null, 1);
        databaseName = dName;
        myContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("create table if not exists " + tableName + "(name text , password text , score int)");
        Log.d("test", "Database created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV)
    {
        db.execSQL("drop table if exists " + tableName);
        onCreate(db);
    }

    public ArrayList<Players> getPlayers()
    {
        SQLiteDatabase database = getWritableDatabase();
        ArrayList<Players> players = new ArrayList<>();
        Cursor cursor = database.rawQuery("select * from " + tableName, null);

        if(cursor.getCount() == 0)
        {
            cursor.close();
            return players;
        }

        int nameIndex, scoreIndex;

        nameIndex = cursor.getColumnIndex("name");
        scoreIndex = cursor.getColumnIndex("score");

        if(cursor.moveToFirst())
        {
            do
            {
                Players ps = new Players(cursor.getString(nameIndex), cursor.getInt(scoreIndex));
                players.add(ps);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return players;
    }

    public void insertPlayer(String name, String password, int score)
    {
        //int score = 3; μπήκε ως παράμετρος
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL("insert into " + tableName + "(name, password, score) values('"+ name +"','"+ password +"',"+ score +")");
    }
}
