package app.logic;

import java.sql.*;
import java.security.MessageDigest;

public class Logic {

    // Generate username: first letter of firstName + lastName (lowercase)
    private String generateUsername(String firstName, String lastName) {
        return (firstName.charAt(0) + lastName).toLowerCase();
    }

    // Hash password using SHA-256
    private String hashPassword(String password) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashedBytes = md.digest(password.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (byte b : hashedBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    // Insert a new user into the database
    public boolean insertUser(String firstName, String lastName, char[] password, String role) {
        String username = generateUsername(firstName, lastName);

        try (Connection conn = DBConnection.connect()) {
            // Check if username already exists
            PreparedStatement check = conn.prepareStatement(
                    "SELECT username FROM users WHERE username = ?");
            check.setString(1, username);
            ResultSet rs = check.executeQuery();
            if (rs.next())
                return false; // username exists

            // Hash the password
            String hashed = hashPassword(new String(password));

            // Insert user into database
            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO users(firstName, lastName, username, password, role) VALUES(?, ?, ?, ?, ?)");
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, username);
            stmt.setString(4, hashed);
            stmt.setString(5, role);

            stmt.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Check login credentials
    public boolean checkLogin(String usernameInput, char[] passwordInput) {
        try (Connection conn = DBConnection.connect();
                PreparedStatement stmt = conn.prepareStatement(
                        "SELECT password FROM users WHERE username = ?")) {

            stmt.setString(1, usernameInput);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("password");
                String inputHash = hashPassword(new String(passwordInput));
                return storedHash.equals(inputHash);
            }
            return false;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Search user by username
    public String searchUser(String username) {
        try (Connection conn = DBConnection.connect();
                PreparedStatement stmt = conn.prepareStatement(
                        "SELECT firstName, lastName, role FROM users WHERE username = ?")) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("firstName") + " " + rs.getString("lastName") +
                        " (" + rs.getString("role") + ")";
            } else {
                return "Not found";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
    }

}