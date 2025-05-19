package Homework1CarRental;

public class Car {
    private String plateNumber;
    private String model;
    private int kilometers;
    private boolean rented;

    // Constructor
    public Car(String plateNumber, String model, int kilometers) {
        this.plateNumber = plateNumber;
        this.model = model;
        this.kilometers = kilometers;
        this.rented = false; // Initially not rented
    }

    // Getters
    public String getPlateNumber() {
        return plateNumber;
    }

    public String getModel() {
        return model;
    }

    public int getKilometers() {
        return kilometers;
    }

    public boolean isRented() {
        return rented;
    }

    // Method to rent the car
    public boolean rentCar() {
        if (!rented) {
            rented = true;
            return true; // Successfully rented
        }
        return false; // Car is already rented
    }

    // Method to return the car with updated kilometers
    public boolean returnCar(int newKilometers) {
        if (rented) {
            rented = false;
            if (newKilometers > kilometers) {
                kilometers = newKilometers;
            }
            return true; // Successfully returned
        }
        return false; // Car was not rented
    }

    // toString method for displaying car information
    @Override
    public String toString() {
        return String.format("Plate: %s | Model: %s | KM: %d | Status: %s",
                plateNumber, model, kilometers,
                rented ? "RENTED" : "AVAILABLE");
    }
}