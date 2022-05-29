package com.example.projectf2f3.entitades;

import java.io.Serializable;

public class GameInfo implements Serializable {

    private Integer Id;
    private String User;
    private Integer Player1;
    private Integer Player2;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

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



    public GameInfo(Integer id, String user, Integer player1, Integer player2) {
        Id = id;
        User = user;
        Player1 = player1;
        Player2 = player2;
    }

    public GameInfo() {

    }


}

