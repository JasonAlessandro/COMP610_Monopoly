package com.mycompany.monopoly;

import java.awt.*;
import javax.swing.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class Monopoly {

    //buttons
    private static JButton buyPropertyButton = new JButton("Buy Property");
    private static JButton endTurnButton = new JButton("End Turn");
    // For dice
    private static int currentDice = 1;
    private static Image[] diceImages = new Image[6];

    // Player
    private static ArrayList<Player> players;
    private static int currentPlayer = 0;
    private static JLabel currentPlayerLabel;
    private static JPanel panel;

    //For turn
    private static int turn = 0;

    private static Color[] playerColors = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW};

    //Positions
    private static int[] playerPositions = new int[]{0, 0, 0, 0};

    public void actionPerformed(ActionEvent e) {
        Timer timer = new Timer(20, null);
        Random random = new Random();
        int[] diceRolls = new int[10];

        for (int i = 0; i < diceRolls.length; i++) {
            diceRolls[i] = random.nextInt(6) + 1;
        }

        final int[] rollIndex = {0};

        ActionListener animationListener = new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                currentDice = diceRolls[rollIndex[0]];
                panel.repaint();

                if (rollIndex[0] >= diceRolls.length - 1) {
                    timer.stop();
                } else {
                    rollIndex[0]++;
                }
            }
        };

        timer.addActionListener(animationListener);
        timer.start();
    }

    // define properties
    private static Property[] properties = new Property[]{
        new Property("Start", 0, 0, null, 1),
        new Property("Jakarta", 500, 50, null, 2),
        new Property("Bandung", 400, 40, null, 3),
        new Property("Surabaya", 300, 30, null, 4),
        new Property("Medan", 200, 20, null, 5),
        new Property("Tp1", 0, 0, null, 6),
        new Property("Sydney", 500, 50, null, 7),
        new Property("Canberra", 400, 40, null, 8),
        new Property("Melbourne", 300, 30, null, 9),
        new Property("Perth", 200, 20, null, 10),
        new Property("Casino", 0, 0, null, 11),
        new Property("Auckland", 500, 50, null, 12),
        new Property("Wellington", 300, 30, null, 13),
        new Property("Christchurch", 400, 40, null, 14),
        new Property("Dunedin", 200, 20, null, 15),
        new Property("Tp2", 0, 0, null, 16),
        new Property("Beijing", 300, 30, null, 17),
        new Property("Shanghai", 200, 20, null, 18),
        new Property("Guangzhou", 400, 40, null, 19),
        new Property("Shenzhen", 500, 50, null, 20),};

    public static void main(String[] args) {
        loadDiceImages();

        String playerInput = JOptionPane.showInputDialog(null, "Please enter the number of players(2-4):", "Number of players", JOptionPane.QUESTION_MESSAGE);
        int numPlayers;
        try {
            numPlayers = Integer.parseInt(playerInput);
            if (numPlayers < 2 || numPlayers > 4) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "The number of players entered is invalid, please enter an integer between 2 and 4.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        players = new ArrayList<>();

        for (int i = 0; i < numPlayers; i++) {
            String name = "Player " + (i + 1);
            players.add(new Player(name, playerColors[i], 1500));
        }

        playerPositions = new int[numPlayers];
        for (int i = 0; i < numPlayers; i++) {
            playerPositions[i] = players.get(i).getPosition();
        }

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Monopoly");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1200, 700);

            panel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    try {
                        Image board = ImageIO.read(new File("Monopoly_map.png"));
                        g.drawImage(board, 0, 0, this);

                        // draw players
                        Draw.drawPlayersAndProperty(g, playerPositions, players);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };

            //End Turn
            endTurnButton.setBounds(1000, 450, 100, 50);
            endTurnButton.setEnabled(false);

            //buy button
            buyPropertyButton.setBounds(1000, 550, 100, 50);
            buyPropertyButton.setEnabled(false);

            buyPropertyButton.addActionListener(e -> {
                int currentPosition = playerPositions[currentPlayer];
                Property currentProperty = properties[currentPosition];

                if (currentProperty.getOwner() == null && currentPosition != 0) {
                    int price = currentProperty.getCost();
                    if (players.get(currentPlayer).getProperty().getMoney() >= price) {
                        int option = JOptionPane.showConfirmDialog(null, "Do you want to buy " + currentProperty.getName() + " for $" + price + "?", "Buy Property", JOptionPane.YES_NO_OPTION);
                        if (option == JOptionPane.YES_OPTION) {
                            players.get(currentPlayer).getProperty().subtractMoney(price);
                            currentProperty.setOwner(players.get(currentPlayer));
                            panel.repaint();
                            JOptionPane.showMessageDialog(null, "You have bought " + currentProperty.getName() + " for $" + price + ".", "Success", JOptionPane.INFORMATION_MESSAGE);
                            buyPropertyButton.setEnabled(false);
                            players.get(currentPlayer).addProperty(currentPosition);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "You don't have enough money to buy " + currentProperty.getName() + ".", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }

            });

            // dice part
            JButton rollDiceButton = new JButton("Roll Dice") {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(diceImages[currentDice - 1], 0, 0, getWidth(), getHeight(), this);
                }
            };
            rollDiceButton.setPreferredSize(new Dimension(50, 50));
            JLabel rollResultLabel = new JLabel("Roll Here");

            rollDiceButton.addActionListener(e -> {

                Timer timer = new Timer(10, null);
                Random random = new Random();
                int[] diceRolls = new int[10];

                for (int i = 0; i < diceRolls.length; i++) {
                    diceRolls[i] = random.nextInt(6) + 1;
                }

                final int[] rollIndex = {0};

                ActionListener animationListener = new ActionListener() {
                    public void actionPerformed(ActionEvent event) {
                        currentDice = diceRolls[rollIndex[0]];
                        rollDiceButton.repaint();

                        if (rollIndex[0] >= diceRolls.length - 1) {
                            timer.stop();
                            rollDiceButton.setEnabled(false);
                            int finalRoll = diceRolls[rollIndex[0]];
                            // Handle final roll result here
                            int steps = finalRoll;
                            movePlayer(steps, playerPositions, currentPlayer);
                            panel.repaint();

                        } else {
                            rollIndex[0]++;
                        }
                    }
                };

                timer.addActionListener(animationListener);
                timer.start();
                endTurnButton.setEnabled(true);

            });

            //define end turn
            endTurnButton.addActionListener(e -> {
                if (players.get(currentPlayer).getHasEndedTurn()) {
                    JOptionPane.showMessageDialog(null, "You have already ended your turn!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    players.get(currentPlayer).setHasEndedTurn(true);
                    currentPlayer = (currentPlayer + 1) % players.size();
                    rollDiceButton.setEnabled(true);
                    buyPropertyButton.setEnabled(false);
                    updateCurrentPlayerLabel();
                    turn++;
                    Game.checkTurn(turn, players, frame);

                }

            });

            currentPlayerLabel = new JLabel();
            currentPlayerLabel.setBounds(1000, 50, 250, 50);
            panel.add(currentPlayerLabel);
            updateCurrentPlayerLabel();

            panel.add(buyPropertyButton);

            panel.setLayout(null);
            panel.add(endTurnButton);

            JPanel bottomPanel = new JPanel();
            bottomPanel.add(rollDiceButton);
            bottomPanel.add(rollResultLabel);

            frame.add(panel, BorderLayout.CENTER);
            frame.add(bottomPanel, BorderLayout.SOUTH);

            frame.setVisible(true);

        });
    }

    private static void updateCurrentPlayerLabel() {

        currentPlayerLabel.setText("Current Round: " + players.get(currentPlayer).getName() + " - $" + players.get(currentPlayer).getProperty().getMoney());
        currentPlayerLabel.setForeground(players.get(currentPlayer).getColor());
        players.get(currentPlayer).setHasEndedTurn(false);
        endTurnButton.setEnabled(false);

    }

    public static void movePlayer(int steps, int[] playerPositions, int currentPlayer) {

        int newPosition = playerPositions[currentPlayer] + steps;
        if (newPosition >= 20) {
            newPosition -= 20;
            players.get(currentPlayer).getProperty().addMoney(400);
            System.out.println(players.get(currentPlayer).getName() + " passed Start. Player's money increased by 400. New balance: " + players.get(currentPlayer).getProperty().getMoney());
        }
        playerPositions[currentPlayer] = newPosition;
        handleCellAction(currentPlayer, newPosition + 1);

    }

    private static void loadDiceImages() {
        for (int i = 0; i < 6; i++) {
            try {
                diceImages[i] = ImageIO.read(new File("d" + (i + 1) + ".png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void handleCellAction(int playerNum, int cellIndex) {
        if (cellIndex == 1) {
            // Start
            buyPropertyButton.setEnabled(false);

        } else if (cellIndex == 11) {

            buyPropertyButton.setEnabled(false);
            Board.casino(players, currentPlayer);

        } else if (cellIndex == 6 || cellIndex == 16) {
            movePlayer(11, playerPositions, currentPlayer);
            buyPropertyButton.setEnabled(false);
            System.out.println(players.get(currentPlayer).getName() + " has been teleported");

        } else {
            Property currentProperty = properties[(cellIndex - 1) % 20];

            if (currentProperty.getOwner() == null) {
                buyPropertyButton.setEnabled(true);
            } else if (!currentProperty.getOwner().equals(players.get(playerNum))) {
                int rent = currentProperty.getRent();
                players.get(playerNum).getProperty().subtractMoney(rent);

                currentProperty.getOwner().getProperty().addMoney(rent);
            } else {
                System.out.println(players.get(playerNum).getName() + " landed on their own property: " + currentProperty.getName());

            }
        }

    }
}
