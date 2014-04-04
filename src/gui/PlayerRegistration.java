/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import domain.DomainController;
import java.util.ArrayList;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author lukas_000
 */
public class PlayerRegistration {
    private ArrayList<PlayerRowView> rows = new ArrayList<PlayerRowView>();
    private DomainController controller;
    GridPane gridPane;
    Stage dialogStage;
    SimpleStringProperty page;
    
    
    public PlayerRegistration(DomainController controllerParam) {
        controller = controllerParam;    
    }
    
    public void setControlString(SimpleStringProperty pageParam) {
        page = pageParam;
    }
    
    public GridPane showRegistration() {
        gridPane = new GridPane();
        
        gridPane.setId("root");
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
        
        Button begin = new Button("Start game");
        begin.setPrefSize(150, 50);
        begin.setAlignment(Pos.CENTER);
        
        buttonGrid.add(begin, 0, 0, 1, 4);
        gridPane.add(buttonGrid, 1, 0, 1, 4);
        
        begin.setOnAction(new EventHandler<ActionEvent>() {     
            public void handle(ActionEvent event) {  
                for (PlayerRowView p : rows) {
                    if (p.isEnabled()) {
                        if (p.getName().isEmpty()) {
                            showDialog("Name can not be empty");
                            break;
                        }
                        
                        System.out.println("S: " + p.getSector());
                        if (p.getSector() < 1 || p.getSector() > 4) {
                            showDialog("You have to select a sector");
                            break;
                        }
                        
                        boolean err = false;
                        try {
                            controller.registerPlayer(p.getName(), p.getColor(), p.getSector());
                        } catch(IllegalArgumentException e) {
                            if (e.getMessage().compareTo("name") == 0)  {
                                showDialog("Wrong name");
                                err = true;
                            }
                            if (e.getMessage().compareTo("color") == 0)  {
                                showDialog("Wrong color");
                                err = true;
                            }
                            if (e.getMessage().compareTo("sector") == 0)  {
                                showDialog("Wrong sector");
                                err = true;
                            }
                        }
                        
                        if (!err) {
                            p.disable();
                            
                            int count = 0;
                            
                            for (PlayerRowView pp : rows)
                                if (pp.isEnabled() == false)
                                    count += 1;
                            if (count == 4)
                                page.setValue("empty");
                        }
                        else break;
                            
                    }
            }
         } }); 
        
        return gridPane;
    }
    
    private void showDialog(String text) {
        VBox box = new VBox();
        box.setSpacing(20);
        box.setAlignment(Pos.CENTER);
        box.setId("dialog");
        Text t = new Text(text);
        t.setId("dialogText");
        Button b = new Button("Ok.");
        b.setId("dialogButton");
        box.getChildren().addAll(t, b);
        dialogStage = new Stage();
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.setScene(new Scene(VBoxBuilder.create().
            children(box).
            alignment(Pos.CENTER).padding(new Insets(5)).build()));
        dialogStage.getScene().getStylesheets().add
            (this.getClass().getResource("css.css").toExternalForm());
        dialogStage.show();
        
        b.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                dialogStage.hide();
            }
        });
    }
}
