package app.pages;

import javax.swing.*;

import app.Main;
import app.logic.*;
import java.awt.*;

public class LoginPage extends JPanel {
    Logic logic = new Logic();

    private JLabel userLabel, passwordLabel, validateLabel;
    private JTextField userText;
    private JPasswordField password;
    private JButton logButton;

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
            UserSession session = logic.checkLogin(usr, input);
            if (session != null) {
                app.setSession(session);
                validateLabel.setText("Login successful");
                app.showPage("page1"); // go to search page
            } else {
                validateLabel.setText("Username or password incorrect");
            }
        });
    }

}