/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 *
 * @author lukas_000
 */
public class PlayerRowView {
    private final HBox row = new HBox();
    private boolean enabled = true;
    private TextField name;
    private ColorPicker color;
    private ComboBox sector;
    
    public PlayerRowView() {  
        Image img = new Image("gui/img/person.png");
        ImageView imgView = new ImageView(img);
        imgView.setFitHeight(30);
        imgView.setFitWidth(30);
        
        name = new TextField();
        color = new ColorPicker();
        ObservableList<String> options =  FXCollections.observableArrayList(
            "1",
            "2",
            "3",
            "4"
        );
        sector = new ComboBox(options);
        
        row.setAlignment(Pos.CENTER);
        row.setSpacing(10);
        
        row.getChildren().add(0, imgView);
        row.getChildren().add(1, name);
        row.getChildren().add(2, color);
        row.getChildren().add(3, sector);
        //row.getChildren().addAll(img, name, color, sector); 
    }
    
    public HBox getView() {
        return row;
    }   
    
    public boolean isEnabled() {
        return enabled;
    }
    
    public void disable() {
        enabled = false;
        name.setEditable(false);
        name.setDisable(true);
        color.setEditable(false);
        color.setDisable(true);
        sector.setEditable(false);
        sector.setDisable(true);
    }
    
    public String getName() {
        return name.getText();
    }
    
    public String getColor() {
        return color.getValue().toString();
    }
    
    public int getSector() {
        if (sector.getValue() == null)
            return 0;
        return Integer.parseInt(sector.getValue().toString());
    }
}
