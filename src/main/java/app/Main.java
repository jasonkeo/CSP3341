package app;

import javax.swing.*;

import app.logic.UserSession;
import app.logic.DBConnection;

import java.awt.*;
import app.pages.*;

public class Main {
    private UserSession currentSession;
    private CardLayout cardLayout;
    private JPanel container;

    public Main() {
        DBConnection.initDatabase();
        UserPage(); // launch GUI
    }

    public void UserPage() {
        JFrame frame = new JFrame("Multi Page UI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        container = new JPanel(cardLayout);

        // Create pages
        LoginPage page0 = new LoginPage(this);
        SearchPage page1 = new SearchPage(this);
        InsertPage page2 = new InsertPage(this);

        container.add(page0, "page0");
        container.add(page1, "page1");
        container.add(page2, "page2");

        frame.add(container);
        frame.setVisible(true);
    }

    public void showPage(String page) {
        cardLayout.show(container, page);
    }

    public static void main(String[] args) {
        new Main(); // GUI opens from constructor
    }

    public void setSession(UserSession session) {
        this.currentSession = session;
    }

    public UserSession getSession() {
        return this.currentSession;
    }

}