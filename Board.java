package com.mycompany.monopoly;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Board {

    public static void casino(ArrayList<Player> players, int currentPlayer) {
        Random random = new Random();
        int mult = (random.nextInt(21) - 10) * 50;
        players.get(currentPlayer).getProperty().addMoney(mult);
        String message = players.get(currentPlayer).getName() + " landed on Casino, spins the wheel and gets $" + mult;
        JOptionPane.showMessageDialog(null, message);
        Monopoly.updateCurrentPlayerMoney();

    }
    public static void horseRace(ArrayList<Player> players, int currentPlayer) {
    
    Horse[] horses = new Horse[]{
            new Horse(0.1, 5.0),
            new Horse(0.12, 4.0),
            new Horse(0.14, 3.5),
            new Horse(0.16, 3.0),
            new Horse(0.23, 2.5),
            new Horse(0.25, 2.0),
    };

    int response = JOptionPane.showConfirmDialog(null, "Do you want to participate in horse racing?", "Horse Race", JOptionPane.YES_NO_OPTION);

    
    if (response == JOptionPane.YES_OPTION) {
        
        JFrame horseFrame = new JFrame("Horse Race");
        horseFrame.setSize(700, 1000);
        JPanel horsePanel = new JPanel();
        horsePanel.setLayout(null);  

        
       for (int i = 1; i <= 6; i++) {
    JButton horseButton = new JButton("Horse " + i);

   
    horseButton.setBounds(50, i * 100, 120, 30);
    horsePanel.add(horseButton);
    
    horseFrame.add(horsePanel);
    horseFrame.setVisible(true);

   try {
    
    Image img = ImageIO.read(new File("horses.png"));
    ImageIcon icon = new ImageIcon(img);
    JLabel horseLabel = new JLabel(icon);

    
    horseLabel.setBounds(180, 40, img.getWidth(null), img.getHeight(null));
    horsePanel.add(horseLabel);

} catch (IOException e) {
    e.printStackTrace();
}




    
    int finalI = i;
    horseButton.addActionListener(e -> {
        
        Object[] possibilities = {"100", "200", "500", "1000"};
        String s = (String)JOptionPane.showInputDialog(
            horseFrame,
            "You chose horse number " + finalI + ",\n"
            + "How much do you want to bet?",
            "Place your bet",
            JOptionPane.PLAIN_MESSAGE,
            null,
            possibilities,
            "100");

        
        if ((s != null) && (s.length() > 0)) {
            int bet = Integer.parseInt(s);
            if (players.get(currentPlayer).getProperty().getMoney() >= bet) {
                
                Random rand = new Random();
    int randomNumber = rand.nextInt(100) + 1;
    
    int winningHorseIndex = 0;
    double cumulativeProbability = 0.0;
    for (int horseIndex = 0; horseIndex < horses.length; horseIndex++) {
        cumulativeProbability += horses[horseIndex].getWinProbability() * 100;
        if (randomNumber <= cumulativeProbability) {
            winningHorseIndex = horseIndex;
            break;
        }
    }

    
    if (winningHorseIndex == finalI - 1) {
        int winnings = (int) (bet * horses[finalI - 1].getPayoutRatio());
        players.get(currentPlayer).getProperty().addMoney(winnings);
        JOptionPane.showMessageDialog(horseFrame, "You won! You gained $" + winnings);
        Monopoly.updateCurrentPlayerMoney();
    } else {
        players.get(currentPlayer).getProperty().subtractMoney(bet);
        JOptionPane.showMessageDialog(horseFrame, "You lost. You lost $" + bet);
        Monopoly.updateCurrentPlayerMoney();
    }


              
                horseFrame.dispose();
                
            } else {
                
                JOptionPane.showMessageDialog(horseFrame, "You don't have enough money for this bet.");
            }
        }

        return;
    });
       }
}
    }
}

