package monopoly;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Game {

    public static void removePlayer(ArrayList<Player> players, int playerNum, Property[] properties) {
        // Removes a player from the game by releasing their owned properties and setting their money to a specific value.
        // property
        for (int i = 0; i < 20; i++) {
            if (properties[i].getOwner() == players.get(playerNum)) {
                properties[i].setOwner(null);
            }
        }

        players.get(playerNum).getProperty().setMoney(-2);
    }

    public static void checkRemovePlayer(ArrayList<Player> players, Property[] properties) {
        // Checks if any player has run out of money and lost the game, displays a message if necessary, and removes the player from the game.
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getProperty().getMoney() <= 0 && players.get(i).getProperty().getMoney() != -2) {
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
        // If there's only one player whose money is more than 0, they are the winner
        int bankruptPlayers = 0;
        Player winner = null;

        for (Player player : players) {
            if (player.getProperty().getMoney() == -2) {
                bankruptPlayers++;
            } else {
                winner = player;
            }
        }

        if (bankruptPlayers == players.size() - 1) {
            // Create a new JFrame and set its properties
            JFrame winWindow = new JFrame("Winner");
            winWindow.setSize(300, 200);
            winWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            // Create a JLabel with the win message and add it to the JFrame
            JLabel winMessage = new JLabel(winner.getName() + " has won!");
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

    public static void initializeDatabase(int numPlayer) {
        // Initializes the database for storing player and game state information.
        try {
            String URL = "jdbc:derby:app;create=true";
            Connection connection = DriverManager.getConnection(URL);

            // clean the data in table
            String deleteSQL = "DELETE FROM PLAYER";
            Statement deleteStatement = connection.createStatement();
            deleteStatement.executeUpdate(deleteSQL);
            deleteStatement.close();

            String insertSQL = "INSERT INTO PLAYER (Playerid, hasendturn, position, money) VALUES (?, ?, ?, ?)";
            PreparedStatement insertStatement = connection.prepareStatement(insertSQL);

            for (int i = 1; i <= numPlayer; i++) {
                insertStatement.setInt(1, i);
                insertStatement.setBoolean(2, false);
                insertStatement.setInt(3, 0);
                insertStatement.setInt(4, 1500);
                insertStatement.executeUpdate();
            }

            for (int i = numPlayer + 1; i <= 4; i++) {
                insertStatement.setInt(1, i);
                insertStatement.setBoolean(2, false);
                insertStatement.setInt(3, 0);
                insertStatement.setInt(4, -2);
                insertStatement.executeUpdate();
            }

            String deleteGameStateSQL = "DELETE FROM STATE";
            Statement deleteGameStateStatement = connection.createStatement();
            deleteGameStateStatement.executeUpdate(deleteGameStateSQL);
            deleteGameStateStatement.close();

            String insertGameStateSQL = "INSERT INTO STATE (currentPlayer) VALUES (?)";
            PreparedStatement insertGameStateStatement = connection.prepareStatement(insertGameStateSQL);
            insertGameStateStatement.setInt(1, 1);
            insertGameStateStatement.executeUpdate();
            insertGameStateStatement.close();

            insertStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void initializeProperties() {
        // Initializes the properties in the database with their initial values.
        try {
            String URL = "jdbc:derby:app;create=true";
            Connection connection = DriverManager.getConnection(URL);

            String deleteSQL = "DELETE FROM Lands";
            Statement deleteStatement = connection.createStatement();
            deleteStatement.executeUpdate(deleteSQL);
            deleteStatement.close();

            String insertSQL = "INSERT INTO Lands (position, rent, owner, cost,level) VALUES (?, ?, ?, ?,?)";
            PreparedStatement insertStatement = connection.prepareStatement(insertSQL);

            Property[] properties = new Property[]{
                new Property("Start", 0, 0, 0, 0, null, 1),
                new Property("Jakarta", 500, 50, 500, 0, null, 2),
                new Property("Bandung", 400, 40, 400, 0, null, 3),
                new Property("Surabaya", 300, 30, 300, 0, null, 4),
                new Property("Medan", 200, 20, 200, 0, null, 5),
                new Property("Tp1", 0, 0, 0, 0, null, 6),
                new Property("Sydney", 500, 50, 500, 0, null, 7),
                new Property("Canberra", 400, 40, 400, 0, null, 8),
                new Property("Melbourne", 300, 30, 300, 0, null, 9),
                new Property("Perth", 200, 20, 200, 0, null, 10),
                new Property("Casino", 0, 0, 0, 0, null, 11),
                new Property("Auckland", 500, 50, 500, 0, null, 12),
                new Property("Wellington", 300, 30, 300, 0, null, 13),
                new Property("Christchurch", 400, 40, 400, 0, null, 14),
                new Property("Dunedin", 200, 20, 200, 0, null, 15),
                new Property("Tp2", 0, 0, 0, 0, null, 16),
                new Property("Beijing", 300, 30, 300, 0, null, 17),
                new Property("Shanghai", 200, 20, 200, 0, null, 18),
                new Property("Guangzhou", 400, 40, 400, 0, null, 19),
                new Property("Shenzhen", 500, 50, 500, 0, null, 20),};

            for (int i = 0; i < properties.length; i++) {
                Property property = properties[i];
                insertStatement.setInt(1, i + 1);
                insertStatement.setInt(2, property.getRent());
                insertStatement.setInt(3, 0);
                insertStatement.setInt(4, property.getCost());
                insertStatement.setInt(5, property.getPropertyLevel());
                insertStatement.executeUpdate();
            }

            insertStatement.close();

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void saveProperties(int position, int owner, int level) {
        // Saves the ownership and level of a property in the database.
        try {
            String URL = "jdbc:derby:app;create=true";
            Connection connection = DriverManager.getConnection(URL);
            String updateSQL = "UPDATE Lands SET owner = ?, level = ? WHERE position = ?";
            PreparedStatement updateStatement = connection.prepareStatement(updateSQL);

            updateStatement.setInt(1, owner);
            updateStatement.setInt(2, level);
            updateStatement.setInt(3, position);
            updateStatement.executeUpdate();

            updateStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Map<Integer, Integer> checkHorseWinners() {
        // Retrieves the horse winners and their corresponding number of wins from the database.
        Map<Integer, Integer> winners = new HashMap<>();
        String URL = "jdbc:derby:app;create=true";

        try (Connection connection = DriverManager.getConnection(URL); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery("SELECT horseId, gamesWon FROM Horse")) {

            while (resultSet.next()) {
                int horseNumber = resultSet.getInt("horseId");
                int gamesWon = resultSet.getInt("gamesWon");
                winners.put(horseNumber, gamesWon);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return winners;
    }

    public static void saveHorseWinners(int winNum) {
        // Saves the updated number of wins for a horse in the database.
        String URL = "jdbc:derby:app;create=true";
        try (Connection connection = DriverManager.getConnection(URL); PreparedStatement statement = connection.prepareStatement("UPDATE Horse SET gamesWon = gamesWon + 1 WHERE horseId = ?")) {

            statement.setInt(1, winNum);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void saveGame(ArrayList<Player> players, int id, int currentPlayer) {
        // Saves the current game state, including player positions, turn status, and money, in the database.
        try {

            System.out.println("Saving game:");
            System.out.println("id = " + id);
            System.out.println("Number of players: " + players.size());
            System.out.println("Number of player positions: " + Monopoly.playerPositions.length);

            String URL = "jdbc:derby:app;create=true";
            Connection connection = DriverManager.getConnection(URL);
            String updateSQL = "UPDATE PLAYER SET position = ?, hasendturn = ?, money = ? WHERE playerid = ?";
            PreparedStatement updateStatement = connection.prepareStatement(updateSQL);

            Player player = players.get(id - 1);
            updateStatement.setInt(1, Monopoly.playerPositions[id - 1]);
            updateStatement.setBoolean(2, player.getHasEndedTurn());
            updateStatement.setInt(3, player.getProperty().getMoney());
            updateStatement.setInt(4, id);
            updateStatement.executeUpdate();

            String updateCurrentPlayerSQL = "UPDATE STATE SET currentPlayer = ?";
            PreparedStatement updateCurrentPlayerStatement = connection.prepareStatement(updateCurrentPlayerSQL);
            updateCurrentPlayerStatement.setInt(1, currentPlayer + 1);

            updateCurrentPlayerStatement.executeUpdate();

            updateStatement.close();
            updateCurrentPlayerStatement.close();

            connection.close();
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void continueProperty(Property[] properties, ArrayList<Player> players) {
        // Retrieves and continues the property data from the database to maintain the game state.
        try {
            String URL = "jdbc:derby:app;create=true";
            Connection connection = DriverManager.getConnection(URL);

            String selectSQL = "SELECT * FROM LANDS";
            Statement selectStatement = connection.createStatement();
            ResultSet resultSet = selectStatement.executeQuery(selectSQL);

            while (resultSet.next()) {
                int position = resultSet.getInt("position");
                int level = resultSet.getInt("level");
                int cost = resultSet.getInt("cost");
                int owner = resultSet.getInt("owner");
                int rent = resultSet.getInt("rent");

                Property property = properties[position - 1];
                property.setPropertyLevel(level);
                property.setCost(cost);

                if (owner > 0 && owner <= players.size()) {
                    Player player = players.get(owner - 1);
                    property.setOwner(player);
                } else {
                    property.setOwner(null);
                }
                property.setRent(rent);
            }

            resultSet.close();
            selectStatement.close();
            connection.close();

            System.out.println("Property data has been successfully loaded from the database.");
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void continuePlaying() {
        // Continues the game by retrieving player and game state information from the database.
        try {

            String URL = "jdbc:derby:app;create=true";
            Connection connection = DriverManager.getConnection(URL);

            String query = "SELECT * FROM APP.PLAYER";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            for (int i = 0; i < 4; i++) {
                int playerNum = i + 1;

                String playerQuery = "SELECT * FROM PLAYER WHERE Playerid = " + playerNum;
                ResultSet playerResultSet = statement.executeQuery(playerQuery);

                if (playerResultSet.next()) {
                    int playerMoney = playerResultSet.getInt("money");
                    int playerPosition = playerResultSet.getInt("Position");
                    boolean hasEndTurn = playerResultSet.getBoolean("HasEndTurn");

                    Player player = new Player("Player " + playerNum, Monopoly.playerColors[i], playerMoney, hasEndTurn, playerNum);
                    Monopoly.players.add(player);

                    Monopoly.playerPositions[i] = playerPosition;
                } else {

                    Monopoly.players.remove(i);
                    Monopoly.playerPositions[i] = 0;
                }

                String currentPlayerQuery = "SELECT currentPlayer FROM STATE";
                ResultSet currentPlayerResultSet = statement.executeQuery(currentPlayerQuery);
                if (currentPlayerResultSet.next()) {
                    int currentRound;
                    currentRound = currentPlayerResultSet.getInt("currentPlayer");
                    if (currentRound > 3) {
                        Monopoly.currentPlayer = 0;
                    } else {
                        Monopoly.currentPlayer = currentRound;
                    }

                }

                playerResultSet.close();
            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
