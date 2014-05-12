/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import domain.DomainController;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author lukas_000
 */
public class LoadGame {
    private DomainController controller;
    GridPane gridPane;
    SimpleStringProperty page;
    ResourceBundle labels;
    
    
    public LoadGame(DomainController controllerParam, ResourceBundle labelsParam) {
        labels = labelsParam;
        controller = controllerParam;  
        gridPane = new GridPane();
        gridPane.setId("rootMenu");
        
        Button loadButton = new Button(labels.getString("LoadGameButton"));
        Button backButton = new Button(labels.getString("BackButton"));
        
        loadButton.setPrefSize(120, 60);
        backButton.setPrefSize(120, 60);
        
        final ListView gameList = new ListView();
        gameList.setPrefSize(200, 300);
       
        
        backButton.setOnAction(new EventHandler<ActionEvent>() {     
            public void handle(ActionEvent event) {  
                page.set("menu");
            }
        });
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.add(gameList, 0, 0, 1, 5);
        
        VBox buttons = new VBox();
        buttons.getChildren().add(loadButton);
        buttons.getChildren().add(backButton);
        buttons.setSpacing(10);
        buttons.setAlignment(Pos.BOTTOM_CENTER);
        gridPane.add(buttons, 1, 4, 1, 1);
        
        final Map<String, Integer> gameMap = controller.getGames();
        gameList.setItems(FXCollections.observableArrayList (gameMap.keySet()));
        
        
        loadButton.setOnAction(new EventHandler<ActionEvent>() {     
            public void handle(ActionEvent event) {  
                int id = gameMap.get(gameList.getSelectionModel().selectedItemProperty().getValue().toString());
                // load game by ID
                controller.loadGame(id);
                controller.setGameLoaded(true);
                page.set("game");
            }
        });
    }
    
    public void setControlString(SimpleStringProperty pageParam) {
        page = pageParam;
    }
    
    public GridPane getView() {
        return gridPane;
    }
}
