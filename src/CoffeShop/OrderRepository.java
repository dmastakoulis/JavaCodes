package CoffeShop;

import java.util.List;

public interface OrderRepository {
    void addOrder(Order order);
    List<Order> getOrdersByDate(String date);
    double getTotalAmountByCustomer(String customerName);
}
