package com.example.hangman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class LogIn extends AppCompatActivity implements View.OnClickListener
{
    private TextView click = null;
    private Button logInButton = null;
    public static Database mydb = null;
    private Bundle bundle = null;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);
        mydb = new Database(this, "players.db");

        click = findViewById(R.id.clickView);
        logInButton = findViewById(R.id.logInButton);

        logInButton.setOnClickListener(this);
        click.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.logInButton:
                Intent i = new Intent(this, Welcome.class);
                startActivity(i);
                break;

            case R.id.clickView:
                Intent u = new Intent(this, SignUp.class);
                startActivity(u);
                break;

            default:
                break;
        }
    }

    public void insertIntoDatabase(String name, String password, int score)
    {
        mydb.insertPlayer(name, password, score);
    }

    public ArrayList<Players> returnPlayers() {return mydb.getPlayers();} // επιστρέφει μια ArrayList με αντικείμενα Players
}