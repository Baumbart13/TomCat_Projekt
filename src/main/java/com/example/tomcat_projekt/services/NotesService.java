package com.example.tomcat_projekt.services;

import com.example.tomcat_projekt.database.NotesDatabase;
import com.example.tomcat_projekt.database.UserDatabase;
import com.example.tomcat_projekt.models.Note;
import com.example.tomcat_projekt.models.User;

import javax.servlet.ServletException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class NotesService {
    private static NotesService instance = null;

    private NotesService(){ }

    public static NotesService getInstance(){
        if(instance == null){
            instance = new NotesService();
        }
        return instance;
    }

    public List<Note> getAllNotes(NotesDatabase db, String email, String username) throws ServletException {
        var u = new User();
        u.setEmail(email);
        u.setUsername(username);
        return getAllNotes(db, u);
    }

    public List<Note> getAllNotes(NotesDatabase db, User user) throws ServletException {
        LinkedList<Note> l = new LinkedList<Note>();
        try{
            db.connect();
            l = db.getAllNotes(user);
            db.disconnect();
        }catch (SQLException e){
            throw new ServletException(e.getMessage());
        }
        return l;
    }
}
