/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import domain.DomainController;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author lukas_000
 */
public class MainMenu {
    private DomainController controller;
    GridPane gridPane;
    SimpleStringProperty page;
    ResourceBundle labels;
    
    
    public MainMenu(DomainController controllerParam, final Stage primaryStage, ResourceBundle labelsParam) {
        labels = labelsParam;
        controller = controllerParam;  
        gridPane = new GridPane();
        gridPane.setId("rootMenu");
        
        Button continueGame = new Button(labels.getString("ContinueGameButton"));
        Button newGame = new Button(labels.getString("NewGameButton"));
        Button loadGame = new Button(labels.getString("LoadGameButton"));
        Button quitGame = new Button(labels.getString("QuitGameButton"));
        
        continueGame.setPrefSize(120, 60);
        newGame.setPrefSize(120, 60);
        loadGame.setPrefSize(120, 60);
        quitGame.setPrefSize(120, 60);
        
        newGame.setOnAction(new EventHandler<ActionEvent>() {     
            public void handle(ActionEvent event) { 
                controller.startGame();
                controller.placeTCardsOnBoard();
                page.set("registration");
            }
        });
        
        loadGame.setOnAction(new EventHandler<ActionEvent>() {     
            public void handle(ActionEvent event) {  
                page.set("load");
            }
        });
        
        quitGame.setOnAction(new EventHandler<ActionEvent>() {     
            public void handle(ActionEvent event) {  
                primaryStage.close();
            }
        });
        
        continueGame.setOnAction(new EventHandler<ActionEvent>() {     
            public void handle(ActionEvent event) {  
                page.set("game");
            }
        });
        
        
        if (controller.isGameInProgress())
            gridPane.add(continueGame, 0, 0);
        gridPane.add(newGame, 0, 1);
        gridPane.add(loadGame, 0, 2);
        gridPane.add(quitGame, 0, 3);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER);
    }
    
    public void setControlString(SimpleStringProperty pageParam) {
        page = pageParam;
    }
    
    public GridPane getView() {
        return gridPane;
    }
}
