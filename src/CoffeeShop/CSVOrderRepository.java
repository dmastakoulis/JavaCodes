package CoffeeShop;

import java.io.*;
import java.util.*;

public class CSVOrderRepository implements OrderRepository {
    private final String fileName = "orders.csv";
    private final File file;

    public CSVOrderRepository() {
        file = new File(fileName);
        try {
            if (!file.exists()) {
                file.createNewFile();
                System.out.println("CSV file 'orders.csv' created.");
            } else {
                System.out.println("Using existing CSV file.");
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize CSV storage", e);
        }
    }

    @Override
    public void addOrder(Order order) {
        if (order.getCustomerName().isEmpty() || order.getDate().isEmpty() || order.getTotalAmount() < 0) {
            throw new IllegalArgumentException("Invalid order data.");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            String escapedName = order.getCustomerName().replace(",", ";");
            String line = String.format("%s,%.2f,%s%n", escapedName, order.getTotalAmount(), order.getDate());
            writer.write(line);
        } catch (IOException e) {
            throw new RuntimeException("Failed to add order", e);
        }
    }

    @Override
    public List<Order> getOrdersByDate(String date) {
        List<Order> orders = new ArrayList<>();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(",");
                if (parts.length >= 3 && parts[2].equals(date)) {
                    String name = parts[0].replace(";", ",");
                    double amount = Double.parseDouble(parts[1]);
                    orders.add(new Order(name, amount, date));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read orders", e);
        }
        return orders;
    }

    @Override
    public double getTotalAmountByCustomer(String customerName) {
        double total = 0.0;
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(",");
                String name = parts[0].replace(";", ",");
                if (name.equalsIgnoreCase(customerName)) {
                    total += Double.parseDouble(parts[1]);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to calculate total", e);
        }
        return total;
    }
}
