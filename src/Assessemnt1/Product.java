package Assessemnt1;

// Product.java
// Product abstract class - base class for all products
abstract class Product {
    private String id;
    private String name;
    private double price;
    private String category;

    public Product(String id, String name, double price, String category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
    }

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getCategory() { return category; }

    // Abstract method that child classes will implement to show specific details
    public abstract String getProductDetails();

    @Override
    public String toString() {
        return "ID: " + id +
                "\nName: " + name +
                "\nPrice: $" + String.format("%.2f", price) +
                "\nCategory: " + category;
    }
}