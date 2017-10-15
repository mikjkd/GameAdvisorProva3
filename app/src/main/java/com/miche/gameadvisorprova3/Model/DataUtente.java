package com.miche.gameadvisorprova3.Model;

import com.google.firebase.auth.FirebaseUser;

import java.io.Serializable;

/**
 * Created by miche on 15/10/2017.
 */

public class DataUtente implements Serializable {
    private String Username;
    private String Password;
    private String Email;
    private boolean Autenticated;

    public DataUtente(){}

    public DataUtente(String username, String password, String email, boolean autenticated) {
        Username = username;
        Password = password;
        Email = email;
        Autenticated = autenticated;
    }

    public String getUsername() { return Username;}

    public void setUsername(String username) {Username = username;}

    public String getPassword() { return Password;}

    public void setPassword(String password) { Password = password;}

    public String getEmail() { return Email; }

    public void setEmail(String email) {Email = email;}

    public boolean isAutenticated() {return Autenticated;}

    public void setAutenticated(boolean autenticated) {Autenticated = autenticated;}
}