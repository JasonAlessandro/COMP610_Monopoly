/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package monopoly;

/**
 *
 * @author Jason
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileIO {

    public static void incrementGamesPlayed() {
        // Increments the number of games played by reading the current count from a file, 
        // incrementing it by 1, and then writing the updated count back to the file.
        String fileName = "GamesPlayed.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line = reader.readLine();

            if (line != null) {
                String[] tokens = line.split(" ");
                int gamesPlayed = Integer.parseInt(tokens[0]);
                gamesPlayed++;

                try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                    writer.write(gamesPlayed + " games has been played");
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading or writing to file: " + e.getMessage());
        }
    }
}
