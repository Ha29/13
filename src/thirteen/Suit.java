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
public enum Suit {
    Spades(0, "S"), Clubs(1, "C"), 
    Diamonds(2, "D"), Hearts(3, "H");
    private final int value;
    private final String name;
    Suit(int value, String name) {
        this.value = value;
        this.name = name;
    }

    int value() {
        return this.value;
    }
    
    @Override
    public String toString() {
        return this.name;
    }
    
}
