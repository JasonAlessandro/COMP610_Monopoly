package monopoly;

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
    public static ArrayList<Player> players;
    public static int currentPlayer = 0;
    private static JLabel currentPlayerLabel;
    private static JPanel panel;

    //For turn
    private static int turn = 0;

    public static Color[] playerColors = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW};

    //Positions
    public static int[] playerPositions = new int[]{0, 0, 0, 0};

    public static boolean ContinueGame = false;

    static void updateCurrentPlayerMoney(Player player, int i) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void actionPerformed(ActionEvent e) {
        Timer timer = new Timer(100, null);
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
        new Property("Start", 0, 0, 0, 0, null, 1),
        new Property("Jakarta", 500, 50, 500, 0, null, 2),
        new Property("Bandung", 400, 40, 400, 0, null, 3),
        new Property("Surabaya", 300, 30, 300, 0, null, 4),
        new Property("Medan", 200, 20, 200, 0, null, 5),
        new Property("Horse Race", 0, 0, 0, 0, null, 6),
        new Property("Sydney", 500, 50, 500, 0, null, 7),
        new Property("Canberra", 400, 40, 400, 0, null, 8),
        new Property("Melbourne", 300, 30, 300, 0, null, 9),
        new Property("Perth", 200, 20, 200, 0, null, 10),
        new Property("Casino", 0, 0, 0, 0, null, 11),
        new Property("Auckland", 500, 50, 500, 0, null, 12),
        new Property("Wellington", 300, 30, 300, 0, null, 13),
        new Property("Christchurch", 400, 40, 400, 0, null, 14),
        new Property("Dunedin", 200, 20, 200, 0, null, 15),
        new Property("Teleport", 0, 0, 0, 0, null, 16),
        new Property("Beijing", 300, 30, 300, 0, null, 17),
        new Property("Shanghai", 200, 20, 200, 0, null, 18),
        new Property("Guangzhou", 400, 40, 400, 0, null, 19),
        new Property("Shenzhen", 500, 50, 500, 0, null, 20),};

    public static void upgradeProperty(Player player, int position) {
        if (properties[position].getPropertyLevel() == 3) {
            JOptionPane.showMessageDialog(
                    null,
                    "The property " + properties[position].getName() + " is already at its maximum level.",
                    "Maximum Level Reached",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        if (player.getProperty().getMoney() >= properties[position].getUpgradeCost()) {
            player.getProperty().subtractMoney(properties[position].getUpgradeCost());

            properties[position].setPropertyLevel(properties[position].getPropertyLevel() + 1);
            properties[position].setRent(properties[position].getRent() * 2);

            JOptionPane.showMessageDialog(null, player.getName() + " has upgraded the property " + properties[position].getName() + ".");
            panel.repaint();
            updateCurrentPlayerMoney();
        } else {
            JOptionPane.showMessageDialog(null, player.getName() + " doesn't have enough money to upgrade the property " + properties[position].getName() + ".");

        }
    }

    public static void main(String[] args) {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        loadDiceImages();
        createAndShowGUI();
    }

    public static void createAndShowGUI() {
        // Create a window asking players to type the number of players
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Monopoly");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(620, 640);
            // Define initial panel
            final JPanel startPanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    try {
                        Image background = ImageIO.read(new File("pictures/monopoly_start.png"));
                        g.drawImage(background, 0, 0, this);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };

            startPanel.setLayout(null);
            // Define start and exit buttons
            JButton startButton = new JButton("Start Game");
            startButton.setBounds(200, 200, 160, 50);
            startPanel.add(startButton);
            startButton.addActionListener(e -> {
                startPanel.setVisible(false);
                selectPlayers(frame);
            });

            JButton exitButton = new JButton("Exit Game");
            exitButton.setBounds(200, 320, 160, 50);
            startPanel.add(exitButton);
            exitButton.addActionListener(e -> System.exit(0));

            JButton continueButton = new JButton("Continue Last Game");
            continueButton.setBounds(200, 260, 160, 50);
            continueButton.addActionListener(e -> {
                startPanel.setVisible(false);
                ContinueGame = true;
                initGame(frame, 4);
            });

            startPanel.add(continueButton);
            startPanel.add(startButton);
            startPanel.add(exitButton);

            frame.add(startPanel);
            frame.setVisible(true);
        });
    }

    public static void selectPlayers(JFrame frame) {

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(620, 640);

        // Define initial panel
        JPanel playerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    Image background = ImageIO.read(new File("pictures/Monopoly_start.png"));
                    g.drawImage(background, 0, 0, this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        playerPanel.setLayout(null);
        int buttonX = 180;
        int buttonY = 200;

        for (int i = 2; i <= 4; i++) {
            JButton playerButton = new JButton(i + " Players");
            int finalI = i;
            playerButton.addActionListener(e -> {
                playerPanel.setVisible(false);
                initGame(frame, finalI);
            });

            playerButton.setBounds(buttonX, buttonY, 180, 50);
            buttonY += 75;
            playerPanel.add(playerButton);
        }

        frame.add(playerPanel);
        playerPanel.setVisible(true);
    }

    public static void initGame(JFrame frame, int numPlayers) {
        players = new ArrayList<>();

        if (ContinueGame == true) {
            Game.continuePlaying();
            Game.continueProperty(properties, players);
            Game.checkRemovePlayer(players, properties);
        } else {

            for (int i = 0; i < numPlayers; i++) {
                String name = "Player " + (i + 1);
                players.add(new Player(name, playerColors[i], 1500, false, i + 1));
            }

            playerPositions = new int[numPlayers];
            for (int i = 0; i < numPlayers; i++) {
                playerPositions[i] = players.get(i).getPosition();
            }
            Game.initializeDatabase(numPlayers);
            Game.initializeProperties();
        }

        frame.getContentPane().removeAll();
        frame.repaint();

        SwingUtilities.invokeLater(() -> {

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1200, 700);

            panel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    try {
                        Image board = ImageIO.read(new File("pictures/Monopoly_map.png"));
                        g.drawImage(board, 0, 0, this);

                        // draw players
                        Draw.drawPlayers(g, playerPositions, players);
                        Draw.drawProperties(g, properties);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };

            //Go back
            /*JButton returnButton = new JButton("Back");
            returnButton.setBounds(1000, 100, 180, 50); 
            panel.add(returnButton);
            returnButton.addActionListener(e -> {
  
                         // Remove game panel
                    frame.getContentPane().removeAll();

                // Revalidate and repaint the frame
                frame.revalidate();
                    frame.repaint();

                    

                         createAndShowGUI();
                        });
             */
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
                            updateCurrentPlayerMoney();
                            players.get(currentPlayer).addProperty(currentPosition);
                            properties[currentPosition].setPropertyLevel(1);
                            Game.saveProperties(currentPosition + 1, players.get(currentPlayer).id, 1);
                            panel.repaint();

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

                    Game.saveGame(players, players.get(currentPlayer).id, currentPlayer);
                    Game.checkWinner(players);
                    do {
                        currentPlayer = (currentPlayer + 1) % players.size();
                    } while (players.get(currentPlayer).getProperty().getMoney() <= 0);

                    updateCurrentPlayerLabel();
                    rollDiceButton.setEnabled(true);
                    buyPropertyButton.setEnabled(false);

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

    public static void updateCurrentPlayerMoney() {

        currentPlayerLabel.setText("Current Round: " + players.get(currentPlayer).getName() + " - $" + players.get(currentPlayer).getProperty().getMoney());
        currentPlayerLabel.setForeground(players.get(currentPlayer).getColor());
    }

    public static void movePlayer(int steps, int[] playerPositions, int currentPlayer) {

        int newPosition = playerPositions[currentPlayer] + steps;
        if (newPosition >= 20) {
            newPosition -= 20;
            players.get(currentPlayer).getProperty().addMoney(400);
            updateCurrentPlayerMoney();
            JOptionPane.showMessageDialog(
                    null,
                    players.get(currentPlayer).getName() + " passed Start. Player's money increased by 400. New balance: " + players.get(currentPlayer).getProperty().getMoney(),
                    "Pass Start",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
        playerPositions[currentPlayer] = newPosition;
        handleCellAction(currentPlayer, newPosition + 1);

    }

    private static void loadDiceImages() {
        for (int i = 0; i < 6; i++) {
            try {
                diceImages[i] = ImageIO.read(new File("pictures/d" + (i + 1) + ".png"));
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
            Game.checkWinner(players);

        } else if (cellIndex == 6) {

            buyPropertyButton.setEnabled(false);
            Board.horseRace(players, currentPlayer);

        } else if (cellIndex == 16) {
            Random random = new Random();
            int newPosition = random.nextInt(20) + 1;

            // Move the player to the new position
            movePlayer(newPosition, playerPositions, currentPlayer);
            buyPropertyButton.setEnabled(false);

            // Display the message
            String message = players.get(currentPlayer).getName() + " has been teleported to " + properties[playerPositions[currentPlayer]].getName();
            JOptionPane.showMessageDialog(null, message);

        } else {
            Property currentProperty = properties[(cellIndex - 1) % 20];

            if (currentProperty.getOwner() == null) {
                buyPropertyButton.setEnabled(true);
            } else if (!currentProperty.getOwner().equals(players.get(playerNum))) {
                int rent = currentProperty.getRent();
                players.get(playerNum).getProperty().subtractMoney(rent);
                Game.checkWinner(players);
                currentProperty.getOwner().getProperty().addMoney(rent);
                JOptionPane.showMessageDialog(
                        null,
                        players.get(playerNum).getName() + " has landed on " + currentProperty.getOwner().getName() + "'s property and paid rent of " + rent,
                        "Pay Rent",
                        JOptionPane.INFORMATION_MESSAGE
                );
                updateCurrentPlayerMoney();

            } else {

                int choice = JOptionPane.showConfirmDialog(
                        null,
                        "You landed on your own property: " + currentProperty.getName() + ".\nDo you want to upgrade it?",
                        "Upgrade Property",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );

                if (choice == JOptionPane.YES_OPTION) {
                    upgradeProperty(players.get(currentPlayer), cellIndex - 1);
                    panel.repaint();
                    Game.saveProperties(players.get(currentPlayer).getPosition(), players.get(currentPlayer).id, properties[players.get(currentPlayer).getPosition()].getPropertyLevel());
                } else {
                    JOptionPane.getRootFrame().dispose();
                }

            }
        }

    }

}
