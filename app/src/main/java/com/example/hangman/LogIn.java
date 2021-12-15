package com.example.hangman;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class LogIn extends AppCompatActivity implements View.OnClickListener {
    private EditText name = null;
    private EditText password = null;
    private Button logInButton = null;
    private TextView clickView = null;
    private ImageView show_hide_img = null;
    private static int count = 0;

    public static Database db = null;

    public void show_hide_password() {
        count++;

        if (count %2 == 0)
            password.setTransformationMethod(new PasswordTransformationMethod());
        else
            password.setTransformationMethod(null);
    }

    public boolean checkNamePasswordCorrectness(String name, String password) {
        boolean check = db.checkLogin(name, password);
        return check;
    }

    public void logInPLayer(String name, String password) {
        if(name.equals("") || password.equals("")) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage("PLease enter all required fields");
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            alert.create().show();
        } else {
            boolean check = checkNamePasswordCorrectness(name, password);

            if(check) {
                Intent i = new Intent(this, Welcome.class);
                i.putExtra("name", name);
                startActivity(i);
            } else {
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setMessage("Sorry but one or more fields are incorrect");
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alert.create().show();
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);
        db = new Database(this, "players.db");

        name = findViewById(R.id.nameText);
        password = findViewById(R.id.passwordText);
        logInButton = findViewById(R.id.logInButton);
        clickView = findViewById(R.id.clickView);
        show_hide_img = findViewById(R.id.showHidePassword);

        logInButton.setOnClickListener(this);
        clickView.setOnClickListener(this);
        show_hide_img.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        name.setText("");
        password.setText("");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.logInButton:
                String nameIn = name.getText().toString();
                String passwordIn = password.getText().toString();
                logInPLayer(nameIn, passwordIn);
                break;

            case R.id.clickView:
                Intent u = new Intent(this, SignUp.class);
                startActivity(u);
                break;

            case R.id.showHidePassword:
                show_hide_password();
                break;

            default:
                break;
        }
    }

    public void insertIntoDatabase(String name, String password, int score) {
        db.insertPlayer(name, password, score);
    }

    public ArrayList<Players> returnPlayers() {return db.getPlayers();} // επιστρέφει μια ArrayList με αντικείμενα Players
}