package com.example.tomcat_projekt.services;

import com.example.tomcat_projekt.database.UserDatabase;
import com.example.tomcat_projekt.models.User;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class LoginService {
    private static LoginService instance = null;

    private LoginService(){ }

    public static LoginService getInstance(){
        if(instance == null){
            instance = new LoginService();
        }
        return instance;
    }

    public List<User> getAllUsers(UserDatabase db) throws ServletException{
        LinkedList<User> l = new LinkedList<User>();
        try{
            db.connect();
            l = db.getAllUser();
            db.disconnect();
        }catch (SQLException e){
            throw new ServletException(e.getMessage());
        }
        return l;
    }
}
