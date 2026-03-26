package app.pages;

import javax.swing.*;
import java.awt.*;

import app.Main;
import app.logic.*;

public class InsertPage extends JPanel {
    Logic logic = new Logic();

    public InsertPage(Main app) {
        setLayout(new GridLayout(0, 1, 5, 5)); // 1 column, spacing

        JTextField firstName = new JTextField(10);
        JTextField lastName = new JTextField(10);
        JPasswordField password = new JPasswordField(10);

        JButton insertBtn = new JButton("Insert");
        JButton goToSearch = new JButton("Go to Search Page");
        JLabel statusLabel = new JLabel(" ");

        add(new JLabel("Insert Page"));

        add(new JLabel("First Name:"));
        add(firstName);

        add(new JLabel("Last Name:"));
        add(lastName);

        add(new JLabel("Password:"));
        add(password);

        add(insertBtn);
        add(goToSearch);
        add(statusLabel);

        insertBtn.addActionListener(e -> {
            String fName = firstName.getText();
            String lName = lastName.getText();
            char[] pass = password.getPassword();

            boolean success = logic.insertUser(fName, lName, pass, "student");

            if (success) {
                statusLabel.setText("Inserted successfully");
            } else {
                statusLabel.setText("Insert failed (username may exist)");
            }
        });

        goToSearch.addActionListener(e -> app.showPage("page1"));
    }
}