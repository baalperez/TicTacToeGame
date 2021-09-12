package com.company.TTT;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class Game {

    Position position = new Position();
    JButton[] buttons = new JButton[9];
    private int roundCount = 0;
    private int playerWinsCount = 0;
    private int pcWinsCount = 0;
    private int drawsCount = 0;
    JLabel playerName = new JLabel();
    JLabel comp = new JLabel();
    JLabel playerScore = new JLabel();
    JLabel compScore = new JLabel();
    JLabel round = new JLabel();
    JLabel numberOfRound = new JLabel();
    JPanel panel = new JPanel();
    private int best;

    public Game() {
    }

    public int getRoundCount() {
        return roundCount;
    }

    public int getPlayerWinsCount() {
        return playerWinsCount;
    }

    public int getPcWinsCount() {
        return pcWinsCount;
    }

    public int getDrawsCount() {
        return drawsCount;
    }

    public void setRoundCount(int roundCount) {
        this.roundCount = roundCount;
    }

    public void setPlayerWinsCount(int playerWinsCount) {
        this.playerWinsCount = playerWinsCount;
    }

    public void setPcWinsCount(int pcWinsCount) {
        this.pcWinsCount = pcWinsCount;
    }

    public void setDrawsCount(int drawsCount) {
        this.drawsCount = drawsCount;
    }

    public JPanel buttonsPanel() {
        panel.removeAll();
        panel.setBounds(180, 210, 220, 220);
        panel.setLayout(new GridLayout(3, 3));
        for (int i = 0; i < 9; i++) {
            final int idx = i;
            JButton button = new JButton();
            buttons[i] = button;
            panel.add(button);

            Border border = BorderFactory.createLineBorder(Color.BLUE, 3);
            button.setBorder(border);
            button.setFont(new Font("Arial", Font.PLAIN, 80));

            button.addActionListener(e -> {
                button.setText("" + position.turn);
                ((JButton) e.getSource()).setEnabled(false);
                move(idx);
                if (!position.endGame()) {
                    best = position.bestMove();
                    buttons[best].setText("" + position.turn);
                    move(best);
                    buttons[best].setEnabled(false);
                }
                if (position.endGame()) {
                    String message;
                    if (position.win('x')) {
                        message = MainFrame.playerName + " won!";
                        playerWinsCount++;
                        roundCount++;
                    } else if (position.win('o')) {
                        message = "Computer won!";
                        pcWinsCount++;
                        roundCount++;
                    } else {
                        message = "Draw.";
                        roundCount++;
                        drawsCount++;
                    }
                    JOptionPane.showMessageDialog(null,
                            "Round: " + roundCount +
                                    ". " + message);
                    clearBoard();
                    playerScoreLabel();
                    compScoreLabel();
                    numberOfRoundLabel();
                    for (JButton button1 : buttons) {
                        button1.setEnabled(false);
                    }
                }
            });
        }
        return panel;
    }

    public void move(int idx) {
        position = position.move(idx);
    }

    public void clearBoard() {
        for (JButton button1 : buttons) {
            button1.setEnabled(true);
            button1.setText("");
            position.board = "         ".toCharArray();
            position.turn = 'x';
        }
    }

    public JLabel roundLabel() {
        round.setText("Round: ");
        round.setForeground(Color.YELLOW);
        round.setBounds(420, 205, 200, 50);
        round.setFont(new Font("Arial", Font.BOLD, 26));
        return round;
    }

    public JLabel numberOfRoundLabel() {
        numberOfRound.setText("" + roundCount);
        numberOfRound.setForeground(Color.YELLOW);
        numberOfRound.setBounds(650, 205, 30, 50);
        numberOfRound.setFont(new Font("Arial", Font.BOLD, 26));
        return numberOfRound;
    }

    public JLabel playerNameLabel() {
        playerName.setText(MainFrame.playerName.toUpperCase());
        playerName.setForeground(Color.YELLOW);
        playerName.setBounds(420, 248, 200, 50);
        playerName.setFont(new Font("Arial", Font.BOLD, 26));
        return playerName;
    }

    public JLabel playerScoreLabel() {
        playerScore.setText("" + playerWinsCount);
        playerScore.setForeground(Color.YELLOW);
        playerScore.setBounds(650, 248, 30, 50);
        playerScore.setFont(new Font("Arial", Font.BOLD, 26));
        return playerScore;
    }

    public JLabel compLabel() {
        comp.setText("COMPUTER");
        comp.setForeground(Color.YELLOW);
        comp.setBounds(420, 291, 200, 50);
        comp.setFont(new Font("Arial", Font.BOLD, 26));
        return comp;
    }

    public JLabel compScoreLabel() {
        compScore.setText("" + pcWinsCount);
        compScore.setForeground(Color.YELLOW);
        compScore.setBounds(650, 291, 30, 50);
        compScore.setFont(new Font("Arial", Font.BOLD, 26));
        return compScore;
    }
}

