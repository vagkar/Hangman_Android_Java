package com.example.hangman;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

public class LeaderBoard extends AppCompatActivity
{
    private LinearLayout leaderBoardLayout = null;
    private ArrayList<Players> players = null;
    //private ArrayAdapter<Players> playerAdapter = null;
    //private ListView playerList = null;
    private LogIn login = null;
    private Database db = LogIn.db;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leader_board);

        leaderBoardLayout = findViewById(R.id.leaderBoardLayout);
        final ListView playerList = findViewById(R.id.players_list);
        //leaderBoardLayout.addView(playerList);
        try{
            players = new ArrayList<>();
            players = db.getPlayers();

            PlayerItemAdapter playerAdapter = new PlayerItemAdapter(this, players);

            playerList.setAdapter(playerAdapter);
        }catch (Exception e) {
            Log.d("Error", e.getMessage());
        }

        playerList.invalidate();
    }
}