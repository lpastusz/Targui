/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import javafx.beans.property.IntegerProperty;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author lukas_000
 */
public class PlayerInformation {
    Label money;
    private GridPane grid;
    
    public PlayerInformation(String nameParam, String colorParam, IntegerProperty silver) {
        Label name = new Label(nameParam);
        Rectangle rect = new Rectangle(); rect.setWidth(40); rect.setHeight(5); rect.setFill(Color.web(colorParam));
        money = new Label();
        money.setText("10");
        
        ImageView img = new ImageView(new Image("gui/img/coin.png"));
        img.setFitHeight(20); img.setFitWidth(20);
        
        
        silver.addListener(new ChangeListener() { 
            public void changed(ObservableValue observableValue, Object oldValue,Object newValue) {
                money.setText(newValue.toString());
            }                 
        }); 
        
        
        grid = new GridPane();
        grid.add(rect, 0, 0, 3, 1);
        grid.add(name, 1, 1, 2, 1);
        grid.add(img, 1, 2);
        grid.add(money, 2, 2);
    }
    
    public GridPane getView() {
        return grid;
    }
}
