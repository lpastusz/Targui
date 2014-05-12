/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import domain.Cell;
import domain.DomainController;
import domain.Player;
import domain.TCard;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

/**
 *
 * @author lukas_000
 */
public class CellView {
    Button b;
    int row, column;
    EventHandler<ActionEvent> eventHandler;
    
    private GridPane grid;
    CellView(final Cell cell, DomainController controller, int rowParam, int columnParam) { // controller for debug only
        
        row = rowParam;
        column = columnParam;
        b = new Button();
        b.setPrefSize(40, 40); b.setMinSize(70, 70);
        
        if (cell.getCamels() > 0)
            b.setId(cell.getTCard().toString() + "Camel");
        else
            b.setId(cell.getTCard().toString());
        
        cell.getTCardProperty().addListener(new ChangeListener() { 
            public void changed(ObservableValue observableValue, Object oldValue,Object newValue) {
                String id = ((TCard)newValue).toString();
                if (cell.getCamels() != 0)
                    id += "Camel";
                b.setId(id);
            }                 
        }); 
        
        
        cell.getCamelsProperty().addListener(new ChangeListener() { 
            public void changed(ObservableValue observableValue, Object oldValue,Object newValue) {
                if (Integer.parseInt(newValue.toString()) != 0) {
                    b.setText(newValue.toString());
                    b.setId(cell.getTCard().toString() + "Camel");
                }
                else {
                    b.setText("");
                    b.setId(cell.getTCard().toString());
                }
            }                 
        }); 
        
        
        if (cell.getCamels() != 0)
            b.setText(String.valueOf(cell.getCamels()));
        
        
        if (cell.getOwner() != null) {
            Double red = (Color.web(cell.getOwner().getColor()).getRed())*255;
            String redInt = Integer.toString(red.intValue());
            Double green = (Color.web(cell.getOwner().getColor()).getGreen())*255;
            String greenInt = Integer.toString(green.intValue());
            Double blue = (Color.web(cell.getOwner().getColor()).getBlue())*255;
            String blueInt = Integer.toString(blue.intValue());
            b.setStyle("-fx-border-width: 6px; fx-border-style: inset; -fx-border-color: rgba(" + redInt + ", " + greenInt + ", " + blueInt + ", 0.6);");
        }

        cell.getPlayerProperty().addListener(new ChangeListener() { 
            public void changed(ObservableValue observableValue, Object oldValue,Object newValue) {
                Double red = Color.web(((Player)newValue).getColor()).getRed()*255;
                String redInt = Integer.toString(red.intValue());
                Double green = Color.web(((Player)newValue).getColor()).getGreen()*255;
                String greenInt = Integer.toString(green.intValue());
                Double blue = Color.web(((Player)newValue).getColor()).getBlue()*255;
                String blueInt = Integer.toString(blue.intValue());
                b.setStyle("-fx-border-width: 6px; fx-border-style: inset; -fx-border-color: rgba(" + redInt + ", " + greenInt + ", " + blueInt + ", 0.6);");
            }                 
        }); 
        
        grid = new GridPane();
        grid.add(b, 0, 0);
    }
    
    public GridPane getView() {
        return grid;
    }
    
    public Button getButton() {
        return b;
    }
    
    public int getRow() {
        return row;
    }
    
    public int getColumn() {
        return column;
    }
    
    public void registerHandle(EventHandler<ActionEvent> e) {
        b.setOnAction(e);
        eventHandler = e;
    }
    
    public EventHandler<ActionEvent> getHandle() {
        return eventHandler;
    }
    
    public void removeHandler() {
       // b.removeEventHandler(ActionEvent.ACTION, eventHandler);
        b.setOnAction(new EventHandler<ActionEvent>() {     
            public void handle(ActionEvent event) {  
                ;
            }
        });
    }
}

