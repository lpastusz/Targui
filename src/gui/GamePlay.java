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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import targui.Constants;

/**
 *
 * @author lukas_000
 */
public class GamePlay {
    private DomainController controller;
    ErrorDialog errorDialog = new ErrorDialog();
    Button nextCard;
    GridPane gridPane;
    SimpleStringProperty page;
    DiceView dice;
    Button move, buy, saveGame;
    ArrayList<PlayerInformation> playerInformations;
    ArrayList<CellView> cellList;
    Text infoMessage;
    int playerTurn;
    ResourceBundle labels;
    
    
    
    
    public GamePlay(DomainController controllerParam, ResourceBundle labelsParam) {
        controller = controllerParam; 
        labels = labelsParam;
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
        infoMessage = new Text(labels.getString("ThrowDiceAction"));
        
        gridPane.setId("rootGame");
        gridPane.setAlignment(Pos.TOP_CENTER);
        gridPane.setPadding(new Insets(20, 20, 20, 20));
        
        for (int i = 0; i < 20; i++)
            gridPane.getColumnConstraints().add(new ColumnConstraints(50)); 
        
        for (int i = 0; i < 12; i++)
            gridPane.getRowConstraints().add(new RowConstraints(40));
        
        GridPane players = new GridPane();
        players.setId("playersInfo");
        players.setHgap(20);
        for (int i = 0; i < Constants.PlayerCount; i++)
            playerInformations.add(new PlayerInformation(controller.getPlayer(i).getName(), controller.getPlayer(i).getColor(), controller.getPlayer(i).getSilverProperty()));
        
        for (int i = 0; i < Constants.PlayerCount; i++) {
            players.add(playerInformations.get(i).getView(), i, 0);
        }
        
        
        
        GridPane boardView = new GridPane();
        
        for (int i = 0; i < Constants.BoardSize; i++) {
            for (int j = 0; j < Constants.BoardSize; j++) {
                CellView cell = new CellView(controller.getCell(i, j), controller, i, j);
                cellList.add(cell);
                boardView.add(cell.getView(), j, i);
            }
        }

        saveGame = new Button(labels.getString("SaveGameButton"));
        saveGame.setPrefSize(120, 60);
        
        Button mainMenu = new Button(labels.getString("MainMenuButton"));
        mainMenu.setPrefSize(120, 60);
        
        saveGame.setOnAction(new EventHandler<ActionEvent>() {     
            public void handle(ActionEvent event) { 
                saveGameDialog();
            }
        });
        
        saveGame.setVisible(false);
    
        mainMenu.setOnAction(new EventHandler<ActionEvent>() {     
            public void handle(ActionEvent event) { 
                page.set("menu");
            }
        });
        
        if (controller.wasGameLoaded())
            playGame();
        else 
            changeSettlements();
        

        gridPane.add(infoMessage, 0, 0, 7, 1);
        infoMessage.setId("infoMessage");
        infoMessage.setFill(Color.RED);
        move = new Button(labels.getString("MoveButton"));
        buy = new Button(labels.getString("BuyCamelsButton"));
        VBox actionButtons = new VBox();
        actionButtons.getChildren().addAll(move, buy);
        actionButtons.setSpacing(5);
        move.setVisible(false);
        buy.setVisible(false);
        gridPane.add(actionButtons, 7, 6, 2, 2);
        
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        

        
        gridPane.add(saveGame, 0, 9, 2, 1);
        gridPane.add(mainMenu, 0, 10, 2, 1);
        gridPane.add(players, 0, 2, 3, 1);
        gridPane.add(boardView, 11, 1, 9, 9);
    }
    
    
    private void changeSettlements() {    
        infoMessage.setText(controller.getPlayer(playerTurn).getName() + " " + labels.getString("SettlementAction"));
        EventHandler<ActionEvent> e;
        for (final CellView c : cellList) {
           e = new EventHandler<ActionEvent>() {     
                public void handle(ActionEvent event) {  
                    boolean err = false;
                    try {
                        controller.placeSettlementCard(c.getRow(), c.getColumn(), playerTurn);
                    } catch(IllegalArgumentException e) {
                        if (e.getMessage().compareTo("wrong sector") == 0) {
                            err = true;
                            errorDialog.showDialog(labels.getString("SectorError"));
                        }
                    }
                    System.out.println(playerTurn);
                    if (!err) {
                        if (playerTurn < 3) {
                            playerTurn += 1;
                            infoMessage.setText(controller.getPlayer(playerTurn).getName() + " " + labels.getString("SettlementAction"));
                        }
                        else {
                            for (final CellView c : cellList) {
                                c.removeHandler();
                            }
                            registerRounds();
                        }
                            
                    }
                }
            };
            c.registerHandle(e);
        }
    }
    
