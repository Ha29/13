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
public enum Rank {
    Three(3, "3"), Four(4, "4"), Five(5, "5"), Six(6, "6"), 
    Seven(7, "7"), Eight(8, "8"), Nine(9, "9"), Ten(10, "10"), 
    J(11, "J"), Q(12, "Q"), K(13, "K"), A(14, "A"), Two(15, "2");
    private final int value;
    private final String name;
    Rank(int value, String name) {
        this.value = value;
        this.name = name;
    }
    public int value() {
        return this.value;
    }
    
    @Override
    public String toString() {
        return this.name;
    }
    
}
