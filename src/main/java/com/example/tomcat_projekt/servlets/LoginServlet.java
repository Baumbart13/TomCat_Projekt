package com.example.tomcat_projekt.servlets;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import com.example.tomcat_projekt.database.UserDatabase;
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
        String password = request.getParameter("userpass");
        boolean isSuccess = checkIfValid(email, password);

        var session = request.getSession();
        session.setAttribute("isLoginGranted", isSuccess);
        RequestDispatcher d = request.getRequestDispatcher("/Welcome.jsp");
        d.forward(request, response);
    }

    private boolean checkIfValid(String email, String password) {
        boolean success = false;
        try {
            dbm.connect();
            success = dbm.canLogin(email, password);
            dbm.disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }
}
