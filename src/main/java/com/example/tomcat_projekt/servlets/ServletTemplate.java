package com.example.tomcat_projekt.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Need to add <code>WebServlet</code> Annotation here
 */
public class ServletTemplate extends HttpServlet {

    public ServletTemplate(){
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        throw new UnsupportedOperationException();
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        throw new UnsupportedOperationException();
    }
}
