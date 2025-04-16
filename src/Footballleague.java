
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Game {
    String homeTeam;
    String awayTeam;
    int homeScore;
    int awayScore;

    public Game(String homeTeam, String awayTeam, int homeScore, int awayScore) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
    }
}

class TeamPerformance {
    int wins;
    int losses;
    int draws;

    public void addWin() { wins++; }
    public void addLoss() { losses++; }
    public void addDraw() { draws++; }

    @Override
    public String toString() {
        return "Wins: " + wins + ", Losses: " + losses + ", Draws: " + draws;
    }
}

class LeagueSystem {
    private List<Game> games = new ArrayList<>();

    public void addGame(String homeTeam, String awayTeam, int homeScore, int awayScore) {
        games.add(new Game(homeTeam, awayTeam, homeScore, awayScore));
    }

    public TeamPerformance getTeamPerformance(String teamName) {
        TeamPerformance performance = new TeamPerformance();

        for (Game game : games) {
            boolean isHome = game.homeTeam.equalsIgnoreCase(teamName);
            boolean isAway = game.awayTeam.equalsIgnoreCase(teamName);

            if (isHome || isAway) {
                int teamScore = isHome ? game.homeScore : game.awayScore;
                int opponentScore = isHome ? game.awayScore : game.homeScore;

                if (teamScore > opponentScore) {
                    performance.addWin();
                } else if (teamScore < opponentScore) {
                    performance.addLoss();
                } else {
                    performance.addDraw();
                }
            }
        }

        return performance;
    }
}

public class Footballleague {
    public static void main(String[] args) {
        LeagueSystem league = new LeagueSystem();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nFootball League of Elbonia");
            System.out.println("1. Add Game");
            System.out.println("2. Team Performance");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");https://github.com/dmastakoulis/Java-codes.git
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter home team: ");
                    String home = scanner.nextLine();
                    System.out.print("Enter away team: ");
                    String away = scanner.nextLine();
                    System.out.print("Enter home score: ");
                    int homeScore = scanner.nextInt();
                    System.out.print("Enter away score: ");
                    int awayScore = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    league.addGame(home, away, homeScore, awayScore);
                    System.out.println("Game added successfully.");
                }
                case 2 -> {
                    System.out.print("Enter team name: ");
                    String teamName = scanner.nextLine();
                    TeamPerformance performance = league.getTeamPerformance(teamName);
                    System.out.println("Performance of " + teamName + ":");
                    System.out.println(performance);
                }
                case 0 -> System.out.println("Exiting... Goodbye!");
                default -> System.out.println("Invalid option. Try again.");
            }
        } while (choice != 0);
    }
}
