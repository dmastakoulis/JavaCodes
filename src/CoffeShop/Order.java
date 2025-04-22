package CoffeShop;

public class Order {
    private String customerName;
    private double totalAmount;
    private String date; // Format: yyyy/mm/dd

    public Order(String customerName, double totalAmount, String date) {
        this.customerName = customerName;
        this.totalAmount = totalAmount;
        this.date = date;
    }

    public String getCustomerName() {
        return customerName;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getDate() {
        return date;
    }
}
