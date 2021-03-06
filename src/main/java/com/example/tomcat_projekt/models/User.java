package com.example.tomcat_projekt.models;

import java.util.LinkedList;

public class User {
    protected String email;
    protected String forename;
    protected String lastname;
    protected String username;
    protected String password;
    protected transient LinkedList<Note> notes;
    public transient final static String AT = "@";
    public transient final static char[] NEEDED_OPERATORS = {'!', '€', '§', '$', '&'};

    public User(
            String email,
            String forename,
            String lastname,
            String username,
            String password) {
        this.email = email;
        this.forename = forename;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
    }

    public static boolean containsAnyNeededOperators(String toBeChecked){
        for(var c : NEEDED_OPERATORS){
            var str = "" + c;
            if(toBeChecked.contains(str)){
                return true;
            }
        }
        return false;
    }

    public User(){
        this("", "", "", "", "");
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if(email.contains(AT)){
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
        if(password.length() >= 8 && containsAnyNeededOperators(password))
        {
            this.password = password;
        }
    }

    public LinkedList<Note> getNotes(){
        return notes;
    }
}
