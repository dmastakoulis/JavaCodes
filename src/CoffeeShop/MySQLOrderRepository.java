package CoffeeShop;

import java.sql.*;
import java.util.*;

public class MySQLOrderRepository implements OrderRepository {
    private final String url = "jdbc:mysql://localhost:3306/coffeeshop";
    private final String username = "root";
    private final String password = ""; // change as needed

    public MySQLOrderRepository() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            createDatabaseAndTable();
        } catch (Exception e) {
            throw new RuntimeException("MySQL setup failed", e);
        }
    }

    private void createDatabaseAndTable() throws SQLException {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/?useSSL=false", username, password);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS coffeeshop");
        }

        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS orders (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    customer_name VARCHAR(255),
                    total_amount DOUBLE,
                    order_date VARCHAR(10)
                )
            """);
        }
    }

    @Override
    public void addOrder(Order order) {
        String sql = "INSERT INTO orders (customer_name, total_amount, order_date) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, order.getCustomerName());
            stmt.setDouble(2, order.getTotalAmount());
            stmt.setString(3, order.getDate());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("MySQL addOrder failed", e);
        }
    }

    @Override
    public List<Order> getOrdersByDate(String date) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT customer_name, total_amount FROM orders WHERE order_date = ?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, date);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                orders.add(new Order(rs.getString(1), rs.getDouble(2), date));
            }
        } catch (SQLException e) {
            throw new RuntimeException("MySQL getOrdersByDate failed", e);
        }
        return orders;
    }

    @Override
    public double getTotalAmountByCustomer(String customerName) {
        String sql = "SELECT SUM(total_amount) FROM orders WHERE LOWER(customer_name) = LOWER(?)";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, customerName);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? rs.getDouble(1) : 0.0;
        } catch (SQLException e) {
            throw new RuntimeException("MySQL getTotalAmountByCustomer failed", e);
        }
    }
}
