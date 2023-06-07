package com.mycompany.monopoly;

import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Game {

    public static String CheckMostMoneytoString(ArrayList<Player> players) {

        String winner = " ";

        int highestBalance = 0;
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getProperty().getMoney() > highestBalance) {
                highestBalance = players.get(i).getProperty().getMoney();
                winner = players.get(i).getName();
            }
        }

        return winner + " is the winner!";

    }

    public static void checkTurn(int turns, ArrayList<Player> players, JFrame gameWindow) {
        if (turns == 2) {

            JOptionPane.showMessageDialog(gameWindow, CheckMostMoneytoString(players), "Winner", JOptionPane.INFORMATION_MESSAGE);

            gameWindow.dispose();
            FileIO.incrementGamesPlayed();
        }
    }
}
/*private static int currentPlayer = 0;

    public static void removePlayer(ArrayList<Player> players, int playerNum, Property[] properties) {
        // property
        for (int i = 0; i < 20; i++) {
            if (properties[i].getOwner() == players.get(playerNum)) {
                properties[i].setOwner(null);
            }
        }

        players.remove(playerNum);

        if (!players.isEmpty()) {
            currentPlayer = currentPlayer % players.size();
            players.get(currentPlayer).setHasEndedTurn(false);
        }
    }

    public static void checkRemovePlayer(ArrayList<Player> players, Property[] properties) {
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getProperty().getMoney() < 0) {
                removePlayer(players, i, properties);
                break;
            }
        }
    }

    public static void checkWinner(ArrayList<Player> players) {
        if (players.size() == 1) {
            System.out.println("Congratulations, " + players.get(0).getName() + "! You are the winner!");
        }
    }*/
