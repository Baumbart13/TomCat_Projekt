package com.example.tomcat_projekt.servlets;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "RegisterServlet", value = "/RegisterServlet")
public class RegisterServlet extends ServletTemplate {
    DatabaseManager dbm = new DatabaseManager();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("usermail");
        String password = request.getParameter("userpass");
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String username = request.getParameter("username");
        boolean isSuccess = Register(email, password);
        RequestDispatcher d = request.getRequestDispatcher("/Login.jsp");
        d.forward(request, response);
    }

    private boolean Register(String email, String password) {
        try{
            //return dbm.registerUser(email, password);
        }catch(Exception ex){
            System.out(ex);
        }
        return false;
    }
}
