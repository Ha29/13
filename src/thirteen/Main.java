/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thirteen;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.application.Application;
import java.lang.Math;
import java.util.Collections;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;

/**
 *
 * @author steven
 */
public class Main extends Application {
//    private static Game game = null;
    //Change structure and create the game after the initial stage
    //you should make a new game at the start of the initial stage anyway
//    public static void launch(Game game) {
//        GUIMain.game = game;
////        GUIMain gui = new GUIMain();
//        Application.launch(GUIMain.class);
//    }
    
    private Game thisGame;
    private VBox mainVBox;
    private VBox bottomBox;
    private HBox comboDisplay;
    private Stage stage;
//    private StackPane root;
    private BorderPane root;
    private GroupOfCards existingCombo;
    private HBox playerStats;
    private HBox enemyCombo;
    private List<TextField> textFields;
    
    public static void main(String[] args) {
        launch();
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        enemyCombo = new HBox();
        mainVBox = new VBox();
        comboDisplay = new HBox();
        playerStats = new HBox();
//        root = new StackPane();
        root = new BorderPane();
        existingCombo = new GroupOfCards();
        bottomBox = new VBox();
        textFields = new ArrayList<>();
        Scene scene = new Scene(root, 1200, 700, Color.GREEN);
        addMenu();
        displayInitialGameScreen();
//        refreshToPlayerCurtain();
//        addToMainVBox();
//        createPlayerStats();
//        displayEnemyCombo();
//        root.getChildren().add(mainVBox);
        root.setLeft(mainVBox);
        root.setBottom(bottomBox);
        stage.setTitle("Thirteen");
        stage.setScene(scene);

        stage.show();
    }
    
    void displayInitialGameScreen() {
        mainVBox.getChildren().clear();
        Button two = new Button("2 Player");
        two.setFont(new Font(30));
        two.setOnAction(new EventHandler<ActionEvent> () {
           @Override
           public void handle(ActionEvent e) {
               List<String> names = new ArrayList<>();
               for (int i = 0; i < 2; i++) {
                   String name = textFields.get(i).getText();
                   names.add(name);
               };
               thisGame = new Game(2, 0);
               for (int i = 0; i < 2; i++) {
                   thisGame.getPlayers().get(i).changeName(names.get(i));
               }
               refreshToPlayerCurtain();
           };
        });
        Button three = new Button("3 Player");
        three.setFont(new Font(30));
        three.setOnAction(new EventHandler<ActionEvent> () {
           @Override
           public void handle(ActionEvent e) {
               List<String> names = new ArrayList<>();
               for (int i = 0; i < 3; i++) {
                   String name = textFields.get(i).getText();
                   names.add(name);
               };
               thisGame = new Game(3, 0);
               for (int i = 0; i < 3; i++) {
                   thisGame.getPlayers().get(i).changeName(names.get(i));
               }
               refreshToPlayerCurtain();
           };
        });
        
        Button four = new Button("4 Player");
        four.setFont(new Font(30));
        four.setOnAction(new EventHandler<ActionEvent> () {
           @Override
           public void handle(ActionEvent e) {
               List<String> names = new ArrayList<>();
               for (int i = 0; i < 4; i++) {
                   String name = textFields.get(i).getText();
                   names.add(name);
               };
               thisGame = new Game(4, 0);
               for (int i = 0; i < 4; i++) {
                   thisGame.getPlayers().get(i).changeName(names.get(i));
               }
               refreshToPlayerCurtain();
           };
        });
        HBox buttonHBox = new HBox();
        buttonHBox.getChildren().add(two);
        buttonHBox.getChildren().add(three);
        buttonHBox.getChildren().add(four);
        
        List<HBox> list = new ArrayList<>();
        textFields.clear();
        for (int j = 0; j < 4; j++) {
            String i = String.valueOf(j);
            Label label = new Label("Player " + i + " Name:");
            TextField tField = new TextField();
            //find a way to add old textfield text into new one
            tField.setText("");
            HBox hb = new HBox();
            hb.getChildren().addAll(label, tField);
            hb.setSpacing(10);
            list.add(hb);
            textFields.add(tField);
        }
        mainVBox.getChildren().addAll(list);   
        mainVBox.getChildren().add(buttonHBox);
    }
    
