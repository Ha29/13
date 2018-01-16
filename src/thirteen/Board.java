/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thirteen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 *
 * @author steven
 */
public class Board {
    private GroupOfCards oldCards = new GroupOfCards();
    private Combo recentCombo = null;
    private List<GroupOfCards> playersHands = new ArrayList<>(4);
    private int cardsPerHand;
    private Random random;
    private List<Card> shuffledCards;
    
    public Board() {
        int i = 0;
        while (i < 4) {
            playersHands.add(new GroupOfCards());
            i += 1;
        }
        random = new Random();
        GroupOfCards fullDeck = GroupOfCards.fullDeck();
        shuffledCards = new ArrayList<>(fullDeck);
        shuffleAndDistribute();
    };
    
    private Board(GroupOfCards oldCards, Combo recentCombo, 
                                            List<GroupOfCards> playersHands) {
        this.oldCards = oldCards;
        this.recentCombo = recentCombo;
        this.playersHands = playersHands;
        this.random = new Random();
        GroupOfCards fullDeck = GroupOfCards.fullDeck();
        this.shuffledCards = new ArrayList<>(fullDeck);
    };
    
    boolean isLegalCombo(GroupOfCards cards) {
        Combo combo = cards.returnCombo();
        if (recentCombo == null) {
            return combo.isLegal();
        }
        return recentCombo.isLegal(combo);
    };
    
    Board makeNewBoard(Combo recentCombo, int ID) {
        List<GroupOfCards> newHands = new ArrayList<>(4);
        newHands.addAll(this.playersHands);
        if (recentCombo != null) {
            newHands.get(ID).removeAll(recentCombo.getCards());
        }
        GroupOfCards newOldCards = new GroupOfCards();
        newOldCards.addAll(oldCards);
        if (recentCombo != null) {
            newOldCards.addAll(recentCombo.getCards());
        }
        if (recentCombo == null || recentCombo.getCards().size() == 0) {
            System.out.println("board 68");
        }
        return new Board(newOldCards, recentCombo, newHands);
    };
    
    Board makeNewPassingBoard(Combo recentCombo) {
        return this;
    };
    
    Board makeNullBoard() {
        Combo newRecentCombo = null;
        return new Board(oldCards, newRecentCombo, playersHands);
    };
    
    void resetBoard() {
        oldCards = new GroupOfCards();
        recentCombo = null;
        playersHands.clear();
        int i = 0;
        while (i < 4) {
            playersHands.add(new GroupOfCards());
            i += 1;
        }
        random = new Random();
        GroupOfCards fullDeck = GroupOfCards.fullDeck();
        shuffledCards = new ArrayList<>(fullDeck);
        shuffleAndDistribute();
    }

    void shuffleAndDistribute() {
        Collections.shuffle(shuffledCards);
        Iterator<Card> iter = shuffledCards.iterator();
        for (int index = 0; iter.hasNext(); index = (index + 1)%4) {
            Card nextCard = iter.next();
            playersHands.get(index).add(nextCard);
        }
    }

    private Board copy() {
        List<GroupOfCards> playersHandsCopy = new ArrayList<>(4);
        playersHandsCopy.addAll(playersHands);
        Board copy = new Board(oldCards.copy(), recentCombo, 
                                                            playersHandsCopy);
        return copy;
    }
    
    public GroupOfCards getPlayerHand(int ID) {
        return playersHands.get(ID);
    }
    
    public GroupOfCards getOldCards() {
        return this.oldCards;
    }
    
    public Combo getRecentCombo() {
        return this.recentCombo;
    }
    
}
