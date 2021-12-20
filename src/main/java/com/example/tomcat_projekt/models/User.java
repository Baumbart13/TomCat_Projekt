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
    protected LocalDate birthday;
    protected LocalDateTime join_date;
    protected LinkedList<Note> notes;

    public User(
            String email,
            String forename,
            String lastname,
            String username,
            String password,
            LocalDate birthday,
            LocalDateTime join_date) {
        this.setEmail(email);
        this.setForename(forename);
        this.setLastname(lastname);
        this.setUsername(username);
        this.setPassword(password);
        this.setBirthday(birthday);
        this.setJoin_date(join_date);
    }

    public User(){
        this("", "", "", "", "", LocalDate.MIN, LocalDateTime.now());
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public LocalDateTime getJoin_date() {
        return join_date;
    }

    public void setJoin_date(LocalDateTime join_date) {
        this.join_date = join_date;
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