    void refresh() {
        thisGame.nextPlayer().clearCardsInGroup();
        mainVBox.getChildren().clear();
        createPlayerStats();
        addToMainVBox();
//        addMenu();
    }
    
    void refreshToPlayerCurtain() {
        mainVBox.getChildren().clear();
        BorderPane pane = new BorderPane();
        String ID = String.valueOf(thisGame.nextPlayer().hashCode());
        String name = String.valueOf(thisGame.nextPlayer().name());
//        Text text = new Text("Player " + ID + " is next");
//        text.setFont(new Font(40));
        Button button = new Button("Player " + ID + ": " + name +  " is next!");
        button.setFont(new Font(40));
        button.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent e) {
               refresh();
           }
        });
        pane.setCenter(button);
        mainVBox.getChildren().add(pane);
    }
    
    void refreshToEndScreen() {
        mainVBox.getChildren().clear();
        BorderPane pane = new BorderPane();
        Player winningPlayer = thisGame.getGameState().getRecentPlayer();
        String ID = String.valueOf(winningPlayer.hashCode());
        String name = String.valueOf(winningPlayer.name());
        Button button = new Button("Player " + ID + ": " + name +  " won!");
        button.setFont(new Font(40));
        button.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent e) {
               displayInitialGameScreen();
           }
        });
        pane.setCenter(button);
        mainVBox.getChildren().add(pane);
    }
    
    public void addMenu() {
        MenuBar menuBar = new MenuBar();
        Menu menuOptions = new Menu("Options");
        menuBar.getMenus().addAll(menuOptions);
        MenuItem reset = new MenuItem("Reset");
        reset.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent event) {
//               thisGame.reset();
//                try {
//                    thisGame.nextPlayer().clearCardsInGroup();
//                    mainVBox.getChildren().clear();
//                    refreshComboDisplay();
//                    addToMainVBox();
//                    stage.show();
//                } catch (Exception ex) {
////                    Logger.getLogger(GUIMain.class.getName()).log(Level.SEVERE, null, ex);
//                    System.out.println("your messing up");
//                }
                displayInitialGameScreen();
           }
        });
        menuOptions.getItems().add(reset);
//        root.getChildren().addAll(menuBar);
        root.setTop(menuBar);
    }
    public void addToMainVBox() {        
        Button displayOldCardsButton = new Button();
        displayOldCardsButton.setText("Display Old Cards");
        displayOldCardsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                VBox box = createOldCardVBox();
                StackPane secondaryLayout = new StackPane();
                secondaryLayout.getChildren().add(box);
                
                Scene secondScene = new Scene(secondaryLayout, 1000, 500);

                Stage secondStage = new Stage();
                secondStage.setTitle("Played Cards");
                secondStage.setScene(secondScene);
                
                secondStage.initModality(Modality.WINDOW_MODAL);
 
                // Specifies the owner Window (parent) for new window
                secondStage.initOwner(stage);
                
                //Set position of second window, related to primary window.
                secondStage.setX(stage.getX() + 250);
                secondStage.setY(stage.getY() + 100);
 
                secondStage.show();
            }
 
        });
        mainVBox.getChildren().add(displayOldCardsButton);
        Text yourHand = new Text("Your Hand: ");
        yourHand.setFont(new Font(40));
        mainVBox.getChildren().add(yourHand);
//        thisGame.nextPlayer().clearCardsInGroup();
        HBox playerHand = createHand();
        mainVBox.getChildren().add(playerHand);
        Text comboText = new Text("Your Combo Hand: ");
        comboText.setFont(new Font(40));
        mainVBox.getChildren().add(comboText);
        mainVBox.getChildren().add(playerStats);
        mainVBox.getChildren().add(comboDisplay);
        chooseToPlay();
        displayEnemyCombo();
        mainVBox.getChildren().add(enemyCombo);
    }
    
    public HBox createHand() {
        GroupOfCards hand = thisGame.getHand();
        List<Card> sort = new ArrayList<>();
        sort.addAll(hand);
        Collections.sort(sort);
        HBox box = new HBox();
        for (Card c: sort) {
            CardButton cButton = new CardButton(c);
            cButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                    thisGame.nextPlayer().addToGroup(c);
                    existingCombo.add(c);
                    refreshComboDisplay();
                }
                
            });
            box.getChildren().add(cButton);
