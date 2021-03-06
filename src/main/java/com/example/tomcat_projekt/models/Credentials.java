package com.example.tomcat_projekt.models;
import java.io.*;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Credentials {
    protected static Logger logger = Logger.getLogger(Credentials.class.getName());
    private static BufferedReader reader = null;
    public static final String csvSeparator = ",";
    public static final String lineSeparator = System.lineSeparator();
    protected Credentials(){System.err.println("How did you call this? Go away!!"); System.exit(-1);}

    public static class DatabaseCredentials{
        private final String mUser;
        private final String mPassword;
        private final String mDatabase;
        private final String mHost;
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
            logger.log(Level.SEVERE,"File \"%s\" not found", new File(file).getAbsolutePath());
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
        return new DatabaseCredentials("localhost:3306", "root", "SHW_Destroyer02", "notes"); // TODO: export resources to tomcat-folder
        /*
        var out = new DatabaseCredentials("", "", "", "");
        var f = Path.of(file);
        if(!f.toFile().exists()){
            f.toFile().mkdirs();
        }
        logger.log(Level.INFO, f.toAbsolutePath().toString());

        try{
            reader = initReader(file); // TODO: export resources to tomcat-folder
            if(!reader.readLine().contains("hostname,user,password,database")){
                throw new IOException("Corrupted database file!");
            }
            var splittedLine = reader.readLine().trim().split(csvSeparator);
            if(splittedLine.length != 4){
                throw new IOException("Corrupted database file!");
            }
            out = new DatabaseCredentials(splittedLine[0], splittedLine[1], splittedLine[2], splittedLine[3]);
        }catch(FileNotFoundException e){
            logger.log(Level.SEVERE, "File \"%s\" not found", new File(file).getAbsolutePath());
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }catch (NullPointerException e){
            logger.log(Level.WARNING, "Corrupted database file!");
            e.printStackTrace();
        }
        return out;*/
    }
}