package CoffeShop;

import java.util.*;

public class CoffeeShopApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        OrderRepository repo;

        System.out.println("Choose storage method: 1. CSV  2. MySQL");
        int storageChoice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        if (storageChoice == 1) {
            repo = new CSVOrderRepository();
        } else {
            repo = new MySQLOrderRepository();
        }

        while (true) {
            System.out.println("\n1. Add Order\n2. View orders of a date\n3. View total amount of a customer\n0. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter customer name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter total amount: ");
                    double amount = scanner.nextDouble();
                    scanner.nextLine();
                    System.out.print("Enter date (yyyy/mm/dd): ");
                    String date = scanner.nextLine();
                    repo.addOrder(new Order(name, amount, date));
                    break;
                case 2:
                    System.out.print("Enter date (yyyy/mm/dd): ");
                    String searchDate = scanner.nextLine();
                    List<Order> orders = repo.getOrdersByDate(searchDate);
                    if (orders.isEmpty()) {
                        System.out.println("No orders found.");
                    } else {
                        for (Order o : orders) {
                            System.out.println(o.getCustomerName() + " paid $" + o.getTotalAmount());
                        }
                    }
                    break;
                case 3:
                    System.out.print("Enter customer name: ");
                    String custName = scanner.nextLine();
                    double total = repo.getTotalAmountByCustomer(custName);
                    System.out.println("Total amount: $" + total);
                    break;
                case 0:
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
