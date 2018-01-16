/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thirteen;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


/**
 *
 * @author steven
 */
public class GroupOfCards extends HashSet<Card> implements Comparator<Card>{
    private Iterator<Card> cardIterator = null;
    GroupOfCards(Card card) {
        this.add(card);
    };
    
    GroupOfCards() {};
    
    GroupOfCards copy() {
        GroupOfCards copy = new GroupOfCards();
        Iterator<Card> iter = this.iterator();
        while (iter.hasNext()) {
            copy.add(iter.next());
        }
        return copy;
    }

    @Override
    public int compare(Card o1, Card o2) {
        return o1.compareTo(o2);
    }
    
    public static GroupOfCards fullDeck() {
        GroupOfCards deck = new GroupOfCards();
        for (Suit suit: Suit.values()) {
            for (Rank rank: Rank.values()) {
                Card newCard = new Card(suit, rank);
                deck.add(newCard);
            }
        }
        return deck;
    }
    
    public Combo returnCombo() {
        System.out.println("Cards in here:");
        for (Card c: this) {
            System.out.print(c);
        }
        Flushes flush;
        switch(size()) {
            case 0:
                System.out.println("Zero Case");
                return new IllegalCombo();
            case 1:
                Deuce d = new Deuce(this);
                if (d.isLegal()) {
                    System.out.println("deuces");
                    return d;
                }
                return new Single(this);
            case 2:
                System.out.println("Double case!");
                Double doub = new Double(this);
                if (doub.isLegal()) {
                    return doub;
                }
                break;
            case 3:
                flush = new Flushes(this);
                if (flush.isLegal()) {
                    return flush;
                }
                Triple trip = new Triple(this);
                if (trip.isLegal()) {
                    return trip;
                }
                break;
            case 4:
                flush = new Flushes(this);
                if (flush.isLegal()) {
                    return flush;
                }
                RankBomb bomb = new RankBomb(this);
                if (bomb.isLegal()) {
                    return bomb;
                }
                break;
            case 6:
                flush = new Flushes(this);
                if (flush.isLegal()) {
                    return flush;
                }
                FlushBomb fBomb = new FlushBomb(this);
                if (fBomb.isLegal()) {
                    return fBomb;
                }
                break;
            default:
                Flushes otherFlush = new Flushes(this);
                if (otherFlush.isLegal()) {
                    return otherFlush;
                }
                break;
        }
        return new IllegalCombo();
    };
    

};

interface Combo {
    /*
     * Interface implemented by all combos possible in the game. 
     */
    public abstract boolean greaterThan(Combo otherCards) 
                                                    throws IllegalComboException;
    /*
     * Method that returns whether if otherCards is greater in value than 
     * this.value(). 
     * Checks if otherCards is legal as its subclass first as a prerequisite. 
     */
    public abstract boolean isLegal();
    /*
     * Method that returns whether the GroupOfCards is legal for its 
     * specified subclass. 
     */
    public abstract boolean isLegal(Combo otherCards);
    /*
     * Method that returns whether otherCards is legal for its subclass given
     * the specific instance since flushes can have varying sizes
     */
    public abstract int value();
    /*
     * Method that returns the value of the combination of cards. Dependent on
     * the highest card in the combination regardless of the subclass. 
     */
    public abstract int size();
    /*
     * Method that returns the number of cards in GroupOfCards when
     * instantiated. 
     */
    public abstract GroupOfCards getCards();
    /* 
     * Method that returns the cards passed in as an argument when this instance
     * of the subclass was instantiated.
     */
    public abstract String name();
};

class IllegalCombo implements Combo {

    @Override
    public boolean greaterThan(Combo otherCards) throws IllegalComboException {
        throw new IllegalComboException("Illegal Combo! Forgot to check isLegal()"); 
    }

    @Override
    public boolean isLegal() {
        return false;
    }

    @Override
    public boolean isLegal(Combo otherCards){
        return false;
    }

    @Override
    public int value() {
        return 0;
    }

    @Override
    public int size() {
        return 0; 
    }

    @Override
    public GroupOfCards getCards() {
        return null;
    }

    @Override
    public String name() {
        return "Illegal Combo";
    }
    
};
class Double implements Combo {
    private final GroupOfCards cards;
    private int value;
    private boolean legal;
    Double(GroupOfCards cards) {
        this.legal = true;
        this.cards = cards;
        this.value = Integer.MIN_VALUE;
        if (cards.size() != 2) {
            this.legal = false;
        }
        Rank repeatingRank = null;
        for (Card c: cards) {
            if (repeatingRank == null) {
                repeatingRank = c.rank();
            } else if (repeatingRank != c.rank()) {
                this.legal = false;
            }
            if (c.value() > value) {
                value = c.value();
            }
        }
    }

    @Override
    public boolean greaterThan(Combo otherCards) throws IllegalComboException {
        if ((!this.legal) || (!this.isLegal(otherCards))) {
            throw new IllegalComboException("not a legal combo!");
        }
        return this.value() > otherCards.value();
    }

    @Override
    public boolean isLegal() {
        return this.legal;
    }

