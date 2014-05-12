/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

/**
 *
 * @author lukas_000
 */
public class DiceView {
    EventHandler<ActionEvent> eventHandler;
    Button b;
    DiceView() {
        b = new Button();
        b.setId("dice1");
        b.setPrefHeight(100); b.setPrefWidth(100);
    }
    
    public Button getView() {
        return b;
    }
    
    public void setNumber(int num) {
        b.setId("dice" + num);
    }
    
    
    public void registerHandle(EventHandler<ActionEvent> e) {
        b.setOnAction(e);
        eventHandler = e;
    }
    
    public EventHandler<ActionEvent> getHandle() {
        return eventHandler;
    }
    
    public void removeHandler() {
        b.removeEventHandler(ActionEvent.ACTION, eventHandler);
        b.setOnAction(new EventHandler<ActionEvent>() {     
            public void handle(ActionEvent event) {  
                ;
            }
        });
    }
}
