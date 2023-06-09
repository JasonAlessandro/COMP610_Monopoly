/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package monopoly;

/**
 *
 * @author taki
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    public static void main(String[] args) {
        // Calls the createPlayerTable, createHorseTable, createLandsTable, and createStateTable methods.

        createPlayerTable();
        createHorseTable();
        createLandsTable();
        createStateTable();
    }

    private static void createPlayerTable() {
        // Creates a table named "Player" in the database to store player information.
        // Includes columns for playerId, money, position, and hasEndTurn.
        try (Connection connection = DriverManager.getConnection("jdbc:derby:app;create=true"); Statement statement = connection.createStatement()) {

            String createTableSQL = "CREATE TABLE Player ("
                    + "playerId INT PRIMARY KEY, "
                    + "money INT, "
                    + "position INT, "
                    + "hasEndTurn BOOLEAN)";
            statement.executeUpdate(createTableSQL);

            System.out.println("Player table created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createHorseTable() {
        // Creates a table named "Horse" in the database to store horse information.
        // Includes columns for horseId and gamesWon.
        // Inserts six rows representing the initial state of the horses.
        try (Connection connection = DriverManager.getConnection("jdbc:derby:app;create=true"); Statement statement = connection.createStatement()) {

            String createTableSQL = "CREATE TABLE Horse ("
                    + "horseId INT PRIMARY KEY, "
                    + "gamesWon INT DEFAULT 0)";
            statement.executeUpdate(createTableSQL);

            for (int i = 1; i <= 6; i++) {
                String insertHorseSQL = "INSERT INTO Horse (horseId) VALUES (" + i + ")";
                statement.executeUpdate(insertHorseSQL);
            }

            System.out.println("Horse table created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createLandsTable() {
        // Creates a table named "Lands" in the database to store land information.
        // Includes columns for position, rent, owner, cost, and level.
        try (Connection connection = DriverManager.getConnection("jdbc:derby:app;create=true"); Statement statement = connection.createStatement()) {

            String createTableSQL = "CREATE TABLE Lands ("
                    + "position INT PRIMARY KEY, "
                    + "rent INT, "
                    + "owner INT, "
                    + "cost INT, "
                    + "level INT)";
            statement.executeUpdate(createTableSQL);

            System.out.println("Lands table created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createStateTable() {
        // Creates a table named "State" in the database to store game state information.
        // Includes a column for currentPlayer.
        try (Connection connection = DriverManager.getConnection("jdbc:derby:app;create=true"); Statement statement = connection.createStatement()) {

            String createTableSQL = "CREATE TABLE State ("
                    + "currentPlayer INT)";
            statement.executeUpdate(createTableSQL);

            System.out.println("State table created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
