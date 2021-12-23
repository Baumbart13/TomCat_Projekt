package com.example.tomcat_projekt.servlets;

import com.example.tomcat_projekt.database.UserDatabase;
import com.example.tomcat_projekt.models.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
        RequestDispatcher d;
        if(isSuccess){
            d = request.getRequestDispatcher("/Login.jsp");
        }else{
            if(email == null || email.length() < 5 || !email.contains(User.AT) || email.indexOf(".", email.indexOf(User.AT)) <= email.indexOf(User.AT)){
                request.setAttribute("error", "Please provide a correct email");
            }else if(password == null || !User.containsAnyNeededOperators(password) || password.length() < 8){
                request.setAttribute("error", "Your password needs to provide at least 8 characters and at least of the following characters: " + new String(User.NEEDED_OPERATORS));
            }else if(firstname == null || firstname.length() < 4){
                request.setAttribute("error", "Your first name needs to be at least 4 characters long");
            }else if(lastname == null || lastname.length() < 4){
                request.setAttribute("error", "Your last name needs to be at least 4 characters long");
            }else if(username == null || username.length() < 3){
                request.setAttribute("error", "Your username needs to be at least 3 characters long");
            }
            d = request.getRequestDispatcher("/Register.jsp");
        }

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
