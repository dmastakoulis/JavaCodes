package Assessment2;

import javax.swing.SwingUtilities;
import javax.swing.JOptionPane;

public class HangmanGame {
    public static void main(String[] args) {
        try {
            // Set look and feel to system default
            javax.swing.UIManager.setLookAndFeel(
                    javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Could not set Look and Feel: " + e.getMessage());
        }

        // Initialize database manager
        DatabaseManager dbManager = new DatabaseManager();

        // Check if database is connected
        if (!dbManager.isConnected()) {
            JOptionPane.showMessageDialog(null,
                    "Cannot connect to database. Please check your MySQL server and settings.",
                    "Database Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        // Start with login screen
        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame(dbManager);
            loginFrame.setVisible(true);
        });

        // Add shutdown hook to close database connection
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            dbManager.close();
        }));
    }
}