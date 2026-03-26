package app;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class Main {

    public static void main(String[] args) {
        initDatabase();
        JFrame frame = new JFrame("Multi Page UI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        CardLayout cardLayout = new CardLayout();
        JPanel container = new JPanel(cardLayout);

        Main app = new Main(); // needed to call methods

        // ================= PAGE 1 (SEARCH) =================
        JPanel page1 = new JPanel();
        JTextField text1 = new JTextField(15);
        JButton submit1 = new JButton("Search");
        JButton goToPage2 = new JButton("Go to Insert Page");
        JLabel resultLabel = new JLabel("Result: ");

        page1.add(new JLabel("Search Page"));
        page1.add(text1);
        page1.add(submit1);
        page1.add(goToPage2);
        page1.add(resultLabel);

        submit1.addActionListener(e -> {
            String result = app.searchfunc(text1.getText());
            resultLabel.setText("Result: " + result);
        });

        

        // ================= PAGE 2 (INSERT) =================
        JPanel page2 = new JPanel();
        JTextField text2 = new JTextField(15);
        JButton submit2 = new JButton("Insert");
        JButton goToPage1 = new JButton("Go to Search Page");
        JLabel insertLabel = new JLabel(" ");

        page2.add(new JLabel("Insert Page"));
        page2.add(text2);
        page2.add(submit2);
        page2.add(goToPage1);
        page2.add(insertLabel);

        submit2.addActionListener(e -> {
            boolean success = app.createfunc(text2.getText());
            if (success) {
                insertLabel.setText("Inserted successfully");
            } else {
                insertLabel.setText("Insert failed");
            }
        });

        // ================= SWITCHING =================
        goToPage2.addActionListener(e -> cardLayout.show(container, "page2"));
        goToPage1.addActionListener(e -> cardLayout.show(container, "page1"));

        // Add pages
        container.add(page1, "page1");
        container.add(page2, "page2");

        frame.add(container);
        frame.setVisible(true);
    }

    // ================= SEARCH FUNCTION =================
    public String searchfunc(String input) {
        try (Connection conn = DBConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT name FROM users WHERE name = ?")) {

            stmt.setString(1, input);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("name");
            } else {
                return "Not found";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
    }

    // ================= INSERT FUNCTION =================
    public boolean createfunc(String input) {
        try (Connection conn = DBConnection.connect()) {

            // optional: check duplicate
            PreparedStatement check = conn.prepareStatement(
                    "SELECT name FROM users WHERE name = ?");
            check.setString(1, input);
            ResultSet rs = check.executeQuery();

            if (rs.next()) {
                return false; // already exists
            }

            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO users(name) VALUES(?)");
            stmt.setString(1, input);

            stmt.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void initDatabase() {
    try (Connection conn = DBConnection.connect();
         Statement stmt = conn.createStatement()) {

        stmt.executeUpdate("""
            CREATE TABLE IF NOT EXISTS users (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT
            )
        """);

        System.out.println("Database ready");

    } catch (Exception e) {
        e.printStackTrace();
    }
}
}