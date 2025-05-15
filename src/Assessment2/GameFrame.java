package Assessment2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashSet;
import java.util.Set;

public class GameFrame extends JFrame {
    private DatabaseManager dbManager;
    private User user;
    private GameSession gameSession;

    private JLabel wordLabel;
    private JLabel attemptsLabel;
    private JLabel scoreLabel;
    private JPanel lettersPanel;
    private JPanel hangmanPanel;
    private JButton[] letterButtons;
    private JButton newGameButton;
    private JButton leaderboardButton;
    private JButton logoutButton;

    public GameFrame(DatabaseManager dbManager, User user) {
        this.dbManager = dbManager;
        this.user = user;
        this.gameSession = new GameSession(new WordAPI().getRandomWord());
        initializeUI();
        updateGameDisplay();

        // Save user stats when window is closed
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dbManager.updateUserStats(user);
            }
        });
    }

    private void initializeUI() {
        setTitle("Hangman - Playing as " + user.getUsername());
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Top panel for game info
        JPanel infoPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        wordLabel = new JLabel("", SwingConstants.CENTER);
        wordLabel.setFont(new Font("Arial", Font.BOLD, 24));
        infoPanel.add(wordLabel);

        attemptsLabel = new JLabel("Attempts left: " + gameSession.getAttemptsLeft(), SwingConstants.CENTER);
        infoPanel.add(attemptsLabel);

        scoreLabel = new JLabel("Score: " + user.getScore(), SwingConstants.CENTER);
        infoPanel.add(scoreLabel);

        mainPanel.add(infoPanel, BorderLayout.NORTH);

        // Center panel for hangman drawing
        hangmanPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawHangman(g);
            }
        };
        hangmanPanel.setPreferredSize(new Dimension(200, 200));
        mainPanel.add(hangmanPanel, BorderLayout.CENTER);

        // Letters panel
        lettersPanel = new JPanel(new GridLayout(4, 7, 5, 5));
        letterButtons = new JButton[26];
        for (int i = 0; i < 26; i++) {
            char c = (char) ('A' + i);
            letterButtons[i] = new JButton(String.valueOf(c));
            letterButtons[i].addActionListener(new LetterButtonListener(c));
            lettersPanel.add(letterButtons[i]);
        }
        mainPanel.add(lettersPanel, BorderLayout.SOUTH);

        // Bottom panel for controls
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        newGameButton = new JButton("New Game");
        newGameButton.addActionListener(e -> newGame());
        controlPanel.add(newGameButton);

        leaderboardButton = new JButton("Leaderboard");
        leaderboardButton.addActionListener(e -> showLeaderboard());
        controlPanel.add(leaderboardButton);

        logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> logout());
        controlPanel.add(logoutButton);

        // Add control panel to EAST of mainPanel instead of overwriting the SOUTH position
        mainPanel.add(controlPanel, BorderLayout.EAST);

        add(mainPanel);
    }

    private void updateGameDisplay() {
        wordLabel.setText(gameSession.getDisplayWord());
        attemptsLabel.setText("Attempts left: " + gameSession.getAttemptsLeft());
        scoreLabel.setText("Score: " + user.getScore());

        // Disable guessed letters
        Set<Character> guessedLetters = gameSession.getGuessedLetters();
        for (int i = 0; i < 26; i++) {
            char c = (char) ('A' + i);
            letterButtons[i].setEnabled(!guessedLetters.contains(Character.toLowerCase(c)));
        }

        hangmanPanel.repaint();

        // Check game status
        if (gameSession.isGameOver()) {
            if (gameSession.isWon()) {
                JOptionPane.showMessageDialog(this, "Congratulations! You won!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
                user.incrementGamesWon();
                user.addScore(10); // 10 points for winning
            } else {
                JOptionPane.showMessageDialog(this, "Game over! The word was: " + gameSession.getWord(), "Game Over", JOptionPane.INFORMATION_MESSAGE);
            }
            user.incrementGamesPlayed();
            dbManager.updateUserStats(user);

            // Disable all letter buttons
            for (JButton button : letterButtons) {
                button.setEnabled(false);
            }
        }
    }

    private void drawHangman(Graphics g) {
        int mistakes = 6 - gameSession.getAttemptsLeft();

        g.setColor(Color.BLACK);

        // Draw gallows
        g.fillRect(50, 30, 150, 10);  // Top
        g.fillRect(50, 30, 10, 200);  // Left post
        g.fillRect(50, 230, 100, 10); // Bottom
        g.fillRect(150, 30, 10, 50);  // Rope

        if (mistakes > 0) {
            // Head
            g.drawOval(125, 80, 50, 50);
        }
        if (mistakes > 1) {
            // Body
            g.drawLine(150, 130, 150, 180);
        }
        if (mistakes > 2) {
            // Left arm
            g.drawLine(150, 140, 120, 160);
        }
        if (mistakes > 3) {
            // Right arm
            g.drawLine(150, 140, 180, 160);
        }
        if (mistakes > 4) {
            // Left leg
            g.drawLine(150, 180, 120, 210);
        }
        if (mistakes > 5) {
            // Right leg
            g.drawLine(150, 180, 180, 210);
        }
    }

    private void newGame() {
        this.gameSession = new GameSession(new WordAPI().getRandomWord());
        // Re-enable all letter buttons
        for (JButton button : letterButtons) {
            button.setEnabled(true);
        }
        updateGameDisplay();
    }

    private void showLeaderboard() {
        new LeaderboardFrame(dbManager).setVisible(true);
    }

    private void logout() {
        dbManager.updateUserStats(user);
        dispose();
        new LoginFrame(dbManager).setVisible(true);
    }

    private class LetterButtonListener implements ActionListener {
        private char letter;

        public LetterButtonListener(char letter) {
            this.letter = letter;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            gameSession.guessLetter(letter);
            updateGameDisplay();
        }
    }
}