package com.example.tomcat_projekt.database;

import com.example.tomcat_projekt.models.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;

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
        super();
    }

    public UserDatabase(String hostname, String user, String pass, String database) {
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
        sb.append("%s TIMESTAMP,"); // join_date
        sb.append("PRIMARY KEY(%s));"); // email

        var stmntStr = sb.toString();
        stmntStr = String.format(stmntStr,
                _TABLE_NAME,
                _TABLE_FIELDS.email.name(),
                _TABLE_FIELDS.forename.name(),
                _TABLE_FIELDS.lastname.name(),
                _TABLE_FIELDS.username.name(),
                _TABLE_FIELDS.password.name(),
                _TABLE_FIELDS.birthday.name(),
                _TABLE_FIELDS.join_date.name(),
                // Primary Key
                _TABLE_FIELDS.email.name()
        );
        stmnt = connection.prepareStatement(stmntStr);

        stmnt.execute();
    }

    public LinkedList<User> getAllUser() throws SQLException {
        createTable();
        var sql = String.format("SELECT " +
                        "%s " + // email
                        ",%s " + // forename
                        ",%s " + // lastname
                        ",%s " + // username
                        ",%s " + // password
                        "FROM %s;", // _TABLE_NAME
                _TABLE_FIELDS.email.name(),
                _TABLE_FIELDS.forename.name(),
                _TABLE_FIELDS.lastname.name(),
                _TABLE_FIELDS.username.name(),
                _TABLE_FIELDS.password.name(),
                _TABLE_NAME);

        var users = new LinkedList<User>();
        var stmnt = connection.createStatement();
        var rs = stmnt.executeQuery(sql);

        while (rs.next()) {
            var email = rs.getString(_TABLE_FIELDS.email.name());
            var forename = rs.getString(_TABLE_FIELDS.forename.name());
            var lastname = rs.getString(_TABLE_FIELDS.lastname.name());
            var username = rs.getString(_TABLE_FIELDS.forename.name());
            var password = rs.getString(_TABLE_FIELDS.password.name());
            users.add(new User(
                    email,
                    forename,
                    lastname,
                    username,
                    password
            ));
        }
        rs.close();
        return users;
    }

    public boolean hasUser(User user) throws SQLException {
        createTable();
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

    public boolean insertUser(User user) throws SQLException {
        createTable();
        var sql = String.format(
                "INSERT INTO %s (%s, %s, %s, %s, %s)" +
                        " VALUES (?, ?, ?, ?, sha2(?, 256));",
                _TABLE_NAME,
                _TABLE_FIELDS.email,
                _TABLE_FIELDS.username,
                _TABLE_FIELDS.forename,
                _TABLE_FIELDS.lastname,
                _TABLE_FIELDS.password);
        var success = false;
        var stmt = connection.prepareStatement(sql);
        stmt.setString(1, user.getEmail());
        stmt.setString(2, user.getUsername());
        stmt.setString(3, user.getForename());
        stmt.setString(4, user.getLastname());
        stmt.setString(5, user.getPassword());
        success = stmt.execute();

        return !success;
    }

    public User getUser(String email, String username) throws SQLException {
        createTable();
        var sql = String.format("SELECT" +
                        " %s," + // forename
                        " %s," + // lastname
                        " %s" + // password
                        " FROM %s" +
                        " WHERE %s = ?" +
                        " OR %s = ?;",
                _TABLE_FIELDS.forename.name(),
                _TABLE_FIELDS.lastname.name(),
                _TABLE_FIELDS.password.name(),
                _TABLE_NAME,
                _TABLE_FIELDS.email.name(),
                _TABLE_FIELDS.username.name());

        var stmnt = connection.prepareStatement(sql);
        stmnt.setString(1, email);
        stmnt.setString(2, username);

        var rs = stmnt.executeQuery();
        User user = new User();
        if (rs.next()) {
            var forename = rs.getString(_TABLE_FIELDS.forename.name());
            var lastname = rs.getString(_TABLE_FIELDS.lastname.name());
            var password = rs.getString(_TABLE_FIELDS.password.name());
            user = new User(
                    email,
                    forename,
                    lastname,
                    username,
                    password);
        }
        rs.close();
        return user;
    }

    public boolean canLogin(String email, String username, String password) throws SQLException {
        var u = new User();
        u.setEmail(email);
        u.setEmail(username);
        u.setPassword(password);
        return canLogin(u);
    }

    public boolean canLogin(User user) throws SQLException {
        createTable();
        var sql = String.format("SELECT COUNT(*) FROM %s WHERE (%s = ? OR %s = ?) AND %s = sha2(?, 256);",
                _TABLE_NAME,
                _TABLE_FIELDS.email.name(),
                _TABLE_FIELDS.username.name(),
                _TABLE_FIELDS.password.name());
        var stmnt = connection.prepareStatement(sql);
        stmnt.setString(1, user.getEmail());
        stmnt.setString(2, user.getUsername());
        stmnt.setString(3, user.getPassword());
        var rs = stmnt.executeQuery();
        if (rs.next()) {
            var i = rs.getInt(1) > 0;
            rs.close();
            return i;
        }
        rs.close();
        return false;
    }
}
