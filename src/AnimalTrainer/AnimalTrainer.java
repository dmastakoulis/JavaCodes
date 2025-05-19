package AnimalTrainer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class AnimalTrainer extends JFrame {
    private ArrayList<Animal> animals;
    private int currentIndex = 0;

    private JLabel imageLabel;
    private JTextField inputField;
    private JLabel feedbackLabel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AnimalTrainer().setVisible(true));
    }

    public AnimalTrainer() {
        setTitle("Preschool Letter Training");
        setSize(500, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        animals = new ArrayList<>();
        animals.add(new Animal("Elephant", "elephant.jpg"));
        animals.add(new Animal("Cat", "cat.jpg"));
        animals.add(new Animal("Tiger", "tiger.jpg"));
        animals.add(new Animal("Shark", "shark.jpg"));
        animals.add(new Animal("Fox", "fox.jpg"));

        initComponents();
        loadAnimal(currentIndex);
    }

    private void initComponents() {
        imageLabel = new JLabel("", SwingConstants.CENTER);
        inputField = new JTextField(5);
        feedbackLabel = new JLabel(" ", SwingConstants.CENTER);

        JButton checkButton = new JButton("Check");
        JButton nextButton = new JButton("Next");

        checkButton.addActionListener(e -> checkAnswer());
        nextButton.addActionListener(e -> loadNextAnimal());

        JPanel inputPanel = new JPanel();
        inputPanel.add(inputField);
        inputPanel.add(checkButton);
        inputPanel.add(nextButton);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(feedbackLabel, BorderLayout.NORTH);
        mainPanel.add(imageLabel, BorderLayout.CENTER);
        mainPanel.add(inputPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void loadAnimal(int index) {

        Animal animal = animals.get(index);
        java.net.URL imgURL = getClass().getClassLoader().getResource("images/" + animal.getImageFileName());


        if (imgURL == null) {
            System.out.println("Image not found: " + animal.getImageFileName());
            imageLabel.setIcon(null);
            imageLabel.setText("Image not found for " + animal.getName());
            return;
        }

        ImageIcon icon = new ImageIcon(imgURL);
        Image scaled = icon.getImage().getScaledInstance(300, 250, Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(scaled));
        imageLabel.setText("");
        feedbackLabel.setText(" ");
        inputField.setText("");
    }

    private void checkAnswer() {
        String input = inputField.getText().trim().toUpperCase();
        Animal animal = animals.get(currentIndex);

        if (!input.isEmpty() && input.charAt(0) == animal.getName().toUpperCase().charAt(0)) {
            feedbackLabel.setText("Correct! " + animal.getName() + " starts with " + input);
        } else {
            feedbackLabel.setText("Try again. Hint: " + animal.getName());
        }
    }

    private void loadNextAnimal() {
        currentIndex = (currentIndex + 1) % animals.size();
        loadAnimal(currentIndex);
    }
}

class Animal {
    private final String name;
    private final String imageFileName;

    public Animal(String name, String imageFileName) {
        this.name = name;
        this.imageFileName = imageFileName;
    }

    public String getName() {
        return name;
    }

    public String getImageFileName() {
        return imageFileName;
    }
}
