package com.example.tomcat_projekt.services;

public class RegisterService {
    private static RegisterService instance = null;
    private RegisterService(){}
    public static RegisterService getInstance(){
        if(instance == null){
            instance = new RegisterService();
        }
        return instance;
    }
}
