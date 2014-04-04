/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import domain.DomainController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.beans.value.ChangeListener;

/**
 *
 * @author lukas_000
 */
public class GuiApplication {
    private DomainController controller;
    GridPane gridPane = new GridPane();
    Scene scene;
    SimpleStringProperty page;
    
    //pages
    PlayerRegistration playerRegistration;
    GamePlay gamePlay;
    
    public GuiApplication(DomainController controllerParam) {
        controller = controllerParam;
        controller.startGame();
        controller.placeTCardsOnBoard();
    }
    
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Targui");  
        
        page = new SimpleStringProperty();    
        
        playerRegistration = new PlayerRegistration(controller);
        playerRegistration.setControlString(page);
        playerRegistration.newRegistration();
        
        controller.registerPlayer("Lukas", "green", 1);
        controller.registerPlayer("Marek", "blue", 2);
        controller.registerPlayer("Petr", "white", 3);
        controller.registerPlayer("Honza", "yellow", 4);
        
        
        
        page.addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                //set new page
                if (newValue.compareTo("registration") == 0)
                    gridPane = playerRegistration.getView();
                
                else if (newValue.compareTo("game") == 0){
                    gamePlay = new GamePlay(controller);
                    gamePlay.setControlString(page);
                    gamePlay.createView();
                    gridPane = gamePlay.getView();
                }
                
                //set scene to new page
                if (scene != null)
                    scene.setRoot(gridPane);
            }
        });
        
        page.setValue("game");
        
        
        scene = new Scene(gridPane, 1230, 565);
        scene.getStylesheets().add
            (this.getClass().getResource("css.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setMinHeight(565); 
        primaryStage.setMaxHeight(565); 
        primaryStage.setMinWidth(1230); 
        primaryStage.setMaxWidth(1230);
        primaryStage.show();
    } 
}
