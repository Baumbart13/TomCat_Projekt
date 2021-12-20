package com.example.tomcat_projekt.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;

public class User {
    protected String email;
    protected String forename;
    protected String lastname;
    protected String username;
    protected String password;
    protected LinkedList<Note> notes;

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
        this.email = email;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LinkedList<Note> getNotes(){
        return notes;
    }
}
