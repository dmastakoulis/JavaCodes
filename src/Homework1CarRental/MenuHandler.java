package Homework1CarRental;

import java.util.Scanner;

public class MenuHandler {
    private CarManager carManager;
    private Scanner scanner;

    // Constructor
    public MenuHandler(CarManager carManager) {
        this.carManager = carManager;
        this.scanner = new Scanner(System.in);
    }

    // Display the main menu
    public void displayMenu() {
        System.out.println("\n=== Car Rental System ===");
        System.out.println("1. Rent a car");
        System.out.println("2. Return a car");
        System.out.println("3. Display all cars");
        System.out.println("4. Exit");
        System.out.print("Choose an option (1-4): ");
    }

    // Handle rent car option
    public void handleRentCar() {
        System.out.print("Enter plate number: ");
        String plateNumber = scanner.nextLine().trim();

        Car car = carManager.findCarByPlate(plateNumber);
        if (car == null) {
            System.out.println("Car with plate number " + plateNumber + " not found!");
            return;
        }

        if (carManager.rentCar(plateNumber)) {
            System.out.println("Car rented successfully!");
            System.out.println(car);
        } else {
            System.out.println("Car is not available (already rented)!");
        }
    }

    // Handle return car option
    public void handleReturnCar() {
        System.out.print("Enter plate number: ");
        String plateNumber = scanner.nextLine().trim();

        Car car = carManager.findCarByPlate(plateNumber);
        if (car == null) {
            System.out.println("Car with plate number " + plateNumber + " not found!");
            return;
        }

        if (!car.isRented()) {
            System.out.println("Car is not currently rented!");
            return;
        }

        System.out.print("Enter new kilometers: ");
        try {
            int newKilometers = Integer.parseInt(scanner.nextLine().trim());
            if (newKilometers < car.getKilometers()) {
                System.out.println("Warning: New kilometers (" + newKilometers +
                        ") is less than current kilometers (" + car.getKilometers() + ")");
                System.out.println("Kilometers will not be updated.");
            }

            if (carManager.returnCar(plateNumber, newKilometers)) {
                System.out.println("Car returned successfully!");
                System.out.println(car);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter a valid number for kilometers.");
        }
    }

    // Handle display all cars option
    public void handleDisplayAllCars() {
        Car[] cars = carManager.getAllCars();

        System.out.println("\n=== All Cars ===");
        for (int i = 0; i < cars.length; i++) {
            System.out.println((i + 1) + ". " + cars[i]);
        }

        // Display summary
        System.out.println("\n=== Summary ===");
        System.out.println("Available cars: " + carManager.getAvailableCarsCount());
        System.out.println("Rented cars: " + carManager.getRentedCarsCount());
        System.out.println();
    }

    // Get user choice
    public String getUserChoice() {
        return scanner.nextLine().trim();
    }

    // Close scanner
    public void closeScanner() {
        scanner.close();
    }
}