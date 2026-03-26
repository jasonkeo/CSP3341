package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = "jdbc:sqlite:database.db";

    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL);
            System.out.println("Connected to SQLite");
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
        return conn;
    }
}