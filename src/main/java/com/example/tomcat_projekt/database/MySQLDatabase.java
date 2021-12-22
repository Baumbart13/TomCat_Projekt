package com.example.tomcat_projekt.database;

import com.example.tomcat_projekt.models.Credentials;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;

public abstract class MySQLDatabase extends Database{
    public static String _CREDENTIALS_RELATIVE_PATH(){
        var t = new StringBuilder(System.getProperty("user.home"));
        var sep = new StringBuilder(System.getProperty("file.separator"));
        t.append(sep);
        t.append("res");
        t.append(sep);
        t.append("credentials");
        t.append(sep);
        t.append("database.csv");
        return t.toString();
    }
    public MySQLDatabase(String hostname, String user, String password, String database) {
        super(hostname, user, password, database);

    }

    public MySQLDatabase(){
        this(
                Credentials.loadDatabase(_CREDENTIALS_RELATIVE_PATH()).host(),
                Credentials.loadDatabase(_CREDENTIALS_RELATIVE_PATH()).user(),
                Credentials.loadDatabase(_CREDENTIALS_RELATIVE_PATH()).password(),
                Credentials.loadDatabase(_CREDENTIALS_RELATIVE_PATH()).database());
    }

    @Override
    public void connect() throws SQLException{
        if(connection != null){
            if(!connection.isClosed()){
                logger.log(Level.WARNING, "Connection already opened");
            }
        }

        var conString = String.format("jdbc:mysql://%s/%s?useUnicode=true&characterEncoding=utf8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC",
                hostname, database);
        connection = DriverManager.getConnection(
                conString,
                username, password);
        return;
    }

    @Override
    public void disconnect() throws SQLException{
        if(connection == null || connection.isClosed()){
            logger.log(Level.INFO, "Connection already closed.");
            return;
        }

        connection.close();
        connection = null;
    }
}
