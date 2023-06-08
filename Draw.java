package monopoly;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashSet;

public class Draw {
    public static int[][] propertyCoordinates = {
            {95, 575}, {160, 545}, {245, 545}, {330, 545}, {415, 545},
            {575, 480}, {545, 420}, {545, 340}, {545, 250}, {545, 160},
            {480, 5}, {415, 35}, {330, 35}, {245, 35}, {160, 35},
            {5, 95}, {30, 160}, {30, 245}, {30, 330}, {30, 415}
        };
       public static int[][][] playerPositionsCoordinates = {
            {
                {10, 575}, {130, 515}, {220, 515}, {310, 515}, {400, 515},
                {575, 575}, {515, 460}, {515, 370}, {515, 280}, {515, 190},
                {575, 5}, {400, 75}, {310, 75}, {220, 75}, {130, 75},
                {5, 5}, {75, 190}, {75, 280}, {75, 370}, {75, 460}
            },
            {
                {35, 575}, {150, 515}, {240, 515}, {330, 515}, {420, 515},
                {575, 545}, {515, 440}, {515, 350}, {515, 260}, {515, 170},
                {545, 5}, {420, 75}, {330, 75}, {240, 75}, {150, 75},
                {5, 25}, {75, 170}, {75, 260}, {75, 350}, {75, 440}
            },
            {
                {65, 575}, {170, 515}, {260, 515}, {350, 515}, {440, 515},
                {575, 515}, {515, 420}, {515, 330}, {515, 240}, {515, 150},
                {515, 5}, {440, 75}, {350, 75}, {260, 75}, {170, 75},
                {5, 55}, {75, 150}, {75, 240}, {75, 330}, {75, 420}
            },
            {
                {95, 575}, {190, 515}, {280, 515}, {370, 515}, {460, 515},
                {575, 480}, {515, 400}, {515, 310}, {515, 220}, {515, 130},
                {480, 5}, {460, 75}, {370, 75}, {280, 75}, {190, 75},
                {5, 95}, {75, 130}, {75, 220}, {75, 310}, {75, 400}
            }
        };

    public static void drawPlayers(Graphics g, int[] playerPositions, ArrayList<Player> players) {
        

       for (int i = 0; i < players.size(); i++) {
        int positionIndex = playerPositions[i];
        int startX = playerPositionsCoordinates[i][positionIndex][0];
        int startY = playerPositionsCoordinates[i][positionIndex][1];

        g.setColor(players.get(i).getColor());
        g.fillOval(startX, startY, 10, 10);

        g.drawOval(startX, startY, 10, 10);
            }

        }
    public static void drawProperties(Graphics g, HashSet<Integer> ownedProperties) {
    for (Integer propertyIndex : ownedProperties) {
        int x = propertyCoordinates[propertyIndex][0];
        int y = propertyCoordinates[propertyIndex][1];

        g.fillRect(x, y, 10, 10);
    }
}
    

}
