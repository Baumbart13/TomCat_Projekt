package com.example.tomcat_projekt.servlets;

import com.example.tomcat_projekt.database.UserDatabase;
import com.example.tomcat_projekt.models.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

@WebServlet(name = "RegisterServlet", value = "/RegisterServlet")
public class RegisterServlet extends ServletTemplate {
    public static UserDatabase dbm = null;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("Register.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(dbm == null) {
            dbm = new UserDatabase();
        }

        String email = request.getParameter("usermail");
        String password = request.getParameter("userpass");
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String username = request.getParameter("username");
        var user = new User(email, firstname, lastname, username, password);
        boolean isSuccess = Register(user);
        RequestDispatcher d = request.getRequestDispatcher("/Login.jsp");
        d.forward(request, response);
    }

    private boolean Register(User user) {
        boolean success = false;
        try {
            dbm.connect();
            success = dbm.insertUser(user);
            dbm.disconnect();
            //return dbm.registerUser(email, password);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return success;
    }
}
