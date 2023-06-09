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
        createPlayerTable();
        createHorseTable();
        createLandsTable();
        createStateTable();
    }

    private static void createPlayerTable() {
        try (Connection connection = DriverManager.getConnection("jdbc:derby:app;create=true");
             Statement statement = connection.createStatement()) {

            String createTableSQL = "CREATE TABLE Player (" +
                    "playerId INT PRIMARY KEY, " +
                    "money INT, " +
                    "position INT, " +
                    "hasEndTurn BOOLEAN)";
            statement.executeUpdate(createTableSQL);

            System.out.println("Player table created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createHorseTable() {
    try (Connection connection = DriverManager.getConnection("jdbc:derby:app;create=true");
         Statement statement = connection.createStatement()) {

        String createTableSQL = "CREATE TABLE Horse (" +
                "horseId INT PRIMARY KEY, " +
                "gamesWon INT DEFAULT 0)";
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
        try (Connection connection = DriverManager.getConnection("jdbc:derby:app;create=true");
             Statement statement = connection.createStatement()) {

            String createTableSQL = "CREATE TABLE Lands (" +
                    "position INT PRIMARY KEY, " +
                    "rent INT, " +
                    "owner INT, " +
                    "cost INT, " +
                    "level INT)";
            statement.executeUpdate(createTableSQL);

            System.out.println("Lands table created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

   private static void createStateTable() {
    try (Connection connection = DriverManager.getConnection("jdbc:derby:app;create=true");
         Statement statement = connection.createStatement()) {

        String createTableSQL = "CREATE TABLE State (" +
                "currentPlayer INT)";
        statement.executeUpdate(createTableSQL);

        System.out.println("State table created successfully.");
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


}

