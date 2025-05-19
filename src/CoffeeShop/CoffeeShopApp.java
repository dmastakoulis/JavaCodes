package CoffeeShop;

import CoffeeShop.MySQLOrderRepository;

import java.util.*;

public class CoffeeShopApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CoffeeShop.OrderRepository repo;

        System.out.println("Choose storage method: 1. CSV  2. MySQL");
        int choice = scanner.nextInt();
        scanner.nextLine();

        try {
            repo = (choice == 2) ? new MySQLOrderRepository() : new CoffeeShop.CSVOrderRepository();
        } catch (Exception e) {
            System.out.println("Fallback to CSV due to error: " + e.getMessage());
            repo = new CSVOrderRepository();
        }

        while (true) {
            System.out.println("\n=== Coffee Shop Menu ===");
            System.out.println("1. Add Order");
            System.out.println("2. View Orders by Date");
            System.out.println("3. Total Amount by Customer");
            System.out.println("0. Exit");
            System.out.print("Choice: ");
            int action = scanner.nextInt();
            scanner.nextLine();

            switch (action) {
                case 1 -> {
                    System.out.print("Customer name: ");
                    String name = scanner.nextLine();
                    System.out.print("Amount: ");
                    double amount = scanner.nextDouble();
                    scanner.nextLine();
                    System.out.print("Date (yyyy/mm/dd): ");
                    String date = scanner.nextLine();
                    repo.addOrder(new Order(name, amount, date));
                }
                case 2 -> {
                    System.out.print("Enter date: ");
                    String date = scanner.nextLine();
                    List<Order> orders = repo.getOrdersByDate(date);
                    if (orders.isEmpty()) {
                        System.out.println("No orders found.");
                    } else {
                        for (Order o : orders) {
                            System.out.printf("Customer: %s, Amount: %.2f%n", o.getCustomerName(), o.getTotalAmount());
                        }
                    }
                }
                case 3 -> {
                    System.out.print("Customer name: ");
                    String name = scanner.nextLine();
                    double total = repo.getTotalAmountByCustomer(name);
                    System.out.printf("Total: %.2f%n", total);
                }
                case 0 -> {
                    System.out.println("Exiting. Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }
}
