package app.pages;

import javax.swing.*;

import app.Main;

import app.logic.*;

import java.sql.*;
import java.security.MessageDigest;

public class InsertPage extends JPanel {
    Logic logic = new Logic();

    public InsertPage(Main app) {
        JTextField firstName = new JTextField(10);
        JTextField lastName = new JTextField(10);
        JTextField username = new JTextField(10);
        JPasswordField password = new JPasswordField(10);
        JButton insertBtn = new JButton("Insert");
        JButton goToSearch = new JButton("Go to Search Page");
        JLabel statusLabel = new JLabel(" ");

        add(new JLabel("Insert Page"));
        add(new JLabel("First Name:"));
        add(firstName);
        add(new JLabel("Last Name:"));
        add(lastName);
        add(new JLabel("Username:"));
        add(username);
        add(new JLabel("Password:"));
        add(password);
        add(insertBtn);
        add(goToSearch);
        add(statusLabel);

        insertBtn.addActionListener(e -> {
            String fullName = firstName.getText() + " " + lastName.getText();
            String user = username.getText();
            char[] pass = password.getPassword();

            boolean success = logic.insertUser(fullName, user, pass, "student"); // default role student

            if (success) {
                statusLabel.setText("Inserted successfully");
            } else {
                statusLabel.setText("Insert failed (maybe username taken)");
            }
        });

        goToSearch.addActionListener(e -> app.showPage("page1"));
    }

}