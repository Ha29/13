/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thirteen;

import java.util.ArrayList;
import java.util.Collections;
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
public class GroupOfCardsTest {
    
    public GroupOfCardsTest() {
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
     * Test of removeCard method, of class GroupOfCards.
     */
    @Test
    public void testCards() {
        System.out.println("Testing Cards: ");
        GroupOfCards instance = new GroupOfCards();
        for (Rank rank: Rank.values()) {
            for (Suit suit: Suit.values()){
                instance.add(new Card(suit, rank));
            }
            for (Suit suit: Suit.values()) {
                instance.add(new Card(suit, rank));
            }
        }
        assertEquals(52, instance.size());
//        System.out.println("removeCard");
//        Card card = null;
//        GroupOfCards instance = new GroupOfCards();
//        boolean expResult = false;
//        boolean result = instance.removeCard(card);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }
    
    @Test
    public void testTriple() {
        GroupOfCards fullDeck = GroupOfCards.fullDeck();
        List<Card> deck = new ArrayList<>();
        deck.addAll(fullDeck);
        Collections.sort(deck);
        GroupOfCards trip = new GroupOfCards();
        for (int i = 0; i < 3; i++) {
            Card card = deck.get(i);
            assert(card.rank() == Rank.Three);
            trip.add(card);
        }
        Combo triple = new Triple(trip);
        assert(triple.isLegal());
        GroupOfCards oTriple = new GroupOfCards();
        for (int i = 4; i < 7; i++) {
            Card card = deck.get(i);
            assert(card.rank() == Rank.Four);
            oTriple.add(card);
        }
        Combo newTriple = new Triple(oTriple);
        assertEquals(triple.isLegal(newTriple), true);
        
        
        
    }
    
    @Test
    public void testFlushBomb() {
        GroupOfCards fullDeck = GroupOfCards.fullDeck();
        GroupOfCards flushBomb = new GroupOfCards();
        for (Card c: fullDeck) {
            if (c.suit() == Suit.Clubs || c.suit() == Suit.Diamonds) {
                if ((c.rank() == Rank.Three) || (c.rank() == Rank.Four)
                                                || (c.rank() == Rank.Five)) {
                    flushBomb.add(c);
                }
            }
        }
        assertEquals(6, flushBomb.size());
        Combo bomb = new FlushBomb(flushBomb);
        assert(bomb.isLegal());
        GroupOfCards newFBomb = new GroupOfCards();
        for (Card c: fullDeck) {
            if (c.suit() == Suit.Spades || c.suit() == Suit.Hearts) {
                if ((c.rank() == Rank.Five) || (c.rank() == Rank.Six)
                                                || (c.rank() == Rank.Seven)) {
                    newFBomb.add(c);
                }
            }
        }
        Combo newBomb = new FlushBomb(newFBomb);
        assert(bomb.isLegal(newBomb));
        assert(newBomb.isLegal());
    }
    
    @Test
    public void testFlush() {
        GroupOfCards fullDeck = GroupOfCards.fullDeck();
        GroupOfCards flush = new GroupOfCards();
        for (Card c: fullDeck) {
            if (c.suit() == Suit.Clubs) {
                if ((c.rank() == Rank.Three) || (c.rank() == Rank.Four)
                                                || (c.rank() == Rank.Five)) {
                    flush.add(c);
                }
            }
        }
        assertEquals(3, flush.size());
        Combo flushCombo = new Flushes(flush);
        assert(flushCombo.isLegal());
        GroupOfCards newFlush = new GroupOfCards();
        for (Card c: fullDeck) {
            if (c.suit() == Suit.Hearts) {
                if ((c.rank() == Rank.Five) || (c.rank() == Rank.Six)
                                                || (c.rank() == Rank.Seven)) {
                    newFlush.add(c);
                }
            }
        }
        Combo newFlushCombo = new Flushes(newFlush);
        assert(flushCombo.isLegal(newFlushCombo));
        assert(newFlushCombo.isLegal());
        GroupOfCards diffSize = new GroupOfCards();
        for (Card c: fullDeck) {
            if (c.suit() == Suit.Hearts) {
                if ((c.rank() == Rank.Q) || (c.rank() == Rank.K) 
                        || (c.rank() == Rank.A) || (c.rank() == Rank.Two)) {
                    diffSize.add(c);
                } 
            }
        }
        Combo diffSizeCombo = new Flushes(diffSize);
        assert(diffSizeCombo.isLegal());
        assertEquals(false, newFlushCombo.isLegal(diffSizeCombo));
    }
   
    
    @Test
    public void testRankBomb() {
        GroupOfCards fullDeck = GroupOfCards.fullDeck();
        List<Card> deck = new ArrayList<>();
        deck.addAll(fullDeck);
        Collections.sort(deck);
        GroupOfCards bombCards = new GroupOfCards();
        for (int i = 0; i < 4; i++) {
            Card card = deck.get(i);
            assert(card.rank() == Rank.Three);
            bombCards.add(card);
        }
        Combo bomb = new RankBomb(bombCards);
        assert(bomb.isLegal());
        GroupOfCards oTriple = new GroupOfCards();
        for (int i = 4; i < 8; i++) {
            Card card = deck.get(i);
            assert(card.rank() == Rank.Four);
            oTriple.add(card);
        }
        Combo newBomb = new RankBomb(oTriple);
        assertEquals(bomb.isLegal(newBomb), true);
        
        
    }
}
