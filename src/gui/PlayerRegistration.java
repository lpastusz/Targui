/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import domain.DomainController;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author lukas_000
 */
public class PlayerRegistration {
    ErrorDialog errorDialog = new ErrorDialog();
    private ArrayList<PlayerRowView> rows = new ArrayList<PlayerRowView>();
    private DomainController controller;
    GridPane gridPane;
    SimpleStringProperty page;
    ResourceBundle labels;
    
    
    public PlayerRegistration(DomainController controllerParam, ResourceBundle labelsParam) {
        controller = controllerParam;  
        labels = labelsParam;
    }
    
    public void setControlString(SimpleStringProperty pageParam) {
        page = pageParam;
    }
    
    public GridPane getView() {
        return gridPane;
    }
    
    public void newRegistration() {
        gridPane = new GridPane();
        
        gridPane.setId("rootRegistration");
        gridPane.setAlignment(Pos.BOTTOM_RIGHT);
        gridPane.setPadding(new Insets(0, 50, 20, 0));
        
        for (int i = 0; i < 4; i++) {
            PlayerRowView p = new PlayerRowView();
            rows.add(p);
            p.getView().setId("line");
            gridPane.add(p.getView(), 0, i);
        }
        
        GridPane buttonGrid = new GridPane();
        buttonGrid.setId("startButton");
        buttonGrid.setAlignment(Pos.CENTER);
        
        Button begin = new Button(labels.getString("StartGameButton"));
        begin.setPrefSize(120, 60);
        begin.setAlignment(Pos.CENTER);
        
        Button back = new Button(labels.getString("BackButton"));
        back.setPrefSize(120, 60);
        back.setAlignment(Pos.CENTER);
        
        buttonGrid.add(begin, 0, 0);
        buttonGrid.add(back, 0, 1);
        buttonGrid.setVgap(10);
        gridPane.add(buttonGrid, 1, 0, 1, 4);
        
        back.setOnAction(new EventHandler<ActionEvent>() {     
            public void handle(ActionEvent event) { 
                controller.resetGame();
                page.set("menu");
            }
        });
        
        begin.setOnAction(new EventHandler<ActionEvent>() {     
            public void handle(ActionEvent event) {  
                for (PlayerRowView p : rows) {
                    if (p.isEnabled()) {
                        if (p.getName().isEmpty()) {
                            errorDialog.showDialog(labels.getString("EmptyNameError"));
                            break;
                        }
                        
                        System.out.println("S: " + p.getSector());
                        if (p.getSector() < 1 || p.getSector() > 4) {
                            errorDialog.showDialog(labels.getString("EmptySectorError"));
                            break;
                        }
                        
                        boolean err = false;
                        try {
                            controller.registerPlayer(p.getName(), p.getColor(), p.getSector());
                        } catch(IllegalArgumentException e) {
                            if (e.getMessage().compareTo("name") == 0)  {
                                errorDialog.showDialog(labels.getString("NameUsedError"));
                                err = true;
                            }
                            if (e.getMessage().compareTo("color") == 0)  {
                                errorDialog.showDialog(labels.getString("ColorUsedError"));
                                err = true;
                            }
                            if (e.getMessage().compareTo("sector") == 0)  {
                                errorDialog.showDialog(labels.getString("SectorUsedError"));
                                err = true;
                            }
                        }
                        
                        if (!err) {
                            p.disable();
                            
                            int count = 0;
                            
                            for (PlayerRowView pp : rows)
                                if (pp.isEnabled() == false)
                                    count += 1;
                            if (count == 4) {
                                controller.setGameLoaded(false);
                                page.setValue("game");
                            }
                        }
                        else break;
                            
                    }
            }
         } }); 
    }
}
