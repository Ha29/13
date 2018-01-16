/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thirteen;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author steven
 */
public class HumanPlayer extends Player {

    public HumanPlayer(Board board, int ID) {
        super(board, ID);
    }
    
    @Override
    public GameState move(int code, GameState gameState)
                        throws IllegalComboException, NotGreaterThanException {
        Board board = gameState.getBoard();
        Combo combo = this.cardsInGroup().returnCombo();
        if (code == 0) {
            System.out.println("code = 0");
//            return gameState.makeNewGameState(board, false, null);
            return gameState.makeNewGameState(board, false, gameState.getRecentCombo());
        }
//        Combo combo = this.cardsInGroup().returnCombo();
        System.out.println("Your Combo Type" + combo.name());
        assert(combo != null);
        System.out.println();
        System.out.println("recent combo:");
        
        Combo recentCombo = gameState.getBoard().getRecentCombo();
        if (recentCombo != null) {
            for (Card c: recentCombo.getCards()) {
                System.out.println(c);
            }
        }
        System.out.println();
        if (!combo.isLegal()) {
            throw new IllegalComboException("Tell user combo is illegal, HP 30");
        } else if (recentCombo != null && recentCombo.greaterThan(combo)) {
            throw new NotGreaterThanException();
        }
        //create the board in GameState instead so you can control null boards
//        board = board.makeNewBoard(combo, this.hashCode());
//        return gameState.makeNewGameState(board, true);
        System.out.println("create new board in gamestate.java");
        return gameState.makeNewGameState(board, true, combo);
    }
}
