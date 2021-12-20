package com.example.tomcat_projekt.database;

import com.example.tomcat_projekt.models.Note;
import com.example.tomcat_projekt.models.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;


public class NotesDatabase extends MySQLDatabase{
    public static final String _TABLE_NAME = "notes_notes";
    public static final String _DATABASE_NAME = "notes";

    public enum _TABLE_FIELDS {
        email_user,
        write_time,
        message
    }

    public NotesDatabase() {
        this("localhost:3306", "root", "DuArschloch4", _DATABASE_NAME);
    }

    public NotesDatabase(String hostname, String user, String pass, String database) {
        super(hostname, user, pass, database);
        try {
            this.connect();
            this.createDatabase();
            this.createTable();
            this.disconnect();
        }catch(SQLException e){
            logger.log(Level.SEVERE, e.getMessage());
        }
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

    public LinkedList<Note> getAllMessages(User user) throws SQLException{
        var sql = String.format("SELECT %s, %s FROM %s WHERE %s = ?;",
                _TABLE_FIELDS.write_time.name(),
                _TABLE_FIELDS.message.name(),
                _TABLE_NAME,
                UserDatabase._TABLE_FIELDS.email);
        var stmnt = connection.prepareStatement(sql);
        stmnt.setString(1, user.getEmail());
        var rs  = stmnt.executeQuery();
        LinkedList<Note> notes = new LinkedList<Note>();
        while(rs.next()){
            notes.add(new Note(
                    user,
                    rs.getTimestamp(1).toLocalDateTime(),
                    rs.getString(2)));
        }
        return notes;
    }
}
