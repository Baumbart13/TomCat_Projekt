package com.example.tomcat_projekt.models;

public class Error {
    public static void logf(String s, Object ... o){
        System.out.printf(s, o);
    }
    public static void loglnf(String s, Object ... o){
        logf(s.concat("%n"), o);
    }
    public static void errf(String s, Object ... o){
        System.err.printf(s, o);
    }
    public static void errlnf(String s, Object ... o){
        errf(s.concat("%n"), o);
    }
}
