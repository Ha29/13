/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thirteen;

/**
 *
 * @author steven
 */
public class Card implements Comparable<Card>{
    private final Suit suit;
    private final Rank rank;
    
    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }
    int value() {
        return this.suit.value() + 4 * this.rank.value();
    }
    
    Suit suit() {
        return this.suit;
    }
    
    Rank rank() {
        return this.rank;
    }
    
    boolean isNextAdjacentRank(Card otherCard) {
        int diff = otherCard.rank().value() - this.rank().value();
        return diff == 1;
    }
    
    @Override
    public int hashCode() {
        return value();
    }
    
    @Override
    public boolean equals(Object o) {
        if (this.hashCode() == o.hashCode()) {
            return true;
        }
        return false;
    }


    @Override
    public int compareTo(Card o) {
        if (value() > o.hashCode()) {
            return 1;
        } else if (value() < o.hashCode()) {
            return -1;
        } else {
            return 0;
        }
    }
    
    @Override
    public String toString() {
        String rankString = rank.toString();
        String suitString = suit.toString();
        return rankString + "of" + suitString;
    }
}
