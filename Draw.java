package monopoly;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Draw {

    public static Color[] playerColors = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW};

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
            g.fillOval(startX, startY - 2, 7, 7);
            g.fillRect(startX + 1, startY + 5, 5, 7);

            //Outline
            g.setColor(Color.BLACK);
            g.drawOval(startX, startY - 2, 7, 7);
            g.drawRect(startX + 1, startY + 5, 5, 7);
        }

    }

    public static void drawProperties(Graphics g, Property[] properties) {
        for (int propertyIndex = 0; propertyIndex < properties.length; propertyIndex++) {
            int x = propertyCoordinates[propertyIndex][0];
            int y = propertyCoordinates[propertyIndex][1];
            Property property = properties[propertyIndex];

            if (property.getOwner() != null) {

                int propertyLevel = property.getPropertyLevel();
                // Define the x-coordinates and y-coordinates for the triangle's vertices
                int[] xPoints = {x + 3, x, x + 6};
                int[] yPoints = {y, y + 10, y + 10};

                switch (propertyLevel) {
                    case 1:
                        // Draw the outline of the triangle
                        g.setColor(Color.BLACK);
                        g.drawPolygon(xPoints, yPoints, 3);

                        // Fill the triangle with color
                        g.setColor(playerColors[property.getOwner().id - 1]);
                        g.fillPolygon(xPoints, yPoints, 3);
                        break;
                    case 2:
                        // Draw the outline of the triangle
                        g.setColor(Color.BLACK);
                        g.drawPolygon(xPoints, yPoints, 3);

                        // Fill the triangle with color
                        g.setColor(playerColors[property.getOwner().id - 1]);
                        g.fillPolygon(xPoints, yPoints, 3);
                        g.fillRect(x, y + 11, 7, 10);
                        break;
                    case 3:
                        int size = 10; // Size of the square
                        int rectWidth = 10; // Width of the rectangles
                        int rectHeight = size * 2; // Height of the rectangles

                        // Draw the square
                        g.setColor(playerColors[property.getOwner().id - 1]);
                        g.fillRect(x + 5, y + 10, size, size);

                        // Draw the left rectangle
                        g.fillRect(x + 5 - rectWidth, y, rectWidth, rectHeight);

                        // Draw the right rectangle
                        g.fillRect(x + 5 + size, y, rectWidth, rectHeight);
                        break;
                    default:
                        break;
                }
            }
        }
    }

}
