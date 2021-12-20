package com.example.tomcat_projekt.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;


public class NotesDatabase extends MySQLDatabase{
    public static final String _TABLE_NAME = "notes_notes";
    public static final String _DATABASE_NAME = "notes";

    public enum _TABLE_FIELDS {
        email_user,
        write_time,
        message
    }

    public NotesDatabase() {
        this("", "", "", _DATABASE_NAME);
    }

    public NotesDatabase(String hostname, String user, String pass, String database) {
        super(hostname, user, pass, database);
    }

    public NotesDatabase(NotesDatabase notesDB) {
        this(notesDB.hostname, notesDB.hostname, notesDB.password, notesDB.database);
    }

    @Override
    public Database clone() {
        return new NotesDatabase(this);
    }

    @Override
    public void createDatabase(String database) throws SQLException {
        var stmnt = connection.createStatement();
        stmnt.execute("CREATE DATABASE IF NOT EXISTS " + database.trim() + ";");
    }

    public void createDatabase() throws SQLException {
        this.createDatabase(database);
    }

    public void createTable() throws SQLException {
        this.createTable(_TABLE_NAME);
    }

    public void createTable(String tableName) throws SQLException {
        PreparedStatement stmnt;
        var sb = new StringBuilder();

        sb.append("CREATE TABLE IF NOT EXISTS %s (");
        sb.append("%s VARCHAR(40) NOT NULL,"); // email_user
        sb.append("%s TIMESTAMP NOT NULL,"); // write_time
        sb.append("%s VARCHAR(500),"); // message

        sb.append("PRIMARY KEY(%s),"); // write_time
        sb.append("FOREIGN KEY(%s) REFERENCES %s(%s);"); // email_user, User._TABLE_NAME, User._TABLE_FIELDS.email

        stmnt = connection.prepareStatement(String.format(sb.toString(),
                _TABLE_FIELDS.email_user.name(),
                _TABLE_FIELDS.write_time.name(),
                _TABLE_FIELDS.message.name(),
                // Primary Key
                _TABLE_FIELDS.write_time.name(),
                // Foreign Key
                _TABLE_FIELDS.email_user.name(),
                UserDatabase._TABLE_NAME,
                UserDatabase._TABLE_FIELDS.email.name()
        ));

        stmnt.execute();
    }
}
