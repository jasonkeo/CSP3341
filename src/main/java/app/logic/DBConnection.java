package app.logic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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

    // ================= DATABASE INIT =================
    public static void initDatabase() {
        try (var conn = DBConnection.connect();
                var stmt = conn.createStatement()) {

            stmt.executeUpdate("""
                        CREATE TABLE IF NOT EXISTS users (
                            id INTEGER PRIMARY KEY AUTOINCREMENT,
                            firstName TEXT NOT NULL,
                            lastName TEXT NOT NULL,
                            username TEXT UNIQUE NOT NULL,
                            password TEXT NOT NULL,
                            role TEXT NOT NULL
                        )
                    """);

            String hashed = Logic.hashPassword("admin");

            PreparedStatement insertStmt = conn.prepareStatement("""
                        INSERT OR IGNORE INTO users (firstName, lastName, username, password, role)
                        VALUES (?, ?, ?, ?, ?)
                    """);

            insertStmt.setString(1, "Mark");
            insertStmt.setString(2, "Grayson");
            insertStmt.setString(3, "mgrayson");
            insertStmt.setString(4, hashed);
            insertStmt.setString(5, "admin");

            insertStmt.executeUpdate();

            System.out.println("Database ready");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}