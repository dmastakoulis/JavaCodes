package Assessment2;

import java.sql.*;
import javax.swing.*;

public class DatabaseSetup {
    private static final String DB_URL_BASE = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "hangman_db";
    private static final String DB_USER = "root";
    private static final String DB_PASS = ""; // Empty password - common default for WampServer

    public static void main(String[] args) {
        // Show input dialog for username and password
        String username = JOptionPane.showInputDialog(
                null,
                "Enter MySQL username (default is 'root'):",
                "Database Setup",
                JOptionPane.QUESTION_MESSAGE);

        if (username == null) {
            System.out.println("Setup cancelled by user");
            return;
        }

        if (username.trim().isEmpty()) {
            username = "root";
        }

        String password = JOptionPane.showInputDialog(
                null,
                "Enter MySQL password (leave empty if no password):",
                "Database Setup",
                JOptionPane.QUESTION_MESSAGE);

        if (password == null) {
            System.out.println("Setup cancelled by user");
            return;
        }

        // Use provided credentials
        String actualUsername = username.trim();
        String actualPassword = password; // Don't trim password as it might be intentionally spaces

        try {
            // Test basic connection to MySQL server (without specifying database)
            Connection conn = connectToServer(actualUsername, actualPassword);
            if (conn != null) {
                System.out.println("Successfully connected to MySQL server!");

                // Check if database exists
                if (!databaseExists(conn)) {
                    System.out.println("Database 'hangman_db' does not exist. Creating it now...");
                    createDatabase(conn);
                    System.out.println("Database created successfully!");
                } else {
                    System.out.println("Database 'hangman_db' already exists.");
                }

                // Close the connection to server
                conn.close();

                // Now try to connect to the specific database
                Connection dbConn = connectToDatabase(actualUsername, actualPassword);
                if (dbConn != null) {
                    System.out.println("Successfully connected to 'hangman_db' database!");
                    createTablesIfNotExist(dbConn);
                    System.out.println("Tables are ready!");
                    dbConn.close();

                    // Show instructions for updating DatabaseManager.java
                    JOptionPane.showMessageDialog(null,
                            "Database setup was successful!\n\n" +
                                    "IMPORTANT: You now need to update your DatabaseManager.java file with these credentials:\n" +
                                    "DB_USER = \"" + actualUsername + "\"\n" +
                                    "DB_PASS = \"" + actualPassword + "\"\n\n" +
                                    "Also add allowPublicKeyRetrieval=true to the connection URL.",
                            "Setup Complete", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (SQLException e) {
            String errorMsg = "Error: " + e.getMessage() + "\n";

            if (e.getMessage().contains("Access denied")) {
                errorMsg += "Your username or password is incorrect.";
            } else if (e.getMessage().contains("Communications link failure")) {
                errorMsg += "MySQL server is not running or not accessible. Make sure WampServer is running properly.";
            } else {
                errorMsg += "Please check your MySQL configuration.";
            }

            System.err.println(errorMsg);
            JOptionPane.showMessageDialog(null, errorMsg, "Database Setup Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static Connection connectToServer(String username, String password) throws SQLException {
        return DriverManager.getConnection(
                DB_URL_BASE + "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true",
                username,
                password
        );
    }

    private static Connection connectToDatabase(String username, String password) throws SQLException {
        return DriverManager.getConnection(
                DB_URL_BASE + DB_NAME + "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true",
                username,
                password
        );
    }

    private static boolean databaseExists(Connection conn) throws SQLException {
        ResultSet resultSet = conn.getMetaData().getCatalogs();
        while (resultSet.next()) {
            String databaseName = resultSet.getString(1);
            if (databaseName.equals(DB_NAME)) {
                resultSet.close();
                return true;
            }
        }
        resultSet.close();
        return false;
    }

    private static void createDatabase(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("CREATE DATABASE " + DB_NAME);
        stmt.close();
    }

    private static void createTablesIfNotExist(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "username VARCHAR(50) UNIQUE NOT NULL, " +
                "password VARCHAR(50) NOT NULL, " +
                "games_played INT DEFAULT 0, " +
                "games_won INT DEFAULT 0, " +
                "score INT DEFAULT 0)";
        stmt.executeUpdate(sql);

        // Add a test user if table is empty
        ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM users");
        if (rs.next() && rs.getInt(1) == 0) {
            PreparedStatement pstmt = conn.prepareStatement(
                    "INSERT INTO users (username, password, games_played, games_won, score) VALUES (?, ?, ?, ?, ?)"
            );
            pstmt.setString(1, "test");
            pstmt.setString(2, "test");
            pstmt.setInt(3, 5);
            pstmt.setInt(4, 3);
            pstmt.setInt(5, 30);
            pstmt.executeUpdate();
            pstmt.close();
            System.out.println("Added test user (username: test, password: test)");
        }

        rs.close();
        stmt.close();
    }
}