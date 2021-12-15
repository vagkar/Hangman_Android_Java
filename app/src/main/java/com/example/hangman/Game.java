package com.example.hangman;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Game extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {


    //variables frontend
    private EditText letterInput = null;
    private Button ngButton = null;
    private Button menuButton = null;
    private TextView usedLettersText = null;
    private ImageView hangMan = null;
    private TextView wordText = null;
    private TextView categoryText = null;
    private ConstraintLayout gameLayout = null;

    //variables backend
    private String selectedCategory;
    private String hiddenWord;
    private String displayedWord;
    private char[] displayedWordArray;
    private ArrayList<Word> words = new ArrayList<>();
    private int faultsLetters;
    private String lettersTried;
    private int lettersRevealed;
    private Bundle bundle = null;
    private String userName = null;
    private ArrayList<Players> players = new ArrayList<>();
    private int score;

    private Database db = LogIn.db;

    // αποκαλύπτει το γράμμα μέσα στην λέξη
    public void revealLetter(char l) {
        if (!lettersTried.contains(Character.toString(l))) {
            for (int i = 0; i < hiddenWord.length(); i++) {
                if (l == hiddenWord.charAt(i)) {
                    displayedWordArray[i] = l;
                    lettersRevealed++;
                }
            }
        }
        displayedWord = Arrays.toString(displayedWordArray).trim();

        /*int index = hiddenWord.indexOf(l);

        while (index >= 0) {
            displayedWordArray[index] = hiddenWord.charAt(index);
            index = hiddenWord.indexOf(l, index + 1);
        }*/
    }

    private void displayedWordOnScreen() {
        String word = "";
        for (char ch : displayedWordArray) {
            word += ch + " ";
        }
        wordText.setText(word);
    }


    public void initializeGame() {
        categoryText.setText(selectedCategory);
        faultsLetters = 0;
        lettersRevealed = 0;

        ArrayList<String> categoryWords = new ArrayList<>();
        for (int i = 0; i < words.size(); i++) {
            if (words.get(i).getCategory().equals(selectedCategory)) {
                categoryWords.add(words.get(i).getPhrase());
            }
        }
        Collections.shuffle(categoryWords);
        hiddenWord = categoryWords.get(0);
        categoryWords.remove(0);

        displayedWordArray = hiddenWord.toCharArray();

        for (int i = 0; i < displayedWordArray.length; i++) {
            displayedWordArray[i] = '_';
        }

        displayedWord = Arrays.toString(displayedWordArray);

        displayedWordOnScreen();

        //clear input
        letterInput.setText("");

        lettersTried = " ";

        usedLettersText.setText(R.string.letters_tried);

    }




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        gameLayout = (ConstraintLayout) findViewById(R.id.game_layout);

        categoryText = findViewById(R.id.txtCategory);
        wordText = findViewById(R.id.txtWord);
        usedLettersText = findViewById(R.id.txtUsedLetters);
        hangMan = findViewById(R.id.hangmanimg);

        letterInput = findViewById(R.id.edtLetter);
        menuButton = findViewById(R.id.menu_button);
        ngButton = findViewById(R.id.ngButton);
        bundle = getIntent().getExtras();
        userName = bundle.getString("name");
        players = db.getPlayers();

        for(int i=0; i < players.size(); i++)
        {
            if(userName.equals(players.get(i).getName()))
                score = players.get(i).getScore();
        }

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Game.super.onBackPressed();
            }
        });


        //Input word from file
        InputStream inputStream = null;
        Scanner scanner = null;

        try {
            inputStream = getAssets().open("words_file.txt");
            scanner = new Scanner(inputStream);
            while (scanner.hasNext()) {
                Word w = new Word(scanner.next(), scanner.next());
                words.add(w);
            }
        } catch (IOException e) {
            Toast.makeText(Game.this,
                    e.getClass().getSimpleName() + ":" + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        } finally {
            if (scanner != null) {
                scanner.close();
            }

            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                Toast.makeText(Game.this,
                        e.getClass().getSimpleName() + ":" + e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        }
        


        letterInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    try {
                        checkLetter(s.charAt(0));
                    } catch (Exception e) {
                        Toast.makeText(Game.this, "Select a category to start the game", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


    public boolean containedLetter(String s, char l) {
        boolean cLettter = false;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == l) {
                cLettter = true;
            }
        }
        return cLettter;
    }


    public void checkLetter(char l) {

        //check if input is a letter
        if ((l >= 'a' && l <= 'z') || (l >= 'A' && l <= 'Z')) {
            //check for caps
            if (l >= 'A' && l <= 'Z')
                l = Character.toLowerCase(l);
        }

        if (containedLetter(hiddenWord, l)) {
            //if (!containedLetter(displayedWord, l)) {
                //reveal the letter
                revealLetter(l);

                //update game word
                displayedWordOnScreen();

            //}
            //win check
            if (hiddenWord.length() == lettersRevealed) {
                categoryText.setText("You Won! Press New Game and select category");
            }
        } else {
            //increase the number of fault letters and show on the screen next hangman image
            if (!lettersTried.contains(Character.toString(l))) {
                increaseAndDisplayTries();
                score += 10;
                db.updateScore(userName, score);
            }

            //lose check
            if (faultsLetters == 6) {
                categoryText.setText("You Lost Press New Game and select category");
                wordText.setText(hiddenWord);
            }
        }

        //display used letters
        if (lettersTried.indexOf(l) < 0) {
            lettersTried += l + ",";
            String msg = "Letters Tried: " + lettersTried;
            usedLettersText.setText(msg);
        }
    }

    public void increaseAndDisplayTries() {
        switch (faultsLetters) {
            case 1:
                hangMan.setImageResource(R.drawable.hangman2);
                faultsLetters++;
                break;

            case 2:
                hangMan.setImageResource(R.drawable.hangman3);
                faultsLetters++;
                break;

            case 3:
                hangMan.setImageResource(R.drawable.hangman4);
                faultsLetters++;
                break;

            case 4:
                hangMan.setImageResource(R.drawable.hangman5);
                faultsLetters++;
                break;

            case 5:
                hangMan.setImageResource(R.drawable.hangman6);
                faultsLetters++;
                break;

            default:
                hangMan.setImageResource(R.drawable.hangman1);
                faultsLetters++;
                break;

        }
    }



    public void showCategories(View v) {
        PopupMenu popupCategories = new PopupMenu(this, v);
        popupCategories.setOnMenuItemClickListener(this);
        popupCategories.inflate(R.menu.categories_menu);
        popupCategories.show();
    }

    public void resetGame(View v) {
        hangMan.setImageResource(R.drawable.hangman0);
        initializeGame();
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.geography_item:
                selectedCategory = "geography";
                resetGame(gameLayout);
                return true;
            case R.id.animals_item:
                selectedCategory = "animals";
                resetGame(gameLayout);
                return true;
            case R.id.foods_item:
                selectedCategory = "foods";
                resetGame(gameLayout);
                return true;
            case R.id.planets_item:
                selectedCategory = "planets";
                resetGame(gameLayout);
                return true;
            default:
                return false;
        }
    }
}
