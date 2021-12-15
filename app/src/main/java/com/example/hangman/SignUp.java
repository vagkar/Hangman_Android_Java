package com.example.hangman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SignUp extends AppCompatActivity implements View.OnClickListener
{
    private TextView back = null;
    private EditText name = null;
    private EditText password = null;
    private Button signUpButton = null;
    private ImageView showHidePass = null;
    private static int count = 0;
    private Database db = LogIn.db;

    public void showHidePassword() {
        count++;

        if (count %2 == 0)
            password.setTransformationMethod(new PasswordTransformationMethod());
        else
            password.setTransformationMethod(null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        back = findViewById(R.id.signUpBackText);
        name = findViewById(R.id.signUpNameText);
        password = findViewById(R.id.signUpPasswordText);
        signUpButton = findViewById(R.id.signUpButton);
        showHidePass = findViewById(R.id.showHideSignUpPassword);

        back.setOnClickListener(this);
        signUpButton.setOnClickListener(this);
        showHidePass.setOnClickListener(this);
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
                Intent i = new Intent(this, Welcome.class);
                i.putExtra("name", name.getText().toString());
                startActivity(i);
                break;

            case R.id.showHideSignUpPassword:
                showHidePassword();
                break;

            default:
                break;
        }
    }
}