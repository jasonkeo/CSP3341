package app.pages;

import javax.swing.*;
import app.logic.*;

import app.Main;

public class SearchPage extends JPanel {
    Logic logic = new Logic();

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
            String result = logic.searchUser(text.getText());
            resultLabel.setText("Result: " + result);
        });

        goToInsert.addActionListener(e -> app.showPage("page2"));
    }

}