/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package monopoly;

import java.awt.event.ActionEvent;
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

    /**
     * Test of actionPerformed method, of class Monopoly.
     */
    @Test
    public void testActionPerformed() {
        System.out.println("actionPerformed");
        ActionEvent e = null;
        Monopoly instance = new Monopoly();
        instance.actionPerformed(e);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of upgradeProperty method, of class Monopoly.
     */
    @Test
    public void testUpgradeProperty() {
        System.out.println("upgradeProperty");
        Player player = null;
        int position = 0;
        Monopoly.upgradeProperty(player, position);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of main method, of class Monopoly.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        Monopoly.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of selectPlayers method, of class Monopoly.
     */
    @Test
    public void testSelectPlayers() {
        System.out.println("selectPlayers");
        JFrame frame = null;
        Monopoly.selectPlayers(frame);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of initGame method, of class Monopoly.
     */
    @Test
    public void testInitGame() {
        System.out.println("initGame");
        JFrame frame = null;
        int numPlayers = 0;
        Monopoly.initGame(frame, numPlayers);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateCurrentPlayerMoney method, of class Monopoly.
     */
    @Test
    public void testUpdateCurrentPlayerMoney() {
        System.out.println("updateCurrentPlayerMoney");
        Monopoly.updateCurrentPlayerMoney();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of movePlayer method, of class Monopoly.
     */
    @Test
    public void testMovePlayer() {
        System.out.println("movePlayer");
        int steps = 0;
        int[] playerPositions = null;
        int currentPlayer = 0;
        Monopoly.movePlayer(steps, playerPositions, currentPlayer);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