//            ImageView view = getCardImageView(c);
//            box.getChildren().add(view);
        }
        return box;
    };
    
    public void displayEnemyCombo() {
        // display Enemy combo
        enemyCombo.getChildren().clear();
        GameState state = thisGame.getGameState();
        Board board = state.getBoard();
        Combo recentCombo = board.getRecentCombo();
        Text newText = new Text("Enemy Combo:");
        enemyCombo.getChildren().add(newText);
        if (recentCombo == null) {
            System.out.println("197 GUIMAIN");
            return;
        }
        if (recentCombo.getCards().size() == 0) {
            System.out.println("GUIMAIN 201");
        }
        List<Card> sorted = new ArrayList<>();
        sorted.addAll(recentCombo.getCards());
        Collections.sort(sorted);
        for (Card c: sorted) {
            ImageView view = getCardImageView(c);
            enemyCombo.getChildren().add(view);
        }  
    }
    
    public void refreshComboDisplay() {
        comboDisplay.getChildren().clear();
        GameState state = thisGame.getGameState();
        GroupOfCards combo = thisGame.nextPlayer().cardsInGroup();
        List<Card> sorted = new ArrayList<>(combo.size());
        sorted.addAll(combo);
        Collections.sort(sorted);
        for (Card c: sorted) {
            CardButton comboButton = new CardButton(c);
            comboButton.setOnAction(new EventHandler<ActionEvent>() {
               @Override
               public void handle(ActionEvent t) {
                   existingCombo.remove(c);
                   thisGame.nextPlayer().removeFromGroup(c);
                   refreshComboDisplay();
               }
            });
            comboDisplay.getChildren().add(comboButton);
        }
        stage.show();
//        List<Card> sorted = new ArrayList<>(existingCombo.size());
//        sorted.addAll(existingCombo);
//        Collections.sort(sorted);
//        for (Card c: sorted) {
//            CardButton comboButton = new CardButton(c);
//            comboButton.setOnAction(new EventHandler<ActionEvent>() {
//                @Override
//                public void handle(ActionEvent t) {
//                    existingCombo.remove(c);
//                    game.nextPlayer().removeFromGroup(c);
//                    refreshComboDisplay();
//                }
//            });
//            comboDisplay.getChildren().add(comboButton);
//        }
//        stage.show();
    }
    
    public ImageView getCardImageView(Card c) {
        Image im = new Image(c + ".png");
        ImageView view = new ImageView(im);
        view.setFitHeight(100);
        view.setFitWidth(70);
        return view;
    };
    
    
    public VBox createOldCardVBox() {
        GameState state = thisGame.getGameState();
        Board board = state.getBoard();
        List<ImageView> list = new ArrayList<>();
        Image im;
        ImageView view;
        GroupOfCards oldCards = board.getOldCards();
        List<Card> oldCardList = new ArrayList<>();
        oldCardList.addAll(oldCards);
        Iterator<Card> cardIterator = oldCardList.iterator();
        while(cardIterator.hasNext()) {
            Card nextCard = cardIterator.next();
            System.out.println(nextCard.toString());
            im = new Image(nextCard.toString() + ".png");
            view = new ImageView(im);
            view.setFitHeight(100);
            view.setFitWidth(70);
            list.add(view);
        }
        HBox aBox;
        List<HBox> hBoxes = new ArrayList<HBox>();
        for (int i = 0; i < 5; i += 1) {
            aBox = new HBox();
            aBox.setPrefHeight(100);
            aBox.setFillHeight(true);
            if (i <= 3) {
                for (int j = i * 13; j < 13 * (i+ 1); j += 1) {
                    if (j < list.size()) {
                        aBox.getChildren().add(list.get(j));
                    } else {
                        Rectangle r = new Rectangle();
                        r.setHeight(100);
                        r.setWidth(70);
                        r.setFill(Color.WHITE);
                        r.setStroke(Color.BLACK);
                        aBox.getChildren().add(r);
                    }
                }
            }
            if (i == 4) {
                aBox.setPrefHeight(30);
                aBox.setFillHeight(true);
            }
            hBoxes.add(aBox);
        }
        VBox vBox = new VBox();
        Text text = new Text("Played Cards");
        text.setFont(new Font(40));
        text.setFill(Color.WHITE);
        vBox.getChildren().add(text);
        for (HBox hBox: hBoxes) {
            vBox.getChildren().add(hBox);
        }
        return vBox;
    };
    
    public void chooseToPlay() {
        GameState state = thisGame.getGameState();
        Board board = state.getBoard();
        Button playButton = new Button();
        playButton.setText("Play Combo");
        playButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                try {
                    thisGame.play(1);
//                    comboDisplay.getChildren().clear() || recentCombo.greaterThan(combo);
                    comboDisplay.getChildren().clear();
                    existingCombo.clear();
                    bottomBox.getChildren().clear();
                    if (thisGame.playerWon()) {
                        refreshToEndScreen();
                    } else {
                        refreshToPlayerCurtain();
                    }
//                    refresh();
                } catch (IllegalComboException ex) {
                    Text msg = new Text("Illegal combo or incompatible with current combo");
                    System.out.println("****");
                    for (Card c: thisGame.nextPlayer().cardsInGroup()) {
                        System.out.println(c);
                    }
                    msg.setFont(new Font(20));
                    bottomBox.getChildren().clear();
                    bottomBox.getChildren().add(msg);
                    stage.show();
                } catch (NotGreaterThanException ex) {
                    Text msg = new Text("Your combo is not big enough");
//                    System.out.println("currCombo: " + String.valueOf(board.getRecentCombo().value()));
                    msg.setFont(new Font(20));
                    bottomBox.getChildren().clear();
                    bottomBox.getChildren().add(msg);
                    stage.show();
                }
            }
        });
        HBox playOrPassBox = new HBox();
        playOrPassBox.getChildren().add(playButton);
