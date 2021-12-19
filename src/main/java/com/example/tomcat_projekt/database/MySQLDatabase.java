package com.example.tomcat_projekt.database;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;

public abstract class MySQLDatabase extends Database{
    public MySQLDatabase(String hostname, String user, String password, String database) {
        super(hostname, user, password, database);
    }

    @Override
    public void connect() throws SQLException{
        if(connection != null){
            if(!connection.isClosed()){
                logger.log(Level.WARNING, "Connection already opened");
            }
            return;
        }

        connection = DriverManager.getConnection(String.format(
                "jdbc:mysql://%s%s", hostname, database),
                username,
                password
        );
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
