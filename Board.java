package com.mycompany.monopoly;

import java.util.ArrayList;
import java.util.Random;

public class Board {

    public static void casino(ArrayList<Player> players, int currentPlayer) {
        Random random = new Random();
        int mult = (random.nextInt(21) - 10) * 50;
        players.get(currentPlayer).getProperty().addMoney(mult);
        System.out.println(players.get(currentPlayer).getName() + " landed on Casino, spins the wheel and gets $" + mult);

    }

}
