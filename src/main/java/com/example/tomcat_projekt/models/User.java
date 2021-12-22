package com.example.tomcat_projekt.models;

import java.util.Arrays;
import java.util.LinkedList;

public class User {
    protected String email;
    protected String forename;
    protected String lastname;
    protected String username;
    protected String password;
    protected LinkedList<Note> notes;
    protected String at = "@";
    protected char[] needed_operators = {'!', '€', '§', '$', '&'};
    public User(
            String email,
            String forename,
            String lastname,
            String username,
            String password) {
        this.setEmail(email);
        this.setForename(forename);
        this.setLastname(lastname);
        this.setUsername(username);
        this.setPassword(password);
    }

    public User(){
        this("", "", "", "", "");
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if(email.contains(at)){
            this.email = email;
        }
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        if(forename.length() >= 3)
        {
            this.forename = forename;
        }
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        if(lastname.length() >= 3)
        {
            this.lastname = lastname;
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if(username.length() > 3)
        {
            this.username = username;
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if(password.length() >= 8 && password.contains(Arrays.toString(needed_operators)))
        {
            this.password = password;
        }
    }

    public LinkedList<Note> getNotes(){
        return notes;
    }
}
