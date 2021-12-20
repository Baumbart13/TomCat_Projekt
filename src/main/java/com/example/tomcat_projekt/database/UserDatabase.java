package com.example.tomcat_projekt.database;

import com.example.tomcat_projekt.models.*;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;

public class UserDatabase extends MySQLDatabase {
    public static final String _TABLE_NAME = "notes_user";
    public static final String _DATABASE_NAME = "notes";

    public enum _TABLE_FIELDS {
        email,
        forename,
        lastname,
        username,
        password,
        birthday,
        join_date
    }

    public UserDatabase() {
        this("", "", "", _DATABASE_NAME);
    }

    public UserDatabase(String hostname, String user, String pass, String database) {
        super(hostname, user, pass, database);
    }

    public UserDatabase(UserDatabase userDb) {
        this(userDb.hostname, userDb.hostname, userDb.password, userDb.database);
    }

    @Override
    public Database clone() {
        return new UserDatabase(this);
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
        sb.append("%s VARCHAR(40) NOT NULL,"); // email
        sb.append("%s VARCHAR(130),"); // forename
        sb.append("%s VARCHAR(130),"); // lastname
        sb.append("%s VARCHAR(130) NOT NULL,"); // username
        sb.append("%s VARCHAR(130) NOT NULL,"); // password
        sb.append("%s DATE,"); // birthday
        sb.append("%s TIMESTAMP NOT NULL,"); // join_date
        sb.append("PRIMARY KEY(%s));"); // email

        stmnt = connection.prepareStatement(String.format(sb.toString(),
                _TABLE_FIELDS.email.name(),
                _TABLE_FIELDS.forename.name(),
                _TABLE_FIELDS.lastname.name(),
                _TABLE_FIELDS.username.name(),
                _TABLE_FIELDS.password.name(),
                _TABLE_FIELDS.birthday.name(),
                _TABLE_FIELDS.join_date.name(),
                // Primary Key
                _TABLE_FIELDS.email.name()
        ));

        stmnt.execute();
    }

    public LinkedList<User> getAllUser() throws SQLException {
        var sql = String.format("SELECT %s, %s, %s, %s, %s, %s, %s FROM %s;",
                _TABLE_FIELDS.email.name(),
                _TABLE_FIELDS.forename.name(),
                _TABLE_FIELDS.lastname.name(),
                _TABLE_FIELDS.username.name(),
                _TABLE_FIELDS.password.name(),
                _TABLE_FIELDS.birthday.name(),
                _TABLE_FIELDS.join_date.name());

        var users = new LinkedList<User>();
        var stmnt = connection.createStatement();
        var rs = stmnt.executeQuery(sql);

        while (rs.next()) {
            var email = rs.getString(1);
            var forename = rs.getString(2);
            var lastname = rs.getString(3);
            var username = rs.getString(4);
            var password = rs.getString(5);
            var birthday = rs.getDate(6).toLocalDate();
            var join_date = rs.getTimestamp(7).toLocalDateTime();
            users.add(new User(
                    email,
                    forename,
                    lastname,
                    username,
                    password,
                    birthday,
                    join_date
            ));
        }
        rs.close();
        return users;
    }

    public boolean hasUser(User user) throws SQLException {
        var sql = String.format("SELECT COUNT(*) AS 'user_count' FROM %s WHERE %s = ? OR %s = ?",
                _TABLE_NAME,
                _TABLE_FIELDS.email,
                _TABLE_FIELDS.username);
        boolean hasUser = true;
        var stmnt = connection.prepareStatement(sql);
        stmnt.setString(1, user.getEmail());
        stmnt.setString(2, user.getUsername());

        var rs = stmnt.executeQuery();
        rs.next();
        hasUser = rs.getInt("user_count") > 0;
        rs.close();

        return hasUser;
    }

    public boolean insertUser(User user) throws SQLException{
        var sql = String.format(
                "INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s)" +
                        "VALUES (?, ?, ?, ?, ?, ?, ?);",
                _TABLE_NAME,
                _TABLE_FIELDS.email,
                _TABLE_FIELDS.username,
                _TABLE_FIELDS.forename,
                _TABLE_FIELDS.lastname,
                _TABLE_FIELDS.password,
                _TABLE_FIELDS.birthday,
                _TABLE_FIELDS.join_date);
        var success = false;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getUsername());
            stmt.setString(3, user.getForename());
            stmt.setString(4, user.getLastname());
            stmt.setString(5, user.getPassword());
            stmt.setDate(6, Date.valueOf(user.getBirthday()));
            stmt.setTimestamp(7, Timestamp.valueOf(user.getJoin_date()));
            success = stmt.execute();
        }
        return success;
    }

    public User getUser(String email, String username) throws SQLException{
        var sql = String.format("SELECT %s, %s, %s, %s, %s, %s, %s FROM %s WHERE %s = ? OR %s = ?;",
                _TABLE_FIELDS.email.name(),
                _TABLE_FIELDS.forename.name(),
                _TABLE_FIELDS.lastname.name(),
                _TABLE_FIELDS.username.name(),
                _TABLE_FIELDS.password.name(),
                _TABLE_FIELDS.birthday.name(),
                _TABLE_FIELDS.join_date.name(),

                _TABLE_NAME,

                _TABLE_FIELDS.email.name(),
                _TABLE_FIELDS.username.name());
        var stmnt = connection.prepareStatement(sql);
        var rs = stmnt.executeQuery();
        User user = new User();
        if(rs.next()){
            user = new User(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getDate(6).toLocalDate(),
                    rs.getTimestamp(7).toLocalDateTime()
            );
        }

        return user;
    }

    public boolean canLogin(String email, String password) throws SQLException{
        var u = new User();
        u.setEmail(email);
        u.setPassword(password);
        return canLogin(u);
    }

    public boolean canLogin(User user) throws SQLException {
        var sql = String.format("SELECT COUNT(*) FROM %s WHERE %s = ? AND %s = ?;",
                _TABLE_NAME,
                _TABLE_FIELDS.email.name(),
                _TABLE_FIELDS.password.name());
        var rs = connection.prepareStatement(sql).executeQuery();
        if(rs.next()){
            return rs.getInt(1) > 0;
        }
        return false;
    }
}
