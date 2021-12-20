package com.example.tomcat_projekt.servlets;

import com.example.tomcat_projekt.database.NotesDatabase;
import com.example.tomcat_projekt.database.UserDatabase;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet
public class AppendToNotesServlet extends ServletTemplate{
    public static UserDatabase userDB = new UserDatabase();
    public static NotesDatabase notesDB = new NotesDatabase();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("usermail");
        String password = request.getParameter("userpass");
        boolean isSuccess = checkIfValid(email, password);
        request.setAttribute("isLoginGranted", isSuccess);
        RequestDispatcher d = request.getRequestDispatcher("/Welcome.jsp");
        d.forward(request, response);
    }
}
