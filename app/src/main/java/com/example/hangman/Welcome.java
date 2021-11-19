package com.example.hangman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Welcome extends AppCompatActivity implements View.OnClickListener
{
    private Button exitButton = null, leaderBoardButton = null, playButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);

        exitButton = findViewById(R.id.exitButton);
        leaderBoardButton = findViewById(R.id.leaderBoardButton);
        playButton = findViewById(R.id.playButton);

        exitButton.setOnClickListener(this);
        leaderBoardButton.setOnClickListener(this);
        playButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {

            case R.id.playButton:
                Intent gameActivity = new Intent(this, Game.class);
                startActivity(gameActivity);
                break;

            case R.id.exitButton:
                super.onBackPressed();
                break;

            case R.id.leaderBoardButton:
                Intent i = new Intent(this, LeaderBoard.class);
                startActivity(i);
                break;

            default:
                break;
        }
    }
}