package com.example.tomcat_projekt.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class Database {
    protected static Logger logger = Logger.getLogger(Database.class.getName());
    protected Connection connection = null;
    protected String hostname = "localhost",
                     username = "root",
                     password = "SHW_Destroyer",
                     database = "swpwebloginsignup";

    protected static String createConnectionString(
            String hostname,
            String database,
            String user,
            String pass){
        return String.format("jdbc:mysql://%s/%s?user=%s&password=%s?serverTimezone=UTC?autoReconnect=true",
                hostname, database, user, pass);
    }

    public Database(String hostname, String user, String password, String database) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE, "\"JDBC is not installed. Please first install the driver and try to run this program afterwards.");
            System.exit(-1);
        }

        if (hostname.length() < 1 ||
                user.length() < 1 ||
                password.length() < 1 ||
                database.length() < 1) {
            hostname = user = password = database = "FATAL ERROR: Wrong credentials!";
        }

        this.hostname = hostname;
        this.username = user;
        this.password = password;
        this.database = database;
    }

    public abstract void connect() throws SQLException;
    public abstract void disconnect() throws SQLException;
    public abstract void createDatabase(String database) throws SQLException;

    public void creadeDatabase() throws SQLException{
        this.createDatabase(database);
    }

    @Override
    public String toString(){
        return String.format("Hostname:%s;User:%s;Password:%s;Database%s",
                hostname, username, password, database);
    }

    @Override
    public abstract Database clone();

    public String getHostname() {
        return hostname;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDatabase() {
        return database;
    }
}
