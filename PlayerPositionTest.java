/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package monopoly;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.imageio.ImageIO;

public class PlayerPositionTest {

    private static int currentPosition = 0;
    private static int[][] playerPositionsCoordinates = {
        {65, 575}, {170, 515}, {260, 515}, {350, 515}, {440, 515},
        {575, 515}, {515, 420}, {515, 330}, {515, 240}, {515, 150},
        {515, 5}, {440, 75}, {350, 75}, {260, 75}, {170, 75},
        {5, 55}, {75, 150}, {75, 240}, {75, 330}, {75, 420}
    };

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Player Position Test");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1000, 700);

            JPanel panel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    try {
                        Image board = ImageIO.read(new File("Monopoly_map.png"));
                        g.drawImage(board, 0, 0, this);

                        drawPlayer(g, currentPosition);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };

            JButton nextPositionButton = new JButton("Next Position");
            nextPositionButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    currentPosition = (currentPosition + 1) % playerPositionsCoordinates.length;
                    panel.repaint();
                }
            });

            frame.add(panel, BorderLayout.CENTER);
            frame.add(nextPositionButton, BorderLayout.SOUTH);
            frame.setVisible(true);
        });
    }

    private static void drawPlayer(Graphics g, int positionIndex) {
        int startX = playerPositionsCoordinates[positionIndex][0];
        int startY = playerPositionsCoordinates[positionIndex][1];

        g.setColor(Color.RED);
        g.fillOval(startX, startY, 10, 10);
        g.setColor(Color.BLACK);
        g.drawOval(startX, startY, 10, 10);
    }
}
