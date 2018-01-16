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
public abstract class Player {
    private GroupOfCards cards = new GroupOfCards();
    private final int ID;
    private String name;
    
    Player(Board board, int ID) {
        this.ID = ID;
        this.name = "Player " + String.valueOf(ID);
    };
    
    public void changeName(String name) {
        this.name = name;
    }
    
    public String name() {
        return this.name;
    }
    
    public abstract GameState move(int code, GameState gameState)
                        throws IllegalComboException, NotGreaterThanException;
    void addToGroup(Card c) {
        cards.add(c);
    };
    
    void removeFromGroup(Card c) {
        cards.remove(c);
    };
    
    GroupOfCards cardsInGroup() {
        return cards;
    };
    
    void clearCardsInGroup() {
        cards.clear();
    };
    
    @Override
    public int hashCode() {
        return ID;
    };
    
    @Override
    public boolean equals(Object object) {
        if (object instanceof HumanPlayer) {
            return object.hashCode() == this.hashCode();
        } else if (object instanceof AIPlayer) {
            return object.hashCode() == this.hashCode();
        }
        return false;
    };
}
