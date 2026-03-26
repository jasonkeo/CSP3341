package app.pages;

import javax.swing.*;
import app.logic.*;

import app.Main;

public class SearchPage extends JPanel {
    Logic logic = new Logic();
    private Main app;

    public SearchPage(Main app) {
        this.app = app;
        UserSession session = app.getSession();
        JTextField text = new JTextField(15);
        JButton searchBtn = new JButton("Search");
        if (session != null && session.role.equals("admin")) {
            JButton goToInsert = new JButton("Go to Insert Page");
            add(goToInsert);

            goToInsert.addActionListener(e -> app.showPage("page2"));
        }
        JLabel resultLabel = new JLabel("Result: ");

        add(new JLabel("Search Page"));
        add(text);
        add(searchBtn);

        add(resultLabel);

        searchBtn.addActionListener(e -> {
            String result = logic.searchUser(text.getText());
            resultLabel.setText("Result: " + result);
        });

    }

}