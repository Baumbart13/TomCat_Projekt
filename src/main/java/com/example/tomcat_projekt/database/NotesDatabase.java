package com.example.tomcat_projekt.database;

import com.example.tomcat_projekt.models.Note;
import com.example.tomcat_projekt.models.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;


public class NotesDatabase extends MySQLDatabase {
    public static final String _TABLE_NAME = "notes_notes";

    public enum _TABLE_FIELDS {
        email_user,
        note_index,
        message
    }

    public NotesDatabase() {
        super();
    }

    public NotesDatabase(String hostname, String user, String pass, String database) {
        super(hostname, user, pass, database);
        try {
            this.connect();
            this.createDatabase();
            this.createTable();
            this.disconnect();
        } catch (SQLException e) {
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

        sb.append("CREATE TABLE IF NOT EXISTS %s ("); // tableName
        sb.append("%s VARCHAR(40) NOT NULL,"); // email_user
        sb.append("%s INT NOT NULL,"); // note_index
        sb.append("%s VARCHAR(500),"); // message

        sb.append("PRIMARY KEY(%s),"); // note_index
        sb.append("FOREIGN KEY(%s) REFERENCES %s(%s));"); // email_user, User._TABLE_NAME, User._TABLE_FIELDS.email

        stmnt = connection.prepareStatement(String.format(sb.toString(),
                tableName,
                _TABLE_FIELDS.email_user.name(),
                _TABLE_FIELDS.note_index.name(),
                _TABLE_FIELDS.message.name(),
                // Primary Key
                _TABLE_FIELDS.note_index.name(),
                // Foreign Key
                _TABLE_FIELDS.email_user.name(),
                UserDatabase._TABLE_NAME,
                UserDatabase._TABLE_FIELDS.email.name()
        ));

        stmnt.execute();
    }

    public LinkedList<Note> getAllNotes(String email, String username) throws SQLException {
        var u = new User();
        u.setEmail(email);
        u.setUsername(username);
        return getAllNotes(u);
    }

    public LinkedList<Note> getAllNotes(User user) throws SQLException {
        createTable();
        var sql = String.format("SELECT " +
                        "%s " + // email_user
                        ",%s " + // note_index
                        ",%s " + // message
                        "FROM %s, %s" + // _TABLE_NAME, UserDatabase._TABLE_NAME
                        " WHERE %s.%s = ?" +
                        " OR %s.%s = ?;",
                _TABLE_FIELDS.email_user.name(),
                _TABLE_FIELDS.note_index.name(),
                _TABLE_FIELDS.message.name(),
                _TABLE_NAME, UserDatabase._TABLE_NAME,
                UserDatabase._TABLE_NAME, UserDatabase._TABLE_FIELDS.email.name(),
                UserDatabase._TABLE_NAME, UserDatabase._TABLE_FIELDS.username.name());

        var stmnt = connection.prepareStatement(sql);
        stmnt.setString(1, user.getEmail());
        stmnt.setString(2, user.getUsername());
        var rs = stmnt.executeQuery(sql);

        var notes = new LinkedList<Note>();
        while (rs.next()) {
            var u = new User();
            u.setEmail(rs.getString(_TABLE_FIELDS.email_user.name()));
            var note_index = rs.getInt(_TABLE_FIELDS.note_index.name());
            var msg = rs.getString(_TABLE_FIELDS.message.name());
            notes.add(new Note(
                    user,
                    note_index,
                    msg
            ));
        }
        rs.close();
        return notes;
    }
}
