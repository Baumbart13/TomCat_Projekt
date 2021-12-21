package com.example.tomcat_projekt.database;

import com.example.tomcat_projekt.models.Credentials;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;

public abstract class MySQLDatabase extends Database{
    public static final String _CREDENTIALS_RELATIVE_PATH = "res/credentials/database.csv";
    public MySQLDatabase(String hostname, String user, String password, String database) {
        super(hostname, user, password, database);
    }

    public MySQLDatabase(){
        this(
                Credentials.loadDatabase(_CREDENTIALS_RELATIVE_PATH).host(),
                Credentials.loadDatabase(_CREDENTIALS_RELATIVE_PATH).user(),
                Credentials.loadDatabase(_CREDENTIALS_RELATIVE_PATH).password(),
                Credentials.loadDatabase(_CREDENTIALS_RELATIVE_PATH).database());
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
