/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thirteen;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author steven
 */
public class CardButton extends Button {
    
    private final ImageView iv;
    private final Card c;
    public CardButton(Card card) {
        this.c = card;
        Image image = new Image(card + ".png");
        this.iv = new ImageView(image);
        this.iv.setFitHeight(100);
        this.iv.setFitWidth(70);
        this.setGraphic(this.iv);
    };
    Card getCard() {
        return this.c;
    }
}