    private void registerRounds() {
        final Stage dialogStage;
        VBox box = new VBox();
        box.setSpacing(20);
        box.setAlignment(Pos.CENTER);
        box.setId("dialog");
        Text t = new Text(labels.getString("SelectRoundsAction"));
        t.setId("dialogText");
       
        
        final TextField value = new TextField();
        value.setMaxWidth(30);
        
        HBox hbox = new HBox();
        hbox.setSpacing(10);
        hbox.getChildren().addAll(t, value);
        
        Button b = new Button(labels.getString("OkButton"));
        b.setId("dialogButton");
        box.getChildren().addAll(hbox, b);
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
                boolean err = false;
                try {
                    controller.registerRounds(Integer.parseInt(value.getText()));
                } catch(IllegalArgumentException exception) {
                errorDialog.showDialog(labels.getString("RoundNumberError"));
                err = true;
                }
                
                if (!err) {
                    dialogStage.hide();
                    playGame();
                }
            }
        });
    }
    
    
    private void playGame() {
        dice = new DiceView();
        gridPane.add(dice.getView(), 1, 6, 2, 2);
        
        throwTurns();        
    }
    
    
    private void throwTurns() {
         saveGame.setVisible(true);
        
        infoMessage.setText(labels.getString("ThrowDiceAction"));
            EventHandler<ActionEvent> e;
            e = new EventHandler<ActionEvent>() {     
                 public void handle(ActionEvent event) { 
                     saveGame.setVisible(false);
                     if (controller.isGameEnd()) {
                         infoMessage.setText(controller.getWiner().getName() + " " + labels.getString("WonAction"));
                         dice.removeHandler();
                         return;
                     }
                     int turns = controller.throwDice();
                     dice.setNumber(turns);
                     controller.createNewRound(turns);
                     dice.removeHandler();
                     
                     playNextRound();
                 }
            };

            dice.registerHandle(e);
    }
    
    private void playNextRound() {
        if (!controller.isFirstRound())
            controller.giveLevy();
        if (controller.isGameEnd()) {
            infoMessage.setText(controller.getWiner().getName() + " " + labels.getString("WonAction"));
            return;
        }
        
        infoMessage.setText(labels.getString("NextCardAction"));
        nextCard = new Button();
        nextCard.setId("nextCard");
        nextCard.setPrefSize(104, 130);
        gridPane.add(nextCard, 3, 5, 2, 3);
        
        Image oldCard = new Image("gui/img/cardFaceDown.jpg");
        final ImageView oldCardView = new ImageView(oldCard);
        oldCardView.setFitHeight(130); 
        oldCardView.setFitWidth(104);
        final Rectangle r = new Rectangle();
        r.setWidth(104); r.setHeight(130);

        
        EventHandler<ActionEvent> e;
            e = new EventHandler<ActionEvent>() {     
                 public void handle(ActionEvent event) {
                    if (controller.isNextRoundCardMCard()) {
                        move.setVisible(false);
                        buy.setVisible(false);
                        infoMessage.setText(labels.getString("MisfortuneCardAction"));
                          try {
                              gridPane.getChildren().remove(r);
                          } catch(Exception e) { }
                          controller.getNextRoundCard(); 
                          
                          gridPane.add(oldCardView, 5, 5, 2, 3);
                          if (controller.isRoundEnd()) {
                              nextCard.setVisible(false);
                              playNextRound();
                          }
                    }
                    else {
                        try {
                        gridPane.getChildren().remove(r);
                          } catch(Exception e) { }
                        playerTurn = controller.getNextRoundCard();
                        r.setFill(Color.web(controller.getPlayer(playerTurn).getColor()));
                        r.setVisible(true);
                        gridPane.add(r, 5, 5, 2, 3);
                        chooseAction();
                    }
                    
                    
                    if (controller.isRoundEnd())  {
                        nextCard.setVisible(false);

                        EventHandler<ActionEvent> e;
                        e = new EventHandler<ActionEvent>() {     
                            public void handle(ActionEvent event) { 
                                if (controller.isGameEnd()) {
                                       infoMessage.setText(controller.getWiner().getName() + " " + labels.getString("WonAction"));
                                       dice.removeHandler();
                                       return;
                                   }
                                saveGame.setVisible(false);
                                int turns = controller.throwDice();
                                dice.setNumber(turns);
                                controller.createNewRound(turns);
                                dice.removeHandler();

                                playNextRound();
                             }
                        };
                        dice.registerHandle(e);
                        saveGame.setVisible(true);
                    }
                            
                 }
            };
        
        nextCard.setOnAction(e);
    }
    
    private void chooseAction() {
        for (CellView c : cellList) {
            c.removeHandler();
        }
        
        
        infoMessage.setText(labels.getString("ChooseAction"));
        boolean canMove, canPurchase;
        
        canMove = controller.canPerformMove();
        canPurchase = controller.canPerformPurchase();
        
        if(controller.isRoundEnd()) {
            nextCard.setVisible(false);
            nextCard.setOnAction(new EventHandler<ActionEvent>() {     
                public void handle(ActionEvent event) {  
                }
            });            
        }
            
        
        if (!(canMove || canPurchase)) {
            if (controller.isRoundEnd()) {
                infoMessage.setText(labels.getString("ThrowDiceAction"));
                throwTurns();
            }
            else {
                infoMessage.setText(labels.getString("NextCardAction"));
                playNextRound();
            }
            if (controller.isGameEnd()) {
                infoMessage.setText(controller.getWiner().getName() + " " + labels.getString("WonAction"));
                return;
            }
        }
        
        move.setVisible(canMove);
        buy.setVisible(canPurchase);
        
        EventHandler<ActionEvent> moveHandle = new EventHandler<ActionEvent>() {     
                 public void handle(ActionEvent event) {
                     move.setVisible(false);
                     buy.setVisible(false);
                    infoMessage.setText(labels.getString("SourceCellAction"));
                    EventHandler<ActionEvent> e;
                    for (final CellView c : cellList) {
                       e = new EventHandler<ActionEvent>() {     
                            public void handle(ActionEvent event) {
                                if (controller.isOwner(playerTurn, c.getColumn(), c.getRow())) {
                                    if (controller.isWithCamels(c.getColumn(), c.getRow())) {
                                        for (final CellView c2 : cellList) {
                                            c2.removeHandler();
                                            infoMessage.setText(labels.getString("DestinationCellAction"));
                                            EventHandler<ActionEvent> e2;
                                            e2 = new EventHandler<ActionEvent>() {
                                                public void handle(ActionEvent event2) {
                                                    boolean err = false;
                                                    if (((c.getColumn() == c2.getColumn()) && (c.getRow() == c2.getRow())) || ((Math.abs(c2.getColumn()-c.getColumn()) >  1) || (Math.abs(c2.getRow()-c.getRow()) > 1))) 
                                                        err = true;
                                                    if (err)
                                                        errorDialog.showDialog(labels.getString("MoveHereError"));
                                                    else {
                                                        err = false;
                                                        if (controller.isAttack(playerTurn, c2.getColumn(), c2.getRow())) {
                                                            infoMessage.setText(controller.getPlayer(playerTurn).getName() + " " + labels.getString("AttackAction"));
                                                            int opponent = controller.getOpponent(c2.getColumn(), c2.getRow());
                                                            throwDiceAttack(c.getColumn(), c.getRow(), c2.getColumn(), c2.getRow(), opponent);
                                                        }
                                                        else {
                                                            chooseAmountMove(c.getColumn(), c.getRow(), c2.getColumn(), c2.getRow(), controller.getCell(c.getRow(), c.getColumn()).getCamels());
                                                        }
                                                    }
                                                }
                                            };
                                            c2.registerHandle(e2);
                                        }
                                    }
                                    else 
                                        errorDialog.showDialog(labels.getString("EmptyCellError"));
                                }
                                else
                                    errorDialog.showDialog(labels.getString("ForeignCellError"));
                            }
                       };
                       c.registerHandle(e);
                    }
                 }
        };
        
        EventHandler<ActionEvent> buyHandle = new EventHandler<ActionEvent>() {     
                 public void handle(ActionEvent event) {
                     move.setVisible(false);
                     buy.setVisible(false);
                     chooseAmountBuy();
                 }
        };
        
        move.setOnAction(moveHandle);
        buy.setOnAction(buyHandle);
    }
    
    
    private void chooseAmountBuy() {
        final Stage dialogStage;
        VBox box = new VBox();
        box.setSpacing(20);
        box.setAlignment(Pos.CENTER);
        box.setId("dialog");
        Text t = new Text(labels.getString("SelectCamelsAction"));
        t.setId("dialogText");
       
        
        final TextField value = new TextField();
        value.setMaxWidth(30);
        
        HBox hbox = new HBox();
        hbox.setSpacing(10);
        hbox.getChildren().addAll(t, value);
        
        Button b = new Button(labels.getString("OkButton"));
        b.setId("dialogButton");
        box.getChildren().addAll(hbox, b);
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
                public void handle(ActionEvent event) {
                    boolean err = false;
                     try {
                        controller.buyCamels(playerTurn, Integer.parseInt(value.getText()));
                     } catch(IllegalArgumentException e) {
                        err = true;
                        errorDialog.showDialog(labels.getString("TooManyCamelsError"));
                     }
                     
                     if (!err) {
                        dialogStage.hide();
                        infoMessage.setText(labels.getString("DestinationCellAction"));
                        EventHandler<ActionEvent> e;
                        for (final CellView c : cellList) {
                          e = new EventHandler<ActionEvent>() {     
                               public void handle(ActionEvent event) {  
                                    if (controller.isOwner(playerTurn, c.getColumn(), c.getRow())) {
                                        boolean err = false;
                                        try {
                                            controller.placeCamels(playerTurn, c.getColumn(), c.getRow(), Integer.parseInt(value.getText()));
                                        } catch(IllegalArgumentException e) {
                                            err = true;
                                        }
                                        if (!err)
                                            chooseAction();
                                        else
                                            errorDialog.showDialog(labels.getString("BuyCamelOnChottError"));
                                    } 
                                    else
                                    {
                                        errorDialog.showDialog(labels.getString("ForeignCellError"));
                                    }
                               }
                          };
                          c.registerHandle(e);
                        }
                     }
                }
            });
    }
    
    private void chooseAmountMove(final int sx, final int sy, final int dx, final int dy, final int maxCamels) {
        final Stage dialogStage;
        VBox box = new VBox();
        box.setSpacing(20);
        box.setAlignment(Pos.CENTER);
        box.setId("dialog");
        Text t = new Text(labels.getString("SelectCamelsAction"));
        t.setId("dialogText");
       
        
        final TextField value = new TextField();
        value.setMaxWidth(30);
        
        HBox hbox = new HBox();
        hbox.setSpacing(10);
        hbox.getChildren().addAll(t, value);
        
        Button b = new Button(labels.getString("OkButton"));
        b.setId("dialogButton");
        box.getChildren().addAll(hbox, b);
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
                public void handle(ActionEvent event) {
                    if (Integer.parseInt(value.getText()) > maxCamels)
                        errorDialog.showDialog(labels.getString("NotEnoughCamelsError"));
                    else {
                        controller.performMove(playerTurn, dx, dy, sx, sy, Integer.parseInt(value.getText()));
                        chooseAction();
                        dialogStage.hide();
                    }
                }
            });
    }    
    
    private void throwDiceAttack(final int sx, final int sy, final int dx, final int dy, final int opponent) {
        infoMessage.setText(controller.getPlayer(playerTurn).getName() + " " + labels.getString("AttackAction"));
        EventHandler<ActionEvent> e;
        e = new EventHandler<ActionEvent>() {     
             public void handle(ActionEvent event) { 
                 int num = controller.throwDice();
                 controller.attack(sx, sy, dx, dy, num);
                 nextCard.setVisible(false);
                 dice.setNumber(num);
                 dice.removeHandler();
                 if (!controller.isWithCamels(dx, dy)) {
                     nextCard.setVisible(true);
                     chooseAmountMove(sx, sy, dx, dy, controller.getCell(sy, sx).getCamels());
                 }
                 else {
                     throwDiceDefend(dx, dy, sx, sy, opponent);
                 }
                 
             }
        };
        dice.registerHandle(e);
    }
    
    
    private void throwDiceDefend(final int sx, final int sy, final int dx, final int dy, final int opponent) {
        infoMessage.setText(controller.getPlayer(opponent).getName() + " " + labels.getString("DefendAction"));
        EventHandler<ActionEvent> e;
        e = new EventHandler<ActionEvent>() {     
             public void handle(ActionEvent event) { 
                 int num = controller.throwDice();
                 controller.attack(sx, sy, dx, dy, num);
                 dice.setNumber(num);
                 nextCard.setVisible(true);
                 dice.removeHandler();
                 if (!controller.isWithCamels(dx, dy)) {
                     playNextRound();
                 }
                 else {
                     throwDiceAttack(dx, dy, sx, sy, opponent);
                 }
                 
             }
        };
        dice.registerHandle(e);        
    }
    
    
     private void saveGameDialog() {
        final Stage dialogStage;
        HBox box = new HBox();
        box.setSpacing(20);
        box.setAlignment(Pos.CENTER);
        box.setId("dialog");
        Text t = new Text(labels.getString("SaveGameAction"));
        t.setId("dialogText");
       
        
        final TextField value = new TextField();
        value.setPrefWidth(180);
        
        
        Button b = new Button(labels.getString("OkButton"));
        b.setId("dialogButton");
        box.getChildren().addAll(t, value, b);
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
                public void handle(ActionEvent event) {
                    if (value.textProperty().isEmpty().get() || value.textProperty().get().isEmpty() || value.textProperty().get().compareTo("") == 0)
                        errorDialog.showDialog(labels.getString("EmptyGameNameError"));
                    else {
                        controller.saveGame(value.textProperty().get());
                        errorDialog.showDialog(labels.getString("SavedConfirmation"));
                    }
                }
            });
    }    
}