//        mainVBox.getChildren().add(playButton);
        Button passButton = new Button();
        passButton.setText("Pass/Play Nothing");
        passButton.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent t) {
               try {
                   thisGame.play(0);
                   comboDisplay.getChildren().clear();
                   existingCombo.clear();
                   bottomBox.getChildren().clear();
                   refreshToPlayerCurtain();
               } catch (IllegalComboException ex) {
                   System.err.println("Threw IllegalComboException BADDD");
                   System.exit(1);
               } catch (NotGreaterThanException ex) {
                   System.err.println("Threw NotGreaterThanException BADD");
                   System.exit(1);
               };
           };
        });
        playOrPassBox.getChildren().add(passButton);
        mainVBox.getChildren().add(playOrPassBox);
    }
    
    public void createPlayerStats() {
        playerStats.getChildren().clear();
        GameState state = thisGame.getGameState();
        Board board = state.getBoard();
        LinkedList<Player> order = state.getNextPlayers();
        Text title = new Text("Order of next players: ");
        playerStats.getChildren().add(title);
        while (!order.isEmpty()) {
            Player p = order.removeFirst();
            int pID = p.hashCode();       
            int size = board.getPlayerHand(pID).size();
            String stat = "Player " + String.valueOf(pID) + " has " +
                                String.valueOf(size) + " cards";
            Text playerText = new Text(stat);
            playerStats.getChildren().add(playerText);
            
        }
        assert(state.getNextPlayers().isEmpty() == true);
        order = state.getNextPlayers();
        assert(order.isEmpty() == false);
//        Text title = new Text("Order of next players: ");
        Text excludedTitle = new Text("Excluded players");
        playerStats.getChildren().add(excludedTitle);
        System.out.println(order.isEmpty());
        while (!order.isEmpty()) {
            System.out.println(String.valueOf(order.removeFirst().hashCode()));
        }
        List<Player> list = new ArrayList<>();
        list.addAll(state.getNextPlayers());
        for (Player p: list) {
            if (list.contains(p) == false) {
                String stat = "Player " + String.valueOf(p.hashCode());
                Text playerText = new Text(stat);
                playerStats.getChildren().add(playerText);
            }
        }
    }
            
}
