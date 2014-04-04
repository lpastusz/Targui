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
    
    //pages
    PlayerRegistration playerRegistration;
    GamePlay gamePlay;
    
    public GuiApplication(DomainController controllerParam) {
        controller = controllerParam;
        controller.startGame();
    }
    
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Targui");  
        
        SimpleStringProperty page = new SimpleStringProperty();    
        
        playerRegistration = new PlayerRegistration(controller);
        playerRegistration.setControlString(page);
        
        
        
        
        page.addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                //set new page
                if (newValue.compareTo("registration") == 0)
                    gridPane = playerRegistration.showRegistration();
                
                else {
                    gridPane = new GridPane();
                }
                
                //set scene to new page
                if (scene != null)
                    scene.setRoot(gridPane);
            }
        });
        
        page.setValue("registration");
        
        
        scene = new Scene(gridPane, 867, 397);
        scene.getStylesheets().add
            (this.getClass().getResource("css.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setMinHeight(397); 
        primaryStage.setMaxHeight(397); 
        primaryStage.setMinWidth(867); 
        primaryStage.setMaxWidth(867);
        primaryStage.show();
    } 
}
