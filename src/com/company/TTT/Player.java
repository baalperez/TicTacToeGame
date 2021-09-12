package com.company.TTT;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Player implements Serializable {

    @Serial
    private static final long serialVersionUID = -8095134400310650651L;

    private String name;
    private int rounds;
    private int wins;
    private int draws;
    private int fails;
    List<Player> playersList = new ArrayList<>();

    public Player() {
    }

    public Player(String name, int rounds, int wins, int draws, int fails) {
        this.name = name;
        this.rounds = rounds;
        this.wins = wins;
        this.draws = draws;
        this.fails = fails;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", rounds=" + rounds +
                ", wins=" + wins +
                ", draws=" + draws +
                ", fails=" + fails +
                '}';
    }

    public String getName() {
        return name;
    }

    public int getRounds() {
        return rounds;
    }

    public int getWins() {
        return wins;
    }

    public int getDraws() {
        return draws;
    }

    public int getFails() {
        return fails;
    }

    List<Player> addToList(String name, int rounds, int wins, int draws, int fails) {
        playersList.add(new Player(name, rounds, wins, draws, fails));
        return new ArrayList<>(playersList);
    }

    public void saveClassification() throws IOException {
        try {
            FileOutputStream writeData = new FileOutputStream("classification.txt");
            ObjectOutputStream writeStream = new ObjectOutputStream(writeData);
            writeStream.writeObject(playersList);
            writeStream.flush();
            writeStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadClassification() {
        try {
            FileInputStream readData = new FileInputStream("classification.txt");
            ObjectInputStream readStream = new ObjectInputStream(readData);
            playersList = (ArrayList<Player>) readStream.readObject();
            readStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