    @Override
    public boolean isLegal(Combo otherCards) {
        Combo newDoub = new Double(otherCards.getCards());
        return newDoub.isLegal();
    }

    @Override
    public int value() {
        return this.value;
    }

    @Override
    public int size(){
        return this.cards.size();
    }

    @Override
    public GroupOfCards getCards() {
        return this.cards;
    }

    @Override
    public String name() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return "Double";
    }
}

class Triple implements Combo {
    private final GroupOfCards cards;
    private int value;
    private boolean legal;
    Triple(GroupOfCards cards) {
        this.legal = true;
        this.cards = cards;
        this.value = Integer.MIN_VALUE;
        if (cards.size() != 3) {
            this.legal = false;
        }
        Rank repeatingRank = null;
        for (Card c: cards) {
            if (repeatingRank == null) {
                repeatingRank = c.rank();
            } else if (repeatingRank != c.rank()) {
                this.legal = false;
            }
            if (c.value() > value) {
                value = c.value();
            }
        }
    }

    @Override
    public boolean greaterThan(Combo otherCards) throws IllegalComboException {
        if ((!this.legal) || (!this.isLegal(otherCards))) {
            throw new IllegalComboException("not a legal combo!");
        }
        return this.value() > otherCards.value();
    }

    @Override
    public boolean isLegal() {
        return this.legal;
    }

    @Override
    public boolean isLegal(Combo otherCards) {
        Combo newTriple = new Triple(otherCards.getCards());
        return newTriple.isLegal();
    }

    @Override
    public int value() {
        return this.value;
    }

    @Override
    public int size()  {
        return this.cards.size();
    }

    @Override
    public GroupOfCards getCards() {
        return this.cards;
    }

    @Override
    public String name() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return "Triple";
    }
}


class RankBomb implements Combo {
    /*
     * In Thirteen, RankBombs are 4 cards of the same rank. A RankBomb can be 
     * countered with a bomb of the same type: a RankBomb. 
     */
    private final GroupOfCards cards;
    private boolean legal;
    private int value;
    RankBomb(GroupOfCards cards) {
        this.cards = cards;
        this.legal = true;
        this.value = Integer.MIN_VALUE;
        if (cards.size() != 4) {
            this.legal = false;
        }
        Rank repeatingRank = null;
        for (Card c: cards) {
            if (repeatingRank == null) {
                repeatingRank = c.rank();
            } else if (c.rank() != repeatingRank) {
                this.legal = false;
                break;
            }
            if (value < c.value()) {
                this.value = c.value();
            }
        }
    }

    @Override
    public boolean isLegal() {
        return this.legal;
    }

    @Override
    public boolean greaterThan(Combo otherCards) throws IllegalComboException {
        if (otherCards.name().equals("Deuce")) {
            return true;
        }
        if (!this.isLegal() || (!isLegal(otherCards))) {
            throw new IllegalComboException("combo is not legal or this is not legal");
        }
        return this.value() > otherCards.value();
    }

    @Override
    public int value() {
        return this.value;
    }

    @Override
    public boolean isLegal(Combo otherCards) {
        GroupOfCards ocards = otherCards.getCards();
        if (ocards.size() != 4) {
            return false;
        }
        Rank repeatingRank = null;
        for (Card c: ocards) {
            if (repeatingRank == null) {
                repeatingRank = c.rank();
            } else if (c.rank() != repeatingRank) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int size() {
        return this.cards.size();
    }

    @Override
    public GroupOfCards getCards() {
        return this.cards;
    }

    @Override
    public String name() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return "Rank Bomb";
    }
};

class FlushBomb implements Combo {
    /*
     * In this version of 13, only FlushBombs with 3 adjacent doubles
     * are allowed. FlushBombs can only be countered with other FlushBombs.
     */
    private GroupOfCards cards;
    private List<Card> sortedCards;
    private boolean legal;
    FlushBomb(GroupOfCards cards) {
        this.cards = cards;
        this.legal = true;
        if (cards.size() != 6) {
            this.legal = false;
        }
        sortedCards = new ArrayList<>(this.cards);
        Collections.sort(sortedCards);
        int index = 0;
        Card first, second;
        while (index < sortedCards.size()) {
            first = sortedCards.get(index);
            second = sortedCards.get(index + 1);
            if (first.rank() == second.rank()) {
                index += 2;
                continue;
            } else {
                this.legal = false;
                break;
            }
        }
    };
    
    @Override
    public boolean isLegal() {
        return this.legal;
    }

    @Override
    public int value() {
        int lastIndex = sortedCards.size() - 1;
        return sortedCards.get(lastIndex).value();
    }

    @Override
    public boolean greaterThan(Combo otherCards) throws IllegalComboException {
        if (otherCards.name().equals("Deuce")) {
            return true;
        } else if (!(this.isLegal(otherCards)) || !(this.isLegal())) {
            return false;
        }
        return value() > otherCards.value();
    }

    @Override
    public boolean isLegal(Combo otherCards) {
        GroupOfCards ocards = otherCards.getCards();
        Combo testBomb = new FlushBomb(ocards);
        return testBomb.isLegal();
    }

