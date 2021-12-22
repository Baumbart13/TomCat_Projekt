package com.example.tomcat_projekt.servlets;

import com.example.tomcat_projekt.database.NotesDatabase;
import com.example.tomcat_projekt.database.UserDatabase;
import com.example.tomcat_projekt.services.LoginService;
import com.example.tomcat_projekt.services.NotesService;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "GetAllNotesServlet", value = "/GetAllNotesServlet")
public class GetAllNotesServlet extends ServletTemplate{
    protected static NotesDatabase db = null;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("Welcome.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(db == null){
            db = new NotesDatabase();
        }

        var session = request.getSession();
        var email = (String)session.getAttribute("usermail");
        var username = (String)session.getAttribute("username");

        // get all sql-data from SQL->notes_user
        var l = NotesService.getInstance().getAllNotes(db, "", username);
        var gson = new GsonBuilder().serializeNulls().create();
        var json = gson.toJson(l);
        // send all data to website
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        var out = response.getWriter();
        out.append(json);
        out.flush();
    }
}
