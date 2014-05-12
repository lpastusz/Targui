/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import domain.DomainController;
import java.util.Locale;
import java.util.ResourceBundle;
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
    Locale locale;
    ResourceBundle labels;
    
    //pages
    PlayerRegistration playerRegistration;
    GamePlay gamePlay;
    MainMenu mainMenu;
    LoadGame loadGame;
    ChooseLanguage language;
    
    public GuiApplication(DomainController controllerParam) {
        controller = controllerParam;
    }
    
    public void start(final Stage primaryStage) {
        primaryStage.setTitle("Targui");  
        
        page = new SimpleStringProperty();    
        
        
        
        
        
        page.addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                //set new page
                if (newValue.compareTo("registration") == 0) {
                    playerRegistration = new PlayerRegistration(controller, labels);
                    controller.startGame();
                    controller.placeTCardsOnBoard();
                    playerRegistration.setControlString(page);
                    playerRegistration.newRegistration();
                    gridPane = playerRegistration.getView();
                }
                else if (newValue.compareTo("game") == 0){
                    gamePlay = new GamePlay(controller, labels);
                    gamePlay.setControlString(page);
                    gamePlay.createView();
                    gridPane = gamePlay.getView();
                }
                else if (newValue.compareTo("menu") == 0) {
                    if (labels == null) {
                        locale = language.getLocale();
                         labels = ResourceBundle.getBundle("resources.resources", locale);
                    }
                    mainMenu = new MainMenu(controller, primaryStage, labels);
                    mainMenu.setControlString(page);
                    gridPane = mainMenu.getView();
                }
                else if (newValue.compareTo("load") == 0) {
                    loadGame = new LoadGame(controller, labels);
                    loadGame.setControlString(page);
                    gridPane = loadGame.getView();
                }
                else if (newValue.compareTo("language") == 0) {
                    language = new ChooseLanguage();
                    language.setControlString(page);
                    gridPane = language.getView();
                }
                
                //set scene to new page
                if (scene != null)
                    scene.setRoot(gridPane);
            }
        });
        
        page.setValue("language");
        
        scene = new Scene(gridPane, 1150, 565);
        scene.getStylesheets().add
            (this.getClass().getResource("css.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setMinHeight(565); 
        primaryStage.setMaxHeight(565); 
        primaryStage.setMinWidth(1150); 
        primaryStage.setMaxWidth(1150);
        primaryStage.show();
    } 
}
