package com.example.tfotmauthtest;

public class User {
    private String username;
    private int score;
    private String pfp;

    public User() {
    }

    public User(String username,  String pfp,int score) {
        this.username = username;
        this.score = 0;
        this.pfp = pfp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getPfp() {
        return pfp;
    }

    public void setPfp(String photo) {
        this.pfp = photo;
    }
}
