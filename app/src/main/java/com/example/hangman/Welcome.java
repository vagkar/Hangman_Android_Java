package com.example.hangman;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Welcome extends AppCompatActivity implements View.OnClickListener
{
    private Button playButton = null;
    private Button leaderBoardButton = null;
    private Button exitButton = null;
    private TextView textView = null;
    private Bundle bundle = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);

        bundle = getIntent().getExtras();

        playButton = findViewById(R.id.playButton);
        leaderBoardButton = findViewById(R.id.leaderBoardButton);
        exitButton = findViewById(R.id.exitButton);
        textView = findViewById(R.id.welcomeView);
        textView.setText("Welcome, " + bundle.getString("name"));

        playButton.setOnClickListener(this);
        leaderBoardButton.setOnClickListener(this);
        exitButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {

            case R.id.playButton:
                Intent u = new Intent(this, Game.class);
                u.putExtra("name", bundle.getString("name"));
                startActivity(u);
                break;

            case R.id.leaderBoardButton:
                Intent i = new Intent(this, LeaderBoard.class);
                startActivity(i);
                break;

            case R.id.exitButton:
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setMessage("Are you sure you want to log out?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        onBackPressed();
                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {

                    }
                });
                alert.create().show();
                break;

            default:
                break;
        }
    }
}