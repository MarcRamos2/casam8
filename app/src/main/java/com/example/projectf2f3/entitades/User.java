package com.example.projectf2f3.entitades;

import java.io.Serializable;

// Usuario que li guardem el nom, correu , i contrasenya
public class User implements Serializable {

    private String user;
    private String email;
    private String password;

    // Constructor
    public User(String user, String email, String password) {
        this.user = user;
        this.email = email;
        this.password = password;
    }

     public User() {

     }

     // getter i setter

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
