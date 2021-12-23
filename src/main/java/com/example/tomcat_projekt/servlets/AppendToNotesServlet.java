package com.example.tomcat_projekt.servlets;

import com.example.tomcat_projekt.database.NotesDatabase;
import com.example.tomcat_projekt.database.UserDatabase;
import com.example.tomcat_projekt.models.Note;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

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
        var note = new Note();
        note.setNote_message(request.getParameter("new_note"));
        var session = request.getSession();
        try {
            notesDB.insertNote(
                    note.getNote_message(),
                    (String)session.getAttribute("usermail"),
                    (String)session.getAttribute("username"));
        } catch (SQLException e) {
            throw new ServletException(e.getMessage());
        }

        var d = request.getRequestDispatcher("Welcome.jsp");
        d.forward(request, response);
    }
}
