package com.example.tomcat_projekt.servlets;

import com.example.tomcat_projekt.database.UserDatabase;
import com.example.tomcat_projekt.models.User;
import com.example.tomcat_projekt.services.LoginService;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "GetAllUsersServlet", value = "/GetAllUsersServlet")
public class GetAllUsersServlet extends ServletTemplate{
    public static UserDatabase db = null;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(db == null){
            db = new UserDatabase();
        }

        // get all sql-data from SQL->notes_user
        var l = LoginService.getInstance().getAllUsers(db);
        var gson = new Gson();
        var json = gson.toJson(l);
        // send all data to website
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        var out = response.getWriter();
        out.append(json);
        out.flush();
    }
    /*public static void SelectAllUsers(){
        String sqlCMD = "SELECT * FROM TABLENAME;";
        try{
            con = DriverManager.getConnection("jdbc:mysql://"+hostname+"/"+dbName+"?user="+userName+"&password="+password);

        }catch(Exception ex){
            System.out(ex);
        }
    }*/
}
