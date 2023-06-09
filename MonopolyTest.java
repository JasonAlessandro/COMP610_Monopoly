/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package monopoly;

import javax.swing.JFrame;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jason
 */
public class MonopolyTest {

    public MonopolyTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testMovePlayer() {
        System.out.println("testMovePlayer");

        int[] playerPositions = {0, 0, 0};
        int currentPlayer = 0;

        int steps = 1;
        Monopoly.movePlayer(steps, playerPositions, currentPlayer);

        assertEquals(1, playerPositions[currentPlayer]);
    }

    @Test
    public void testInitGame_ValidNumPlayers() {
        System.out.println("testInitGame_ValidNumPlayers");
        JFrame frame = new JFrame();

        int expectedNumPlayers = 4;
        Monopoly.initGame(frame, expectedNumPlayers);

        assertEquals(expectedNumPlayers, Monopoly.players.size());
    }

}
