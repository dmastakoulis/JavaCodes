package Assessment2;

import java.sql.*;

public class DatabaseManager {
    // These values should match what you entered in DatabaseSetup
    private static final String DB_URL = "jdbc:mysql://localhost:3306/hangman_db";
    private static final String DB_USER = "root";  // Update this with your actual MySQL username
    private static final String DB_PASS = "";      // Update this with your actual MySQL password (empty string for no password)

    private Connection connection;

    public DatabaseManager() {
        connectToDatabase();
    }

    private void connectToDatabase() {
        try {
            // 1. Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 2. Establish connection with timeout settings and allowPublicKeyRetrieval=true
            connection = DriverManager.getConnection(
                    DB_URL + "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true",
                    DB_USER,
                    DB_PASS
            );

            // Test the connection
            if (connection != null && !connection.isClosed()) {
                System.out.println("Successfully connected to MySQL database!");
                // Ensure the tables exist
                createTablesIfNotExist();
            }
        } catch (ClassNotFoundException e) {
            System.err.println("ERROR: MySQL JDBC Driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("ERROR: Connection to MySQL database failed!");
            System.err.println("URL: " + DB_URL);
            System.err.println("User: " + DB_USER);
            e.printStackTrace();
        }
    }

    private void createTablesIfNotExist() {
        try {
            // Create users table if it doesn't exist
            Statement stmt = connection.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS users (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "username VARCHAR(50) UNIQUE NOT NULL, " +
                    "password VARCHAR(50) NOT NULL, " +
                    "games_played INT DEFAULT 0, " +
                    "games_won INT DEFAULT 0, " +
                    "score INT DEFAULT 0)";
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            System.err.println("Error creating tables: " + e.getMessage());
        }
    }

    public boolean isConnected() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean registerUser(String username, String password) {
        if (!isConnected()) {
            System.err.println("Cannot register user - no database connection");
            return false;
        }

        String query = "INSERT INTO users (username, password, games_played, games_won, score) VALUES (?, ?, 0, 0, 0)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error registering user: " + e.getMessage());
            return false;
        }
    }

    public User authenticateUser(String username, String password) {
        if (!isConnected()) {
            System.err.println("Cannot authenticate user - no database connection");
            return null;
        }

        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getInt("games_played"),
                            rs.getInt("games_won"),
                            rs.getInt("score")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error authenticating user: " + e.getMessage());
        }
        return null;
    }

    public void updateUserStats(User user) {
        if (!isConnected()) {
            System.err.println("Cannot update stats - no database connection");
            return;
        }

        String query = "UPDATE users SET games_played = ?, games_won = ?, score = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, user.getGamesPlayed());
            stmt.setInt(2, user.getGamesWon());
            stmt.setInt(3, user.getScore());
            stmt.setInt(4, user.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating user stats: " + e.getMessage());
        }
    }

    public ResultSet getLeaderboard() throws SQLException {
        if (!isConnected()) {
            throw new SQLException("No database connection");
        }

        String query = "SELECT username, games_won, score FROM users ORDER BY score DESC LIMIT 10";
        PreparedStatement stmt = connection.prepareStatement(query);
        return stmt.executeQuery();
    }

    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed");
            }
        } catch (SQLException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }
}