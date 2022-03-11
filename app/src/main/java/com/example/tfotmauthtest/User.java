package com.example.tfotmauthtest;

public class User {
    private String username;
  //  private String score;
    private String pfp;



    public User() {
    }

    public User(String username,  String pfp/*,RestCat category*/) {
        this.username = username;
    //    this.score = score;
        this.pfp = pfp;
        // this.category = category;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = name;
    }

  /*  public String getScore() {
        return score;
    }

    public void setDescription(String description) {
        this.score = score;
    }
    */


    public String getPfp() {
        return pfp;
    }

    public void setPfp(String photo) {
        this.pfp = photo;
    }
}
