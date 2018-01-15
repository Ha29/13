/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thirteen;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
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
public class BoardTest {
    
    public BoardTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        Board board = new Board();
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
     * Test of isLegalCombo method, of class Board.
     */
    @Test
    public void testIsLegalCombo() {
//        System.out.println("isLegalCombo");
//        GroupOfCards cards = null;
//        Board instance = new Board();
//        boolean expResult = false;
//        boolean result = instance.isLegalCombo(cards);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }
    
    @Test
    public void testDistinctHands() {
        Board board = new Board();
        GroupOfCards allCards = new GroupOfCards();
        int index = 0;
        for (int ID = 0; ID < 4; ID += 1) {
            GroupOfCards hand = board.getPlayerHand(ID);
            assertEquals(13, hand.size());
            allCards.addAll(hand);
        }
        assertEquals(GroupOfCards.fullDeck().size(), allCards.size());
    }

    /**
     * Test of makeNewBoard method, of class Board.
     */
    @Test
    public void testMakeNewBoard() {
        Board board = new Board();
        GroupOfCards cards = board.getPlayerHand(0);
        Iterator<Card> iter = cards.iterator();
        Card next;
        if (!iter.hasNext()) {
            assertEquals(0, 1);
        }
        next = iter.next();
        
        Combo single = new Single(new GroupOfCards(next));
        assertEquals(single.isLegal(), true);
        Board newBoard = board.makeNewBoard(single, 0);
        GroupOfCards newHand = board.getPlayerHand(0);
        assert(newHand.contains(next) == false);
        GroupOfCards newCombo = new GroupOfCards();
        cards = newBoard.getPlayerHand(0);
        iter = cards.iterator();
        newCombo.add(iter.next());
        assert(newBoard.isLegalCombo(newCombo) == true);
        
    }
    
    public void testDouble() {
        Board newBoard = new Board();
        GroupOfCards cards;
        Iterator<Card> iter;
        
        int i;
        Card prev = null;
        Card n = null;
        List<Card> listOfCards;
        for (i = 0; i < 4; i++) {
            cards = newBoard.getPlayerHand(i);
            listOfCards = new ArrayList<>();
            listOfCards.addAll(cards);
            Collections.sort(listOfCards);
            iter = listOfCards.iterator();
            while (iter.hasNext()) {
                if (n == null) {
                    prev = iter.next();
                } else {
                    prev = n;
                    n = iter.next();
                    if (prev.rank() == n.rank()) {
                        break;
                    }
                }
            }
            GroupOfCards d = new GroupOfCards();
            if (prev == null || n == null) {
                System.err.println("run test again! no doubles coincidence");
                System.exit(0);
            }
            d.add(prev);
            d.add(n);
            Combo doub = new Double(d);
            assert(doub.isLegal() == true);
            newBoard.makeNewBoard(doub, i);
            GroupOfCards fullDeck = GroupOfCards.fullDeck();
            List<Card> deck = new ArrayList<>();
            deck.addAll(fullDeck);
            Collections.sort(deck);
            iter = deck.iterator();
            Card first = null;
            Card second = null;
            while (iter.hasNext()) {
                if (second == null) {
                    second = iter.next();
                } else {
                    first = second;
                    second = iter.next();
                    if (first.rank() == second.rank()) {
                        break;
                    }
                }
            }
            GroupOfCards newD = new GroupOfCards();
            newD.add(first);
            newD.add(second);
            assert(newBoard.isLegalCombo(newD));
        }
        
    }

    /**
     * Test of resetBoard method, of class Board.
     */
    @Test
    public void testResetBoard() {
        System.out.println("resetBoard");
        Board instance = new Board();
        instance.resetBoard();
        GroupOfCards cards;
        for (int i = 0; i < 4; i++) {
            cards = instance.getPlayerHand(i);
            assertEquals(13, cards.size());
        }

    }

    /**
     * Test of getPlayerHand method, of class Board.
     */
    @Test
    public void testGetPlayerHand() {
        Board instance = new Board();
        GroupOfCards cards;
        for (int i = 0; i < 4; i++) {
            cards = instance.getPlayerHand(i);
            assertEquals(13, cards.size());
        }
    }
    
}
