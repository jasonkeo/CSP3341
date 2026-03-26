package app;

import javax.swing.*;
import java.sql.*;

public class InsertPage extends JPanel {

    public InsertPage(Main app) {

        JTextField text = new JTextField(15);
        JButton insertBtn = new JButton("Insert");
        JButton goToSearch = new JButton("Go to Search Page");
        JLabel statusLabel = new JLabel(" ");

        add(new JLabel("Insert Page"));
        add(text);
        add(insertBtn);
        add(goToSearch);
        add(statusLabel);

        insertBtn.addActionListener(e -> {
            boolean success = insert(text.getText());

            if (success) {
                statusLabel.setText("Inserted successfully");
            } else {
                statusLabel.setText("Insert failed");
            }
        });

        goToSearch.addActionListener(e -> app.showPage("page1"));
    }

    private boolean insert(String input) {
        try (Connection conn = DBConnection.connect()) {

            PreparedStatement check = conn.prepareStatement(
                    "SELECT name FROM users WHERE name = ?");
            check.setString(1, input);

            ResultSet rs = check.executeQuery();
            if (rs.next()) return false;

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
}