/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thirteen;

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
public class RankTest {
    
    public RankTest() {
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
     * Test of values method, of class Rank.
     */
    @Test
    public void testValues() {
        System.out.println("values");
        Rank[] rankArray = new Rank[13];
        Rank[] expArray = Rank.values();
        int i = 0;
        for (Rank rank: Rank.values()){
            rankArray[i] = rank;
            i += 1;
        }
        assertArrayEquals(expArray, rankArray);
        
//        Rank[] expResult = null;
//        Rank[] result = Rank.values();
//        assertArrayEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }
    
}
