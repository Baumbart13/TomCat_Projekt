package com.example.tomcat_projekt.servlets;

import com.example.tomcat_projekt.database.NotesDatabase;
import com.example.tomcat_projekt.database.UserDatabase;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AppendToServlet", value = "/AppendToServlet")
public class AppendToNotesServlet extends ServletTemplate{
    public static UserDatabase userDB = new UserDatabase();
    public static NotesDatabase notesDB = new NotesDatabase();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(userDB == null) {
            userDB = new UserDatabase();
        }
        if(notesDB == null) {
            notesDB = new NotesDatabase();
        }
    }
}
