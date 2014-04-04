/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import domain.DomainController;
import java.util.ArrayList;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import targui.Constants;

/**
 *
 * @author lukas_000
 */
public class GamePlay {
    private DomainController controller;
    GridPane gridPane;
    SimpleStringProperty page;
    ArrayList<PlayerInformation> playerInformations;
    ArrayList<CellView> cellList;
    
    
    
    
    public GamePlay(DomainController controllerParam) {
        controller = controllerParam; 
        playerInformations = new ArrayList<PlayerInformation>();
        cellList = new ArrayList<CellView>();
    }
    
    public void setControlString(SimpleStringProperty pageParam) {
        page = pageParam;
    }
    
    public GridPane getView() {
        return gridPane;
    }
    
    public void createView() {
        gridPane = new GridPane();
        
        gridPane.setId("rootGame");
        gridPane.setAlignment(Pos.TOP_RIGHT);
        gridPane.setPadding(new Insets(20, 20, 20, 20));
        
        GridPane players = new GridPane();
        players.setId("playersInfo");
        players.setHgap(10);
        for (int i = 0; i < Constants.PlayerCount; i++)
            playerInformations.add(new PlayerInformation(controller.getPlayer(i).getName(), controller.getPlayer(i).getColor(), controller.getPlayer(i).getSilverProperty()));
        
        for (int i = 0; i < Constants.PlayerCount; i++) {
            players.add(playerInformations.get(i).getView(), i, 0);
        }
        
        
        
        GridPane boardView = new GridPane();
        
        for (int i = 0; i < Constants.BoardSize; i++) {
            for (int j = 0; j < Constants.BoardSize; j++) {
                CellView cell = new CellView(controller.getCell(i, j), controller);
                cellList.add(cell);
                boardView.add(cell.getView(), j, i);
            }
        }
        
        
        gridPane.add(players, 0, 0, 1, 1);
        gridPane.add(boardView, 1, 0, 5, 5);
    }
    
}
