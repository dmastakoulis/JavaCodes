package Assessemnt1;

// Grocery.java
// Grocery product class
class Grocery extends Product {
    private double weight; // in kg
    private String expirationDate;

    public Grocery(String id, String name, double price,
                   double weight, String expirationDate) {
        super(id, name, price, "Grocery");
        this.weight = weight;
        this.expirationDate = expirationDate;
    }

    // Getters and setters
    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }

    public String getExpirationDate() { return expirationDate; }
    public void setExpirationDate(String expirationDate) { this.expirationDate = expirationDate; }

    @Override
    public String getProductDetails() {
        return toString() +
                "\nWeight: " + String.format("%.2f", weight) + " kg" +
                "\nExpiration Date: " + expirationDate;
    }
}
