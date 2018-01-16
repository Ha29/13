/*
 * Each instance of GameState is the current
 * state of the game.
 */
package thirteen;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 *
 * @author steven
 */
public class GameState {
    /* Instantiated by a board, the players allowed to participate in the 
     * current battle, and the player that made the most recent move. 
     */
    private final Board board;
    private final List<Player> players;
    private final Player recentPlayer;
    private final LinkedList<Player> nextPlayerOrders;
    private final LinkedList<Player> originalPlayerOrders;
    
    GameState(List<Player> players) {
        this.board = new Board();
        this.players = players;
        this.recentPlayer = null;
        nextPlayerOrders = new LinkedList<>();
        originalPlayerOrders = new LinkedList<>();
        for (Player p: players) {
            nextPlayerOrders.add(p);
            originalPlayerOrders.add(p);
        }
    }
    
    private GameState(Board board, List<Player> players, Player recentPlayer, 
                                LinkedList<Player> nextPlayerOrders) {
        /* Use this instantiation when there is a specific list of players
         * that need to go in that order. 
         */
        this.board = board;
        this.players = players;
        this.recentPlayer = recentPlayer;
        this.nextPlayerOrders = nextPlayerOrders;
        originalPlayerOrders = new LinkedList<>();
        originalPlayerOrders.addAll(players);
    };
    
    private GameState(Board board, List<Player> players, Player recentPlayer) {
        /* Use this instantiation when a player wins the fight and plays
         * a new combo. This will allow all players to participate
         * in the correct ordering. 
         */
        this.board = board;
        this.players = players;
        this.recentPlayer = recentPlayer;
        this.nextPlayerOrders = new LinkedList<>();
        LinkedList<Player> lastPlayers = new LinkedList<>();
        for (Player player: players) {
            if (player != recentPlayer) {
                lastPlayers.add(player);
            } else {
                nextPlayerOrders.add(player);
            }
        }
        nextPlayerOrders.addAll(lastPlayers);
        originalPlayerOrders = new LinkedList<>();
        originalPlayerOrders.addAll(players);
    }
    
    private GameState(Board board, List<Player> players) {
        this.board = board;
        this.players = players;
        this.recentPlayer = null;
        this.nextPlayerOrders = new LinkedList<>();
        List<Player> lastPlayers = new LinkedList<>();
        for (Player player: players) {
            if (player != recentPlayer) {
                lastPlayers.add(player);
            } else {
                nextPlayerOrders.add(player);
            }
        }
        nextPlayerOrders.addAll(lastPlayers);
        originalPlayerOrders = new LinkedList<>();
        originalPlayerOrders.addAll(players);
    }
    
    private LinkedList<Player> resetPlayerOrder(Player startingPlayer) {
        //function to use when there is only one player 
        //in the order left and you want to put all four 
        //players back in
        //starting player starts the fight between the 4
        Iterator<Player> ogOrder = originalPlayerOrders.iterator();
        LinkedList<Player> playersGoingLast = new LinkedList<>();
        LinkedList<Player> playersGoingFirst = new LinkedList<>();
        boolean metStarting = false;
        while (ogOrder.hasNext()) {
            Player next = ogOrder.next();
            if (next.equals(startingPlayer)) {
                metStarting = true;
                continue;
            }
            if (metStarting) {
                playersGoingFirst.add(next);
            } else {
                playersGoingLast.add(next);
            }
        }
        playersGoingFirst.addAll(playersGoingLast);
        return playersGoingFirst;
    }
    
    private LinkedList<Player> getNewPlayerOrder(Player startingPlayer) {
        Iterator<Player> ogOrder = originalPlayerOrders.iterator();
        LinkedList<Player> playersGoingLast = new LinkedList<>();
        LinkedList<Player> playersGoingFirst = new LinkedList<>();
        boolean metStarting = false;
        while (ogOrder.hasNext()) {
            Player nextPlayer = ogOrder.next();
            if (metStarting) {
                playersGoingFirst.add(nextPlayer);
            } else if (nextPlayer.equals(startingPlayer)) {
                metStarting = true;
//                playersGoingLast.add(nextPlayer);
            } else {
                playersGoingLast.add(nextPlayer);
            }
        }
        playersGoingFirst.addAll(playersGoingLast);
        return playersGoingFirst;
    }
    
    GameState makeNewGameState(Board board, boolean playerMoved, Combo recentCombo) {
        LinkedList<Player> newPlayerOrder = new LinkedList<>();
        Player recentPlayer = nextPlayerOrders.removeFirst();
        boolean nullBoard = false;
//        if (nextPlayerOrders.size() == 1) {
//            LinkedList<Player> newOrder = getNewPlayerOrder(recentPlayer);
//            nextPlayerOrders.addAll(newOrder);
//        }
//        if (nextPlayerOrders.isEmpty()) {
//            LinkedList<Player> newOrder = getNewPlayerOrder(recentPlayer);
//            nextPlayerOrders.addAll(newOrder);
//            nullBoard = true;
//        }
//        if (nextPlayerOrders.isEmpty()) {
        if (nextPlayerOrders.isEmpty()) {
            LinkedList<Player> newOrder = getNewPlayerOrder(recentPlayer);
            nextPlayerOrders.addAll(newOrder);
            if (!playerMoved) {
//                LinkedList<Player> newOrder = getNewPlayerOrder(recentPlayer);
//                nextPlayerOrders.addAll(newOrder);
                nullBoard = true;
            }
        } else if (nextPlayerOrders.size() == 1 && !playerMoved) {
            LinkedList<Player> newOrder = resetPlayerOrder(nextPlayerOrders.getFirst());
//            newOrder.removeFirst();
            nextPlayerOrders.addAll(newOrder);
            nullBoard = true;
        }
        newPlayerOrder.addAll(nextPlayerOrders);
        if (playerMoved && !nullBoard) {
            newPlayerOrder.addLast(recentPlayer);
        }
        Board newBoard;
        if (nullBoard) {
            System.out.println("null board called");
            newBoard = board.makeNullBoard();
        } else {
            newBoard = board.makeNewBoard(recentCombo, recentPlayer.hashCode());
        }
        GameState newState = new GameState(newBoard, this.players, recentPlayer, 
                                                    newPlayerOrder);
        return newState;

    }
    
    GameState newGameState() {
        return new GameState(players);
    }
    
    LinkedList<Player> getNextPlayers() {
        LinkedList<Player> link = new LinkedList<>();
        link.addAll(nextPlayerOrders);
        return link;
    }
    
    Iterator<Player> getNextPlayersIterator() {
        return nextPlayerOrders.iterator();
    }
    
    Board getBoard() {
        return this.board;
    }
    
    Player getRecentPlayer() {
        return this.recentPlayer;
    }
    
    Player getNextPlayer() {
        return nextPlayerOrders.get(0);
    }
    
    GroupOfCards getCardsOfNextPlayer() {
        int index = players.indexOf(getNextPlayer());
        return board.getPlayerHand(index);
    }
    
    boolean playerWon() {
        int index = players.indexOf(recentPlayer);
        return board.getPlayerHand(index).size() == 0;
    }
    
    List<Player> getPlayers() {
        return players;
    }
    
    Combo getRecentCombo() {
        return board.getRecentCombo();
    }
}
