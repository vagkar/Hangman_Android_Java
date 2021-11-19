package com.example.hangman;

public class Word {
    private String phrase;
    private String category;

    public Word(String phrase, String category) {
        setPhrase(phrase);
        setCategory(category);
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
