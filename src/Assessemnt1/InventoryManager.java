package Assessemnt1;

// InventoryManager.java
// Manages the inventory operations
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class InventoryManager {
    private List<Product> inventory;
    private int nextId;

    public InventoryManager() {
        this.inventory = new ArrayList<>();
        this.nextId = 1;

        // Add some sample products
        addSampleProducts();
    }

    private void addSampleProducts() {
        // Sample Electronics
        addProduct(new Electronics(generateId(), "Laptop", 999.99, "Dell", 24));
        addProduct(new Electronics(generateId(), "Smartphone", 699.99, "Samsung", 12));

        // Sample Clothing
        addProduct(new Clothing(generateId(), "T-Shirt", 19.99, "S,M,L,XL", "Cotton", "Black,White,Blue"));
        addProduct(new Clothing(generateId(), "Jeans", 49.99, "30,32,34", "Denim", "Blue,Black"));

        // Sample Grocery
        addProduct(new Grocery(generateId(), "Apples", 3.99, 1.0, "2025-05-20"));
        addProduct(new Grocery(generateId(), "Milk", 2.49, 1.0, "2025-05-10"));
    }

    private String generateId() {
        return "P" + String.format("%03d", nextId++);
    }

    public void addProduct(Product product) {
        inventory.add(product);
    }

    public boolean updateProduct(String id, Product updatedProduct) {
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).getId().equals(id)) {
                inventory.set(i, updatedProduct);
                return true;
            }
        }
        return false;
    }

    public boolean removeProduct(String id) {
        return inventory.removeIf(product -> product.getId().equals(id));
    }

    public Product getProductById(String id) {
        return inventory.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(inventory);
    }

    public List<Product> searchProductsByName(String name) {
        String searchLower = name.toLowerCase();
        return inventory.stream()
                .filter(product -> product.getName().toLowerCase().contains(searchLower))
                .collect(Collectors.toList());
    }

    public List<Product> getProductsByCategory(String category) {
        return inventory.stream()
                .filter(product -> product.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    public int getInventorySize() {
        return inventory.size();
    }
}