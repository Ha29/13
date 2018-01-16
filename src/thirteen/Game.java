/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thirteen;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.lang.Math;

/**
 *
 * @author steven
 */
public class Game {
    private Stack<GameState> previousStates = new Stack<>();
    private GameState gameState;
    private LinkedList<GameState> forwardStates = new LinkedList<>();
    private int numHumanPlayers;
    private int numAI;
    
    Game(int numHumanPlayers, int numAI) {
        this.numHumanPlayers = 0;
        this.numAI = 0;
        List<Player> players = new ArrayList<>();
        int i = 0;
        Board newBoard = new Board();
        for (; i < numHumanPlayers; i += 1) {
            Player newPlayer = new HumanPlayer(newBoard, i);
            players.add(newPlayer);
            this.numHumanPlayers += 1;
        }
        int limit = Math.min(numAI, 4-numHumanPlayers);
        for (; i < limit; i += 1) {
            Player newPlayer = new AIPlayer(newBoard, i);
            players.add(newPlayer);
            this.numAI += 1;
        }
        gameState = new GameState(players);
    }
    
    Game(GameState gameState) {
        this.gameState = gameState;
        previousStates = new Stack<>();
        forwardStates = new LinkedList<>();
    }
    
    void reset() {
        previousStates.clear();
        forwardStates.clear();
        gameState = gameState.newGameState();
    }
    
    void undo() {
        GameState gameState = previousStates.pop();
        forwardStates.addFirst(this.gameState);
        this.gameState = gameState;
    }
    
    void fastForward() {
        if (forwardStates.isEmpty()) {
            return;
        }
        previousStates.add(gameState);
        gameState = forwardStates.removeFirst();        
    }
    
    void play(int input) throws IllegalComboException, NotGreaterThanException {
        GameState nextState = nextPlayer().move(input, this.gameState);
        previousStates.push(gameState);
        gameState = nextState;
        forwardStates = new LinkedList<>();
    }
    
    boolean playerWon() {
        return gameState.playerWon();
    }
    
    Player nextPlayer() {
        return gameState.getNextPlayer();
    }
    
    GameState getGameState() {
        return gameState;
    }
    
    GroupOfCards getHand() {
        return gameState.getCardsOfNextPlayer();
    }
    
    List<Player> getPlayers() {
        return gameState.getPlayers();
    }
}
