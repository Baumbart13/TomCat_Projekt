package com.example.tomcat_projekt.servlets;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "LoginServlet", value = "/LoginServlet")
public class LoginServlet extends ServletTemplate {
    DatabaseManager dbm = new DatabaseManager();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("usermail");
        String password = request.getParameter("userpass");
        boolean isSuccess = checkIfValid(email, password);
        request.setAttribute("isLoginGranted", isSuccess);
        RequestDispatcher d = request.getRequestDispatcher("/Welcome.jsp");
        d.forward(request, response);
    }

    private boolean checkIfValid(String email, String password) {
        try {
            boolean isUserValid = dbm.canLogin(email, password);
            return isUserValid;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
