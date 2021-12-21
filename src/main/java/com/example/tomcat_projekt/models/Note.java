package com.example.tomcat_projekt.models;

public class Note {
    protected User user;
    protected int note_index;
    protected String note_message;

    public Note(User user, int note_index, String note_message) {
        this.setUser(user);
        this.setNote_index(note_index);
        this.setNote_message(note_message);
    }

    public Note(){
        this(new User(), 0, "");
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getNote_index() {
        return note_index;
    }

    public void setNote_index(int note_index) {
        this.note_index = note_index;
    }

    public String getNote_message() {
        return note_message;
    }

    public void setNote_message(String note_message) {
        this.note_message = note_message;
    }
}
