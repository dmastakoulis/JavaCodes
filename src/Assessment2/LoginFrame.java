package Assessment2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {
    private DatabaseManager dbManager;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginFrame(DatabaseManager dbManager) {
        this.dbManager = dbManager;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Hangman - Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Hangman Game", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(titleLabel);
        panel.add(new JLabel()); // Empty space

        panel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                attemptLogin();
            }
        });
        panel.add(loginButton);

        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                attemptRegistration();
            }
        });
        panel.add(registerButton);

        add(panel);

        // Set Enter key to submit login
        getRootPane().setDefaultButton(loginButton);
    }

    private void attemptLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both username and password", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!dbManager.isConnected()) {
            JOptionPane.showMessageDialog(this, "Database connection failed. Please check your connection and try again.",
                    "Connection Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        User user = dbManager.authenticateUser(username, password);
        if (user != null) {
            dispose();
            new GameFrame(dbManager, user).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
            passwordField.setText("");
        }
    }

    private void attemptRegistration() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both username and password", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (username.length() < 3) {
            JOptionPane.showMessageDialog(this, "Username must be at least 3 characters long", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (password.length() < 4) {
            JOptionPane.showMessageDialog(this, "Password must be at least 4 characters long", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!dbManager.isConnected()) {
            JOptionPane.showMessageDialog(this, "Database connection failed. Please check your connection and try again.",
                    "Connection Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (dbManager.registerUser(username, password)) {
            JOptionPane.showMessageDialog(this, "Registration successful! Please login.", "Success", JOptionPane.INFORMATION_MESSAGE);
            passwordField.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Username already exists or registration failed", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}