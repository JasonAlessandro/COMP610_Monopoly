/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package monopoly;

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
public class PlayerPropertyTest {

    public PlayerPropertyTest() {
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
    public void testGetMoney() {
        PlayerProperty instance = new PlayerProperty(0);
        instance.setMoney(1000);
        int result = instance.getMoney();
        assertEquals(1000, result);
    }

    @Test
    public void testAddMoney() {
        PlayerProperty instance = new PlayerProperty(1500);
        instance.addMoney(500);
        int result = instance.getMoney();
        assertEquals(2000, result);
    }

    @Test
    public void testSubtractMoney() {
        PlayerProperty instance = new PlayerProperty(1500);
        instance.setMoney(1500);
        instance.subtractMoney(500);
        int result = instance.getMoney();
        assertEquals(1000, result);
    }

    @Test
    public void testSetMoney() {
        PlayerProperty instance = new PlayerProperty(1500);
        instance.setMoney(1000);
        int result = instance.getMoney();
        assertEquals(1000, result);
    }

    @Test
    public void testAddProperty() {
        PlayerProperty instance = new PlayerProperty(1500);
        instance.addProperty();
        instance.addProperty();
        int result = instance.getNumberOfProperties();
        assertEquals(2, result);
    }

}
