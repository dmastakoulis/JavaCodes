package Assessment2;

public class User {
    private int id;
    private String username;
    private int gamesPlayed;
    private int gamesWon;
    private int score;

    public User(int id, String username, int gamesPlayed, int gamesWon, int score) {
        this.id = id;
        this.username = username;
        this.gamesPlayed = gamesPlayed;
        this.gamesWon = gamesWon;
        this.score = score;
    }

    // Getters and Setters
    public int getId() { return id; }
    public String getUsername() { return username; }
    public int getGamesPlayed() { return gamesPlayed; }
    public int getGamesWon() { return gamesWon; }
    public int getScore() { return score; }

    public void incrementGamesPlayed() { gamesPlayed++; }
    public void incrementGamesWon() { gamesWon++; }
    public void addScore(int points) {
        if (points > 0) {
            score += points;
        }
    }

    /**
     * Returns win percentage as a value between 0 and 100.
     * If no games have been played, returns 0.
     */
    public int getWinPercentage() {
        if (gamesPlayed == 0) {
            return 0;
        }
        return (int)((double)gamesWon / gamesPlayed * 100);
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", gamesPlayed=" + gamesPlayed +
                ", gamesWon=" + gamesWon +
                ", score=" + score +
                '}';
    }
}