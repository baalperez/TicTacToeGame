package com.company.TTT;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class MainFrame {

    Game game = new Game();
    Position position = new Position();
    static String playerName;
    Player player = new Player();

    public MainFrame() {
        final JFrame frame = new JFrame();
        frame.setTitle("TicTacToeGame");
        JLayeredPane layeredPane = frame.getLayeredPane();
        JLabel backgroundLabel = new JLabel(new ImageIcon("Files/start1.jpg"));
        frame.add(backgroundLabel);

        Border border = BorderFactory.createLineBorder(Color.black, 2);
        JButton newGameButton = new JButton("NEW GAME");
        newGameButton.setBackground(new Color(255, 230, 0));
        newGameButton.setBounds(258, 210, 220, 60);
        newGameButton.setBorder(border);
        newGameButton.setFont(new Font("Arial", Font.BOLD, 14));
        layeredPane.add(newGameButton);

        JButton rulesButton = new JButton("RULES");
        rulesButton.setBackground(new Color(255, 200, 0));
        rulesButton.setBounds(258, 270, 220, 60);
        rulesButton.setBorder(border);
        rulesButton.setFont(new Font("Arial", Font.BOLD, 14));
        layeredPane.add(rulesButton);

        JButton classificationButton = new JButton("CLASSIFICATION");
        classificationButton.setBackground(new Color(255, 170, 0));
        classificationButton.setBounds(258, 330, 220, 60);
        classificationButton.setBorder(border);
        classificationButton.setFont(new Font("Arial", Font.BOLD, 14));
        layeredPane.add(classificationButton);

        JButton exitButton = new JButton("EXIT");
        exitButton.setBackground(new Color(255, 132, 0));
        exitButton.setBounds(258, 390, 220, 60);
        exitButton.setBorder(border);
        exitButton.setFont(new Font("Arial", Font.BOLD, 14));
        layeredPane.add(exitButton);

        newGameButton.addActionListener(e -> {
            do {
                playerName = JOptionPane.showInputDialog(null, "Player name?");
            } while (playerName == null || playerName.isEmpty());

            layeredPane.remove(newGameButton);
            layeredPane.remove(rulesButton);
            layeredPane.remove(classificationButton);
            layeredPane.remove(exitButton);
            layeredPane.repaint();
            layeredPane.add(game.buttonsPanel());

            for (JButton button1 : game.buttons) {
                button1.setEnabled(false);
            }

            layeredPane.add(game.playerNameLabel());
            layeredPane.add(game.compLabel());
            layeredPane.add(game.playerScoreLabel());
            layeredPane.add(game.compScoreLabel());
            layeredPane.add(game.roundLabel());
            layeredPane.add(game.numberOfRoundLabel());

            JButton buttonNewGame = new JButton("New Game");
            buttonNewGame.setFont(new Font("Arial", Font.BOLD, 14));
            buttonNewGame.setBounds(420, 350, 119, 50);
            layeredPane.add(buttonNewGame);
            buttonNewGame.addActionListener(e1 -> {
                int result = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to play a new game?", "Message", JOptionPane.OK_CANCEL_OPTION);
                if (result == 0) {
                    game.clearBoard();
                }
            });

            JButton buttonSave = new JButton("Save");
            buttonSave.setFont(new Font("Arial", Font.BOLD, 14));
            buttonSave.setBounds(541, 350, 119, 50);
            layeredPane.add(buttonSave);
            buttonSave.addActionListener(e12 -> {
                player.addToList(playerName, game.getRoundCount(), game.getPlayerWinsCount(),
                        game.getDrawsCount(), game.getPcWinsCount());
                try {
                    player.saveClassification();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            });

            JButton buttonBack = new JButton("Back");
            buttonBack.setFont(new Font("Arial", Font.BOLD, 14));
            buttonBack.setBounds(478, 403, 120, 50);
            layeredPane.add(buttonBack);
            buttonBack.addActionListener(e13 -> {
                layeredPane.remove(game.panel);
                layeredPane.remove(game.playerNameLabel());
                layeredPane.remove(game.compLabel());
                layeredPane.remove(game.playerScoreLabel());
                layeredPane.remove(game.compScoreLabel());
                layeredPane.remove(game.roundLabel());
                layeredPane.remove(game.numberOfRoundLabel());
                layeredPane.remove(buttonBack);
                layeredPane.remove(buttonSave);
                layeredPane.remove(buttonNewGame);
                layeredPane.repaint();
                layeredPane.add(newGameButton);
                layeredPane.add(rulesButton);
                layeredPane.add(classificationButton);
                layeredPane.add(exitButton);
                game.setRoundCount(0);
                game.setPlayerWinsCount(0);
                game.setPcWinsCount(0);
                game.setDrawsCount(0);
            });
        });

        rulesButton.addActionListener(e -> {
            JFrame rulesFrame = new JFrame("RULES");
            rulesFrame.setResizable(false);
            JLabel introLabel = new JLabel(new ImageIcon("Files/intro.jpg"));
            rulesFrame.add(introLabel, BorderLayout.CENTER);
            rulesFrame.setSize(600, 372);
            rulesFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            rulesFrame.setLocationRelativeTo(null);
            rulesFrame.setVisible(true);
        });

        classificationButton.addActionListener(e -> {
            player.loadClassification();
            JFrame classificationFrame = new JFrame("CLASSIFICATION");
            classificationFrame.setResizable(false);
            String[] columns = {"Place", "Player name", "Rounds", "Wins", "Draws", "Fails"};
            String[][] data = new String[player.playersList.size()][columns.length];
            JTable jt = new JTable(data, columns);
            jt.setFont(new Font("Arial", Font.PLAIN, 14));
            jt.setEnabled(false);
            jt.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
            JScrollPane pane = new JScrollPane(jt);
            pane.setPreferredSize(new Dimension(483, 150));
            classificationFrame.add(pane, BorderLayout.CENTER);
            classificationFrame.pack();

            for (int i = 0; i < player.playersList.size(); i++) {
                jt.setValueAt(Integer.valueOf(i + 1).toString(), i, 0);
                jt.setValueAt(player.playersList.get(i).getName(), i, 1);
                jt.setValueAt(String.valueOf(player.playersList.get(i).getRounds()), i, 2);
                jt.setValueAt(String.valueOf(player.playersList.get(i).getWins()), i, 3);
                jt.setValueAt(String.valueOf(player.playersList.get(i).getDraws()), i, 4);
                jt.setValueAt(String.valueOf(player.playersList.get(i).getFails()), i, 5);
            }
            classificationFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            classificationFrame.setLocationRelativeTo(null);
            classificationFrame.setVisible(true);
        });

        exitButton.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to quit?", "Message", JOptionPane.OK_CANCEL_OPTION);
            if (result == 0) {
                position.closeProgram();
            }
        });

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int result = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to quit?", "Message", JOptionPane.OK_CANCEL_OPTION);
                if (result == 0) {
                    frame.dispose();
                    position.closeProgram();
                }
            }
        });
        frame.setSize(800, 520);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new MainFrame();
    }
}


