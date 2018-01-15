/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thirteen;

import java.util.LinkedList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author steven
 */
public class HumanPlayerTest {
    
    public HumanPlayerTest() {
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
     * Test of move method, of class HumanPlayer.
     */
    @Test
    public void testMove() throws Exception {
        Board board = new Board();
        Player newPlayer = new HumanPlayer(board, 1);
        Player newPlayer2 = new HumanPlayer(board, 1);
        LinkedList<Player> link = new LinkedList<Player>();
        link.addFirst(newPlayer);
        assert(newPlayer.equals(newPlayer2) == true);
        assertEquals(true, link.contains(newPlayer));
        assertEquals(true, link.contains(newPlayer2));
    }
    
}
