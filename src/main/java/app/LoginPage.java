package app;

import javax.swing.*;
import java.awt.*;

import java.util.Arrays;

public class LoginPage extends JPanel {
    private JLabel userLabel, passwordLabel, validateLabel;
    private JTextField userText;
    private JPasswordField password;
    private JButton logButton;
    private String username = "root";
    private char[] correctPassword = { 'r','o','o','t','1','2','3' };

    public LoginPage(Main app) {
        setLayout(new FlowLayout());

        userLabel = new JLabel("Username: ");
        passwordLabel = new JLabel("Password: ");
        validateLabel = new JLabel();
        userText = new JTextField(15);
        password = new JPasswordField(15);
        logButton = new JButton("Login");

        add(new JLabel("Login Page"));
        add(userLabel);
        add(userText);
        add(passwordLabel);
        add(password);
        add(logButton);
        add(validateLabel);

        logButton.addActionListener(e -> {
            String usr = userText.getText();
            char[] input = password.getPassword();

            if (usr.equals(username) && Arrays.equals(input, correctPassword)) {
                validateLabel.setText("Login successful");
                app.showPage("page1"); // go to search page
            } else {
                validateLabel.setText("Username or password incorrect");
            }
        });
    }
}