package com.example.hangman;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignUp extends AppCompatActivity implements View.OnClickListener
{
    private TextView back = null;
    private EditText name = null, password = null;
    private Button signUpButton = null;
    private Database db = LogIn.mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        back = findViewById(R.id.signUpBackText);
        name = findViewById(R.id.signUpNameText);
        password = findViewById(R.id.signUpPasswordText);
        signUpButton = findViewById(R.id.signUpButton);

        back.setOnClickListener(this);
        signUpButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.signUpBackText:
                super.onBackPressed();
                break;

            case R.id.signUpButton:
                String nameInput = name.getText().toString();
                String passwordInput = password.getText().toString();
                db.insertPlayer(nameInput, passwordInput, 0);
                Toast.makeText(getApplicationContext(), "Signed up successfully", Toast.LENGTH_LONG).show();
                break;

            default:
                break;
        }
    }
}