package com.example.tomcat_projekt.servlets;

import com.example.tomcat_projekt.database.UserDatabase;
import com.example.tomcat_projekt.models.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
@WebServlet(name = "LoginServlet", value = "/LoginServlet")

public class LoginServlet extends ServletTemplate {
    public static UserDatabase dbm = null;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("Login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(dbm == null) {
            dbm = new UserDatabase();
        }
        String email = request.getParameter("usermail");
        String username = request.getParameter("usermail");
        String password = request.getParameter("userpass");
        boolean isSuccess = checkIfValid(email, password);

        RequestDispatcher d;
        var session = request.getSession();
        if(isSuccess){
            var u = getUser(email, "");
            session.setAttribute("usermail", u.getEmail());
            session.setAttribute("username", u.getUsername());
            session.setAttribute("isLoginGranted", true);
            d = request.getRequestDispatcher("/Welcome.jsp");
        }else{
            request.setAttribute("error", "Please check your email and password if both are valid");
            d = request.getRequestDispatcher("/Login.jsp");
        }
        d.forward(request, response);
    }

    private boolean checkIfValid(String email, String password) throws ServletException {
        boolean success = false;
        try {
            dbm.connect();
            var username = email; // because it comes from the same field, where
                                         // login is available through email and username
            success = dbm.canLogin(email, username, password);
            dbm.disconnect();
        } catch (SQLException e) {
            throw new ServletException(e.getMessage());
        }
        return success;
    }

    private User getUser(String email, String username) throws ServletException{
        var u = new User();
        u.setEmail(email);
        u.setUsername(username);
        try{
            dbm.connect();
            u = dbm.getUser(u.getEmail(), u.getUsername());
            dbm.disconnect();
        }catch (SQLException e){
            throw new ServletException(e.getMessage());
        }
        return u;
    }
}
