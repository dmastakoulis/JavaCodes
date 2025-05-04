package Assessemnt1;

// UserInterface.java
// User interface for the application
import java.util.List;
import java.util.Scanner;

class UserInterface {
    private Scanner scanner;
    private InventoryManager inventoryManager;

    public UserInterface(InventoryManager inventoryManager) {
        this.scanner = new Scanner(System.in);
        this.inventoryManager = inventoryManager;
    }

    public void displayMainMenu() {
        boolean exit = false;

        while (!exit) {
            System.out.println("\n===== INVENTORY MANAGEMENT SYSTEM =====");
            System.out.println("1. View All Products");
            System.out.println("2. Search Products");
            System.out.println("3. Add New Product");
            System.out.println("4. Update Product");
            System.out.println("5. Remove Product");
            System.out.println("6. View Products by Category");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            int choice = getIntInput();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    viewAllProducts();
                    break;
                case 2:
                    searchProducts();
                    break;
                case 3:
                    addNewProduct();
                    break;
                case 4:
                    updateProduct();
                    break;
                case 5:
                    removeProduct();
                    break;
                case 6:
                    viewProductsByCategory();
                    break;
                case 0:
                    exit = true;
                    System.out.println("Exiting program. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }

    private void viewAllProducts() {
        List<Product> products = inventoryManager.getAllProducts();

        if (products.isEmpty()) {
            System.out.println("No products in inventory.");
            return;
        }

        System.out.println("\n===== ALL PRODUCTS =====");
        for (Product product : products) {
            System.out.println("\n" + product.getProductDetails());
            System.out.println("------------------------");
        }
    }

    private void searchProducts() {
        System.out.print("Enter product name to search: ");
        String name = scanner.nextLine();

        List<Product> results = inventoryManager.searchProductsByName(name);

        if (results.isEmpty()) {
            System.out.println("No products found matching '" + name + "'.");
            return;
        }

        System.out.println("\n===== SEARCH RESULTS =====");
        for (Product product : results) {
            System.out.println("\n" + product.getProductDetails());
            System.out.println("------------------------");
        }
    }

    private void addNewProduct() {
        System.out.println("\n===== ADD NEW PRODUCT =====");
        System.out.println("Select product category:");
        System.out.println("1. Electronics");
        System.out.println("2. Clothing");
        System.out.println("3. Grocery");
        System.out.print("Enter category choice: ");

        int categoryChoice = getIntInput();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter product name: ");
        String name = scanner.nextLine();

        System.out.print("Enter price: $");
        double price = getDoubleInput();
        scanner.nextLine(); // Consume newline

        Product newProduct = null;
        String id = "P" + String.format("%03d", inventoryManager.getInventorySize() + 1);

        switch (categoryChoice) {
            case 1: // Electronics
                System.out.print("Enter brand name: ");
                String brandName = scanner.nextLine();

                System.out.print("Enter warranty period (months): ");
                int warranty = getIntInput();
                scanner.nextLine(); // Consume newline

                newProduct = new Electronics(id, name, price, brandName, warranty);
                break;

            case 2: // Clothing
                System.out.print("Enter available sizes (comma-separated): ");
                String sizes = scanner.nextLine();

                System.out.print("Enter material: ");
                String material = scanner.nextLine();

                System.out.print("Enter available colors (comma-separated): ");
                String colors = scanner.nextLine();

                newProduct = new Clothing(id, name, price, sizes, material, colors);
                break;

            case 3: // Grocery
                System.out.print("Enter weight (kg): ");
                double weight = getDoubleInput();
                scanner.nextLine(); // Consume newline

                System.out.print("Enter expiration date (YYYY-MM-DD): ");
                String expirationDate = scanner.nextLine();

                newProduct = new Grocery(id, name, price, weight, expirationDate);
                break;

            default:
                System.out.println("Invalid category choice.");
                return;
        }

        inventoryManager.addProduct(newProduct);
        System.out.println("Product added successfully! Product ID: " + id);
    }

    private void updateProduct() {
        System.out.print("Enter product ID to update: ");
        String id = scanner.nextLine();

        Product product = inventoryManager.getProductById(id);

        if (product == null) {
            System.out.println("Product not found.");
            return;
        }

        System.out.println("\nCurrent product details:");
        System.out.println(product.getProductDetails());

        System.out.print("\nEnter new name (or press Enter to keep current): ");
        String name = scanner.nextLine();
        if (name.isEmpty()) {
            name = product.getName();
        }

        System.out.print("Enter new price (or -1 to keep current): $");
        double price = getDoubleInput();
        scanner.nextLine(); // Consume newline
        if (price < 0) {
            price = product.getPrice();
        }

        Product updatedProduct = null;

        if (product instanceof Electronics) {
            Electronics electronics = (Electronics) product;

            System.out.print("Enter new brand name (or press Enter to keep current): ");
            String brandName = scanner.nextLine();
            if (brandName.isEmpty()) {
                brandName = electronics.getBrandName();
            }

            System.out.print("Enter new warranty period in months (or -1 to keep current): ");
            int warranty = getIntInput();
            scanner.nextLine(); // Consume newline
            if (warranty < 0) {
                warranty = electronics.getWarrantyPeriod();
            }

            updatedProduct = new Electronics(id, name, price, brandName, warranty);

        } else if (product instanceof Clothing) {
            Clothing clothing = (Clothing) product;

            System.out.print("Enter new sizes (or press Enter to keep current): ");
            String sizes = scanner.nextLine();
            if (sizes.isEmpty()) {
                sizes = clothing.getSizes();
            }

            System.out.print("Enter new material (or press Enter to keep current): ");
            String material = scanner.nextLine();
            if (material.isEmpty()) {
                material = clothing.getMaterial();
            }

            System.out.print("Enter new colors (or press Enter to keep current): ");
            String colors = scanner.nextLine();
            if (colors.isEmpty()) {
                colors = clothing.getColors();
            }

            updatedProduct = new Clothing(id, name, price, sizes, material, colors);

        } else if (product instanceof Grocery) {
            Grocery grocery = (Grocery) product;

            System.out.print("Enter new weight in kg (or -1 to keep current): ");
            double weight = getDoubleInput();
            scanner.nextLine(); // Consume newline
            if (weight < 0) {
                weight = grocery.getWeight();
            }

            System.out.print("Enter new expiration date (or press Enter to keep current): ");
            String expirationDate = scanner.nextLine();
            if (expirationDate.isEmpty()) {
                expirationDate = grocery.getExpirationDate();
            }

            updatedProduct = new Grocery(id, name, price, weight, expirationDate);
        }

        if (inventoryManager.updateProduct(id, updatedProduct)) {
            System.out.println("Product updated successfully!");
        } else {
            System.out.println("Failed to update product.");
        }
    }

    private void removeProduct() {
        System.out.print("Enter product ID to remove: ");
        String id = scanner.nextLine();

        if (inventoryManager.removeProduct(id)) {
            System.out.println("Product removed successfully!");
        } else {
            System.out.println("Product not found.");
        }
    }

    private void viewProductsByCategory() {
        System.out.println("\nSelect category:");
        System.out.println("1. Electronics");
        System.out.println("2. Clothing");
        System.out.println("3. Grocery");
        System.out.print("Enter choice: ");

        int choice = getIntInput();
        scanner.nextLine(); // Consume newline

        String category;
        switch (choice) {
            case 1:
                category = "Electronics";
                break;
            case 2:
                category = "Clothing";
                break;
            case 3:
                category = "Grocery";
                break;
            default:
                System.out.println("Invalid choice.");
                return;
        }

        List<Product> products = inventoryManager.getProductsByCategory(category);

        if (products.isEmpty()) {
            System.out.println("No products found in category: " + category);
            return;
        }

        System.out.println("\n===== " + category.toUpperCase() + " PRODUCTS =====");
        for (Product product : products) {
            System.out.println("\n" + product.getProductDetails());
            System.out.println("------------------------");
        }
    }

    private int getIntInput() {
        while (true) {
            try {
                return scanner.nextInt();
            } catch (Exception e) {
                scanner.nextLine(); // Clear the invalid input
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }

    private double getDoubleInput() {
        while (true) {
            try {
                return scanner.nextDouble();
            } catch (Exception e) {
                scanner.nextLine(); // Clear the invalid input
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }
}