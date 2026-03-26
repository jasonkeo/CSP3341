package app;

import javax.swing.*;
import java.sql.*;

public class SearchPage extends JPanel {

    public SearchPage(Main app) {

        JTextField text = new JTextField(15);
        JButton searchBtn = new JButton("Search");
        JButton goToInsert = new JButton("Go to Insert Page");
        JLabel resultLabel = new JLabel("Result: ");

        add(new JLabel("Search Page"));
        add(text);
        add(searchBtn);
        add(goToInsert);
        add(resultLabel);

        searchBtn.addActionListener(e -> {
            String result = search(text.getText());
            resultLabel.setText("Result: " + result);
        });

        goToInsert.addActionListener(e -> app.showPage("page2"));
    }

    private String search(String input) {
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
}