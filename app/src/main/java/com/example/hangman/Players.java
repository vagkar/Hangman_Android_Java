package com.example.hangman;

public class Players
{
    private String name;
    private String password;
    private int score;

    public Players(String name, String password, int score)
    {
        this.name = name;
        this.password = password;
        this.score = score;
    }

    public Players(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public int getScore()
    {
        return score;
    }

    public void setScore(int score)
    {
        this.score = score;
    }

    public String τoString()
    {
        return "Name: " + getName() + " , " + "Score: " + getScore();
    }
}
