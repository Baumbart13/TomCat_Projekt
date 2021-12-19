package com.example.tomcat_projekt.services;

public class LoginService {
    private static LoginService instance = null;

    private LoginService(){ }

    public static LoginService getInstance(){
        if(instance == null){
            instance = new LoginService();
        }
        return instance;
    }
}
