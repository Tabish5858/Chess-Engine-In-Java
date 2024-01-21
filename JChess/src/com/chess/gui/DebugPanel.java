package com.chess.gui;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.*;
import com.LoginFrom.login;
public class DebugPanel extends JPanel {

    private static final Dimension CHAT_PANEL_DIMENSION = new Dimension(600, 150);
    private static JLabel currentUserLabel;
    private final JLabel opponentLabel;
    private final JLabel checkPerformanceButton;
    private final JOptionPane JOptionPane;

    public DebugPanel() {
        this.checkPerformanceButton = new JLabel("Check Performance");
        this.JOptionPane = new JOptionPane();
        this.setLayout(new GridLayout(1, 2)); // Set the column count to 2
        currentUserLabel = new JLabel();
        this.opponentLabel = new JLabel();
        styleLabels(currentUserLabel, opponentLabel);
        this.setPreferredSize(CHAT_PANEL_DIMENSION);
        this.add(currentUserLabel);
        this.checkPerformanceButton.setSize(20,20);
        this.checkPerformanceButton.setForeground(Color.WHITE);
        this.add(checkPerformanceButton); // Added checkPerformanceButton here
        this.add(opponentLabel);
        this.setBackground(Color.BLACK);
        redo();
    }
    private static Connection getDBConnection() throws SQLException {
        String url = "jdbc:mysql://localhost/chess";
        String user = "root";
        String password = "";
        return DriverManager.getConnection(url, user, password);
    }
    public static int getEloFromDatabase(String userName) {
        try (Connection connection = getDBConnection()) {
            String query = "SELECT elo FROM player WHERE username = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, userName);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getInt("elo");
                    } else {
                        return 0;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
    public static int getWinFromDatabase(String userName) {
        try (Connection connection = getDBConnection()) {
            String query = "SELECT won FROM player WHERE username = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, userName);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getInt("won");
                    } else {
                        return 0;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
    public static int getLoseFromDatabase(String userName) {
        try (Connection connection = getDBConnection()) {
            String query = "SELECT lost FROM player WHERE username = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, userName);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getInt("lost");
                    } else {
                        return 0;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
    public static int getPlayedFromDatabase(String userName) {
        try (Connection connection = getDBConnection()) {
            String query = "SELECT played FROM player WHERE username = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, userName);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getInt("played");
                    } else {
                        return 0;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
    public static int getDrawFromDatabase(String userName) {
        try (Connection connection = getDBConnection()) {
            String query = "SELECT draw FROM player WHERE username = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, userName);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getInt("draw");
                    } else {
                        return 0;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
    public void redo() {
        String currentUserName = login.getPlayerOneName();
        String currentUserElo = String.valueOf(getEloFromDatabase(currentUserName));

        String opponentName = "AI Bot";
        String opponentElo = String.valueOf(1500);


        currentUserLabel.setText("User: \n" +
                currentUserName +
                " \n(Elo: " + currentUserElo + ")\n"
                );
        checkPerformanceButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showPerformance();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                checkPerformanceButton.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

            }


            @Override
            public void mouseEntered(MouseEvent e) {
                checkPerformanceButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        opponentLabel.setText("Opponent: \n" + opponentName + " \n(Elo: " + opponentElo + ")");
    }

    public void showPerformance() {
        String currentUserName = login.getPlayerOneName();
        int played = getPlayedFromDatabase(currentUserName);
        int won = getWinFromDatabase(currentUserName);
        int draw = getDrawFromDatabase(currentUserName);
        int lose = getLoseFromDatabase(currentUserName);

        String message = "Performance: \n" +
                "Matches Played: " + played + "\n" +
                "Won: " + won + "\n" +
                "Drawn: " + draw + "\n" +
                "Lost: " + lose + "\n";

        javax.swing.JOptionPane.showMessageDialog(JOptionPane, message, "Your Performance", javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    private void styleLabels(JLabel... labels) {
        for (JLabel label : labels) {
            label.setFont(new Font("Arial", Font.BOLD, 16));
            label.setForeground(Color.WHITE);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            label.setBackground(Color.BLACK);
        }
    }

}