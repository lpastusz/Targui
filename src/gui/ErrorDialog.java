/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package gui;



import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author lukas_000
 */
public class ErrorDialog {
    Stage dialogStage;
        public void showDialog(String text) {
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