    @Override
    public int size() {
        return this.cards.size();
    }

    @Override
    public GroupOfCards getCards() {
        return this.cards;
    }

    @Override
    public String name() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return "Flush Bomb";
    }
};

class Flushes implements Combo {
    /* In Thirteen, Flushes have a minimum of 3 cards and a maximum of 13 cards.
     * The highest flush value has a value of the highest ranking card, 2 of H.
     */
    private final GroupOfCards cards;
    private final int size;
    private List<Card> sortedCards = null;
    private boolean legal;
    
    Flushes(GroupOfCards cards) {
        this.cards = cards;
        this.size = this.cards.size();
        this.legal = true;
        if (cards.size() < 3) {
            this.legal = false;
        }
        sortedCards = new ArrayList<>(this.cards);
        Collections.sort(sortedCards);
        int index = 1;
        Card prev = sortedCards.get(0);
        for (Card curr = sortedCards.get(index); index < sortedCards.size();
                                                    index += 1) {
            if (!prev.isNextAdjacentRank(curr)) {
                this.legal = false;
            } 
        }
    }

    @Override
    public boolean isLegal() {
        return this.legal;
    };

    @Override
    public int value() {
        if (sortedCards == null) {
            return 0;
        }
        int lastIndex = sortedCards.size() - 1;
        return sortedCards.get(lastIndex).value();
    }
    
    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean greaterThan(Combo otherCards) throws IllegalComboException {
        if (!isLegal(otherCards)) {
            throw new IllegalComboException("Combo is not compatible, did" 
                            + "not check isLegal");
        }
        return this.value() > otherCards.value();
    }

    @Override
    public boolean isLegal(Combo otherCards) {
        GroupOfCards ocards = otherCards.getCards();
        if ((ocards.size() < 3) || (ocards.size() != this.size())) {
            return false;
        }
        List<Card> sortedCards = new ArrayList<>(ocards);
        Collections.sort(sortedCards);
        int index = 1;
        Card prev = sortedCards.get(0);
        for (Card curr = sortedCards.get(index); index < sortedCards.size(); index += 1) {  
            if (prev.isNextAdjacentRank(curr)) {
                continue;
            } else {
                return false;
            }
        }
        return true;
    }

    @Override
    public GroupOfCards getCards() {
        return this.cards;
    }

    @Override
    public String name() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return "Flush";
    }
}

class Single implements Combo {
    /* Single cards can only be beaten by other single cards, or by any kind of
     * bombs.
     */
    private final GroupOfCards cards;
    private final int value;
    private boolean legal;
    
    Single(GroupOfCards cards) {
        this.cards = cards;
        this.legal = true;
        Iterator<Card> iter = cards.iterator();
        if (!iter.hasNext()) {
            this.value = -1;
        } else {
            this.value = iter.next().value();
        }
        if (cards.size() != 1) {
            this.legal = false;
        }
    };

    @Override
    public boolean isLegal() {
        return this.legal;
    }

    @Override
    public int value() {
        return value;
    }
    
    @Override
    public int size() {
        return 1;
    }

    @Override
    public boolean greaterThan(Combo otherCards) throws IllegalComboException {
        if (isLegal(otherCards) && this.isLegal()) {
            return value > otherCards.value();
        }
        throw new IllegalComboException();
    }

    @Override
    public boolean isLegal(Combo otherCards) {
        if (otherCards.getCards().size() == 1) {
            return true;
        }
        if (otherCards.getCards().size() == 4) {
            return new RankBomb(otherCards.getCards()).isLegal();
        } else if (otherCards.getCards().size() == 6) {
            return new FlushBomb(otherCards.getCards()).isLegal();
        }
        return false;
    }

    @Override
    public GroupOfCards getCards() {
        return this.cards;
    }

    @Override
    public String name() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return "Single";
    }
};

class Deuce extends Single {
    private boolean isLegal = true;
    public Deuce(GroupOfCards cards) {
        super(cards);
        if (cards.size() != 1) {
            isLegal = false;
        } else {
            Iterator<Card> iter = cards.iterator();
            while (iter.hasNext()) {
                Card d = iter.next();
                if (d.rank() != Rank.Two) {
                    isLegal = false;
                }
            }
        }
    }
    
    @Override
    public boolean isLegal() {
        return isLegal;
    }
    
    @Override
    public boolean isLegal(Combo combo) {
        GroupOfCards cards = combo.getCards();
        RankBomb rBomb = new RankBomb(cards);
        if (rBomb.isLegal()) {
            return true;
        }
        FlushBomb fBomb = new FlushBomb(cards);
        if (fBomb.isLegal()) {
            return true;
        }
        Deuce two = new Deuce(cards);
        if (two.isLegal()) {
            return true;
        }
        return false;
    }
    
    @Override
    public boolean greaterThan(Combo combo) {
        String name = combo.name();
        if (name.equals("Flush Bomb") || (name.equals("Rank Bomb"))) {
            return false;
        } else if (name.equals("Deuce") || name.equals("Single")) {
            if (value() > combo.value()) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String name() {
        return "Deuce";
    }
}


