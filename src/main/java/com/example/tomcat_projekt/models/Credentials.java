package com.example.tomcat_projekt.models;
import java.io.*;

import static com.example.tomcat_projekt.models.Error.errf;
import static com.example.tomcat_projekt.models.Error.errlnf;

public class Credentials {
    private static BufferedReader reader = null;
    public static final String csvSeparator = ",";
    public static final String lineSeparator = System.lineSeparator();
    protected Credentials(){System.err.println("How did you call this? Go away!!"); System.exit(-1);}

    public static class DatabaseCredentials{
        private String mUser;
        private String mPassword;
        private String mDatabase;
        private String mHost;
        public DatabaseCredentials(String host, String user, String pass, String database){
            mUser = user;
            mPassword = pass;
            mDatabase = database;
            mHost = host;
        }
        public String user(){return mUser;}
        public String password(){return mPassword;}
        public String database(){return mDatabase;}
        public String host(){return mHost;}
    }
    private static BufferedReader initReader(String file){
        if(reader != null)
            return reader;
        BufferedReader out = null;
        try {
            out = new BufferedReader(new FileReader(file));
        }catch(FileNotFoundException e){
            errlnf("File \"%s\" not found", new File(file).getAbsolutePath());
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
        return out;
    }

    public static DatabaseCredentials loadDatabase(boolean inProduction){
        var file = inProduction ? "" : "database.csv";
        return loadDatabase(file);
    }

    public static DatabaseCredentials loadDatabase(String file){
        var user = "root";
        var pass = "SHW_Destroyer02";
        var database = "notes";
        var host = "localhost";

        var out = new DatabaseCredentials(host,user,pass,database);

        try{
            reader = initReader(file);
            if(!reader.readLine().contains("hostname,user,password,database")){
                throw new IOException("Corrupted database file!");
            }
            var splittedLine = reader.readLine().trim().split(csvSeparator);
            if(splittedLine.length != 4){
                throw new IOException("Corrupted database file!");
            }
            out = new DatabaseCredentials(splittedLine[0], splittedLine[1], splittedLine[2], splittedLine[3]);
        }catch(FileNotFoundException e){
            errlnf("File \"%s\" not found", new File(file).getAbsolutePath());
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }catch (NullPointerException e){
            errf("Corrupted database file!");
            e.printStackTrace();
        }
        return out;
    }
}