package com.example.tomcat_projekt.models;

import java.time.LocalDateTime;

public class Note {
    protected User user;
    protected LocalDateTime note_timestamp;
    protected String note_message;

    public Note(User user, LocalDateTime note_timestamp, String note_message) {
        this.setUser(user);
        this.setNote_timestamp(note_timestamp);
        this.setNote_message(note_message);
    }

    public Note(){
        this(new User(), LocalDateTime.now(), "");
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getNote_timestamp() {
        return note_timestamp;
    }

    public void setNote_timestamp(LocalDateTime note_timestamp) {
        this.note_timestamp = note_timestamp;
    }

    public String getNote_message() {
        return note_message;
    }

    public void setNote_message(String note_message) {
        this.note_message = note_message;
    }
}
