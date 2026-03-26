package app;

import javax.swing.*;
import java.awt.*;

public class Main {

    private CardLayout cardLayout;
    private JPanel container;

    public Main() {
        initDatabase();
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
        new Main();  // GUI opens from constructor
    }

    // ================= DATABASE INIT =================
    public static void initDatabase() {
        try (var conn = DBConnection.connect();
             var stmt = conn.createStatement()) {

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