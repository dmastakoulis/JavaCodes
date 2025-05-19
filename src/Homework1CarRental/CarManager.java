package Homework1CarRental;

public class CarManager {
    private Car[] cars;

    // Constructor to initialize the fleet of cars
    public CarManager() {
        cars = new Car[5];
        cars[0] = new Car("ABC123", "Toyota Camry", 50000);
        cars[1] = new Car("XYZ789", "Honda Accord", 30000);
        cars[2] = new Car("DEF456", "Ford Focus", 45000);
        cars[3] = new Car("GHI012", "Chevrolet Malibu", 25000);
        cars[4] = new Car("JKL345", "Mustang GT", 55000);
    }

    // Find car by plate number
    public Car findCarByPlate(String plateNumber) {
        for (Car car : cars) {
            if (car.getPlateNumber().equalsIgnoreCase(plateNumber)) {
                return car;
            }
        }
        return null;
    }

    // Rent a car by plate number
    public boolean rentCar(String plateNumber) {
        Car car = findCarByPlate(plateNumber);
        if (car == null) {
            return false; // Car not found
        }
        return car.rentCar(); // Returns true if successfully rented, false if already rented
    }

    // Return a car by plate number with updated kilometers
    public boolean returnCar(String plateNumber, int newKilometers) {
        Car car = findCarByPlate(plateNumber);
        if (car == null) {
            return false; // Car not found
        }
        return car.returnCar(newKilometers); // Returns true if successfully returned, false if not rented
    }

    // Get all cars
    public Car[] getAllCars() {
        return cars;
    }

    // Get number of available cars
    public int getAvailableCarsCount() {
        int count = 0;
        for (Car car : cars) {
            if (!car.isRented()) {
                count++;
            }
        }
        return count;
    }

    // Get number of rented cars
    public int getRentedCarsCount() {
        int count = 0;
        for (Car car : cars) {
            if (car.isRented()) {
                count++;
            }
        }
        return count;
    }
}