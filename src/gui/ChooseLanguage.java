/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import java.util.Locale;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

/**
 *
 * @author lukas_000
 */
public class ChooseLanguage {
    GridPane gridPane;
    StringProperty page;
    Locale locale;
    public ChooseLanguage() {
        gridPane = new GridPane();
        
        Button eng = new Button();
        Button cze = new Button();
        
        eng.setId("engFlag");
        cze.setId("czeFlag");
        
        eng.setPrefSize(339, 226);
        cze.setPrefSize(339, 226);
   
        eng.setOnAction(new EventHandler<ActionEvent>() {     
            public void handle(ActionEvent event) {
                locale = new Locale("en", "US");
                page.set("menu");
            }  
        });
        
        cze.setOnAction(new EventHandler<ActionEvent>() {     
            public void handle(ActionEvent event) {  
                locale = new Locale("cs", "CZE");
                page.set("menu");
            }  
        });
        
        gridPane.setId("rootLanguage");
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(50);
        
        gridPane.add(eng, 0, 0);
        gridPane.add(cze, 1, 0);     
    }
    
    public GridPane getView() {
        return gridPane;
    }
    
    public void setControlString(StringProperty pageParam) {
        page = pageParam;
    }
    
    public Locale getLocale() {
        return locale;
    }
}
