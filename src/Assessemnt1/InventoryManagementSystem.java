package Assessemnt1;

// InventoryManagementSystem.java
// Main application class
public class InventoryManagementSystem {
    public static void main(String[] args) {
        InventoryManager manager = new InventoryManager();
        UserInterface ui = new UserInterface(manager);
        ui.displayMainMenu();
    }
}