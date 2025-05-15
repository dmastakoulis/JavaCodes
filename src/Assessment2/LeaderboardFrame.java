package Assessment2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LeaderboardFrame extends JFrame {
    public LeaderboardFrame(DatabaseManager dbManager) {
        setTitle("Leaderboard");
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] columnNames = {"Rank", "Username", "Wins", "Score"};
        Object[][] data = getLeaderboardData(dbManager);

        JTable table = new JTable(data, columnNames);
        table.setEnabled(false);

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Add close button
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(closeButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);
    }

    private Object[][] getLeaderboardData(DatabaseManager dbManager) {
        Object[][] data = new Object[10][4];
        // Initialize with blank data
        for (int i = 0; i < 10; i++) {
            data[i][0] = i + 1;
            data[i][1] = "";
            data[i][2] = 0;
            data[i][3] = 0;
        }

        try (ResultSet rs = dbManager.getLeaderboard()) {
            int rank = 1;
            while (rs.next() && rank <= 10) {
                data[rank-1][0] = rank;
                data[rank-1][1] = rs.getString("username");
                data[rank-1][2] = rs.getInt("games_won");
                data[rank-1][3] = rs.getInt("score");
                rank++;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Error loading leaderboard: " + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return data;
    }
}