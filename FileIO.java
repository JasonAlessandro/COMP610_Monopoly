package com.mycompany.monopoly;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileIO {

    public static void incrementGamesPlayed() {
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
