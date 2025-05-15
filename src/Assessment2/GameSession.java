package Assessment2;

import java.util.HashSet;
import java.util.Set;

public class GameSession {
    private String word;
    private Set<Character> guessedLetters;
    private int attemptsLeft;
    private boolean gameOver;
    private boolean won;

    public GameSession(String word) {
        this.word = word != null ? word.toLowerCase() : "";
        this.guessedLetters = new HashSet<>();
        this.attemptsLeft = 6;
        this.gameOver = false;
        this.won = false;
    }

    public String getWord() {
        return word;
    }

    public Set<Character> getGuessedLetters() {
        return guessedLetters;
    }

    public int getAttemptsLeft() {
        return attemptsLeft;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isWon() {
        return won;
    }

    public String getDisplayWord() {
        StringBuilder display = new StringBuilder();

        if (word == null || word.isEmpty()) {
            return "";
        }

        // Show first letter
        char firstChar = word.charAt(0);
        display.append(guessedLetters.contains(firstChar) ? firstChar : '_');

        // Show middle letters
        for (int i = 1; i < word.length() - 1; i++) {
            char c = word.charAt(i);
            display.append(' ');
            display.append(guessedLetters.contains(c) ? c : '_');
        }

        // Show last letter if word has more than one character
        if (word.length() > 1) {
            char lastChar = word.charAt(word.length() - 1);
            display.append(' ');
            display.append(guessedLetters.contains(lastChar) ? lastChar : '_');
        }

        return display.toString().toUpperCase();
    }

    public void guessLetter(char letter) {
        if (gameOver || guessedLetters.contains(Character.toLowerCase(letter))) {
            return;
        }

        guessedLetters.add(Character.toLowerCase(letter));

        if (!word.contains(String.valueOf(Character.toLowerCase(letter)))) {
            attemptsLeft--;
        }

        checkGameStatus();
    }

    private void checkGameStatus() {
        // Check if won
        boolean allLettersGuessed = true;
        for (char c : word.toCharArray()) {
            if (!guessedLetters.contains(c)) {
                allLettersGuessed = false;
                break;
            }
        }

        if (allLettersGuessed) {
            won = true;
            gameOver = true;
            return;
        }

        // Check if lost
        if (attemptsLeft <= 0) {
            gameOver = true;
            won = false;
        }
    }
}