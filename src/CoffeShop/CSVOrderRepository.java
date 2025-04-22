package CoffeShop;

import java.io.*;
import java.util.*;

public class CSVOrderRepository implements OrderRepository {
    private final String fileName = "orders.csv";

    public CSVOrderRepository() {
        File file = new File(fileName);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            System.out.println("Error creating file: " + e.getMessage());
        }
    }

    @Override
    public void addOrder(Order order) {
        try {
            FileWriter writer = new FileWriter(fileName, true); // append mode
            writer.write(order.getCustomerName() + "," + order.getTotalAmount() + "," + order.getDate() + "\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("Error writing order: " + e.getMessage());
        }
    }

    @Override
    public List<Order> getOrdersByDate(String date) {
        List<Order> orders = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(new File(fileName));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 3 && parts[2].equals(date)) {
                    String name = parts[0];
                    double amount = Double.parseDouble(parts[1]);
                    orders.add(new Order(name, amount, date));
                }
            }
            scanner.close();
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        return orders;
    }

    @Override
    public double getTotalAmountByCustomer(String customerName) {
        double total = 0;

        try {
            Scanner scanner = new Scanner(new File(fileName));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 3 && parts[0].equalsIgnoreCase(customerName)) {
                    total += Double.parseDouble(parts[1]);
                }
            }
            scanner.close();
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        return total;
    }
}

