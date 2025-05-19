package Homework1CarRental;

public class CarRental {
    private CarManager carManager;
    private MenuHandler menuHandler;

    // Constructor
    public CarRental() {
        carManager = new CarManager();
        menuHandler = new MenuHandler(carManager);
    }

    // Main application loop
    public void run() {
        System.out.println("Welcome to Car Rental System!");

        while (true) {
            menuHandler.displayMenu();
            String choice = menuHandler.getUserChoice();

            switch (choice) {
                case "1":
                    menuHandler.handleRentCar();
                    break;
                case "2":
                    menuHandler.handleReturnCar();
                    break;
                case "3":
                    menuHandler.handleDisplayAllCars();
                    break;
                case "4":
                    System.out.println("Thank you for using Car Rental System!");
                    menuHandler.closeScanner();
                    return;
                default:
                    System.out.println("Invalid option! Please choose 1-4.");
            }
        }
    }

    // Main method
    public static void main(String[] args) {
        CarRental carRental = new CarRental();
        carRental.run();
    }
}