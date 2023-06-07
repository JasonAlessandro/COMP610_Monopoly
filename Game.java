package com.mycompany.monopoly;

import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Game {

    private static int currentPlayer = 0;

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
                JFrame lossWindow = new JFrame("Player Lost");
            lossWindow.setSize(300, 200);
            lossWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            // Create a JLabel with the loss message and add it to the JFrame
            JLabel lossMessage = new JLabel(players.get(i).getName() + " has lost!");
            lossMessage.setHorizontalAlignment(JLabel.CENTER);
            lossWindow.add(lossMessage);

            // Display the JFrame
            lossWindow.setVisible(true);
                removePlayer(players, i, properties);
                break;
            }
        }
    }

   
    public static void checkWinner(ArrayList<Player> players) {
    // If there's only one player left, they are the winner
    if (players.size() == 1) {
        // Create a new JFrame and set its properties
        JFrame winWindow = new JFrame("Winner");
        winWindow.setSize(300, 200);
        winWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create a JLabel with the win message and add it to the JFrame
        JLabel winMessage = new JLabel(players.get(0).getName() + " has won!");
        winMessage.setHorizontalAlignment(JLabel.CENTER);
        winWindow.add(winMessage);

        // Display the JFrame
        winWindow.setVisible(true);

        FileIO.incrementGamesPlayed();
    }
}


   public static void checkTurn(int turns, ArrayList<Player> players, Property[] properties) {
    // After each turn, check if any player should be removed, and if there's a winner
    checkRemovePlayer(players, properties);
    checkWinner(players);
}
}
