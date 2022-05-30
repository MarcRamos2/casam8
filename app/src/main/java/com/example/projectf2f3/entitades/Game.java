package com.example.projectf2f3.entitades;

import java.io.Serializable;

public class Game implements Serializable {

    private String User;
    private Integer Player1;
    private Integer Player2;

    // constructor

    public Game(String user, Integer player1, Integer player2) {
        User = user;
        Player1 = player1;
        Player2 = player2;
    }

    public Game() {

    }

    // getter i setter
    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }

    public Integer getPlayer1() {
        return Player1;
    }

    public void setPlayer1(Integer player1) {
        Player1 = player1;
    }

    public Integer getPlayer2() {
        return Player2;
    }

    public void setPlayer2(Integer player2) {
        Player2 = player2;
    }





}

