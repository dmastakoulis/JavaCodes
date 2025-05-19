import java.util.Scanner;

// Pet class implementation
class Pet {
    private String species;
    private String name;

    // Constructor
    public Pet(String species, String name) {
        this.species = species;
        this.name = name;
    }

    // Getter methods
    public String getSpecies() {
        return species;
    }

    public String getName() {
        return name;
    }

    // Setter methods
    public void setSpecies(String species) {
        this.species = species;
    }

    public void setName(String name) {
        this.name = name;
    }

    // toString method for easy display
    @Override
    public String toString() {
        return "Pet{species='" + species + "', name='" + name + "'}";
    }
}

// PetClinic class with main function
public class PetClinic {
    public static void main(String[] args) {
        // Create an array of 10 pets
        Pet[] pets = new Pet[10];

        // Initialize the pets array with sample data
        pets[0] = new Pet("dog", "Pluto");
        pets[1] = new Pet("cat", "Lily");
        pets[2] = new Pet("dog", "Jack");
        pets[3] = new Pet("rabbit", "Bugs");
        pets[4] = new Pet("dog", "Ruby");
        pets[5] = new Pet("cat", "Kittens");
        pets[6] = new Pet("turtle", "Thomas");
        pets[7] = new Pet("duck", "Donald");
        pets[8] = new Pet("cat", "Felix");
        pets[9] = new Pet("rabbit", "Prat");

        // Create scanner for user input
        Scanner scanner = new Scanner(System.in);

        // Ask user for the type of pet to search for
        System.out.print("Give type: ");
        String searchType = scanner.nextLine().toLowerCase().trim();

        // Find and display pets of the specified type
        int count = 0;
        boolean found = false;

        for (Pet pet : pets) {
            if (pet != null && pet.getSpecies().toLowerCase().equals(searchType)) {
                if (!found) {
                    // This is the first pet of this type found
                    found = true;
                }
                System.out.println("- " + pet.getName());
                count++;
            }
        }

        // Display total count
        if (found) {
            System.out.println("Total animals of " + searchType + " type: " + count);
        } else {
            System.out.println("No animals of " + searchType + " type found.");
        }

        // Close scanner
        scanner.close();
    }
}