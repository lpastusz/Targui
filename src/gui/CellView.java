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
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

/**
 *
 * @author lukas_000
 */
public class CellView {
    Button b;
    Cell cell2;  // debug only
    DomainController c;  // debug only
    
    private GridPane grid;
    CellView(Cell cell, DomainController controller) { // controller for debug only
        c = controller; // debug only
        cell2 = cell;   // debug only
        
        b = new Button();
        b.setPrefSize(40, 40); b.setMinSize(70, 70);
        b.setId(cell.getTCard().toString());
        
        cell.getTCardProperty().addListener(new ChangeListener() { 
            public void changed(ObservableValue observableValue, Object oldValue,Object newValue) {
                b.setId(((TCard)newValue).toString());
            }                 
        }); 
        
        cell.getCamelsProperty().addListener(new ChangeListener() { 
            public void changed(ObservableValue observableValue, Object oldValue,Object newValue) {
                if (Integer.parseInt(newValue.toString()) != 0)
                    b.setText(newValue.toString());
                else
                    b.setText("");
            }                 
        }); 
        
        
        
        
        if (cell.getOwner() != null)
            b.setStyle("-fx-border: 2px solid rgb(" + Color.web(cell.getOwner().getColor()).getRed() + "" + Color.web(cell.getOwner().getColor()).getGreen() + "" + Color.web(cell.getOwner().getColor()).getBlue() + ")");
           
        cell.getPlayerProperty().addListener(new ChangeListener() { 
            public void changed(ObservableValue observableValue, Object oldValue,Object newValue) {
                Double red = Color.web(((Player)newValue).getColor()).getRed()*255;
                String redInt = Integer.toString(red.intValue());
                Double green = Color.web(((Player)newValue).getColor()).getGreen()*255;
                String greenInt = Integer.toString(green.intValue());
                Double blue = Color.web(((Player)newValue).getColor()).getBlue()*255;
                String blueInt = Integer.toString(blue.intValue());
                /*
                if (redInt.length() == 1) redInt += redInt.substring(0, 1);
                if (greenInt.length() == 1) greenInt += greenInt.substring(0, 1);
                if (blueInt.length() == 1) blueInt += blueInt.substring(0, 1);
                */
                b.setStyle("-fx-border-width: 6px; fx-border-style: inset; -fx-border-color: rgba(" + redInt + ", " + greenInt + ", " + blueInt + ", 0.3);");
            }                 
        }); 
        
        grid = new GridPane();
        grid.add(b, 0, 0);
    }
    
    public GridPane getView() {
        return grid;
    }
}
