/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ui;
import domain.DomainController;
import targui.Constants;
import domain.Cell;
import java.util.Scanner;

/**
 *
 * @author Lukas.Pasta
 */
public class UC2 {
    private DomainController controller;
    public UC2(DomainController controllerParam) {
        controller = controllerParam;
    }     
    
    public void placeTCardsOnBoard() {
        controller.placeTCardsOnBoard();
    }
    
    public void printBoardWithTCards() {
        for (int i = 0; i < Constants.BoardSize; i++) {
            for (int j = 0; j < Constants.BoardSize; j++) {
                Cell cell;
                cell = controller.getCell(i, j);

                switch (cell.getTCard()) {
                    case SETTLEMENT: System.out.print("S"); break;
                    case GUELTA: System.out.print("G");     break;
                    case ERG: System.out.print("E");        break;
                    case REG: System.out.print("R");        break;
                    case MOUNTAIN: System.out.print("M");   break;
                    case MINE: System.out.print("A");       break;
                    case FESH: System.out.print("F");       break;
                    case CHOTT: System.out.print("C");      break;            
                }
                System.out.print(" ");
            }
            System.out.print('\n');
        }
    }
    
    public void printBoardWithSectors() {
        for (int i = 0; i < Constants.BoardSize; i++) {
            for (int j = 0; j < Constants.BoardSize; j++) {
                Cell cell;
                cell = controller.getCell(i, j);
                
                if (cell.getSector() == null) 
                    System.out.print(" ");
                else {
                    for (int k = 0; k < Constants.PlayerCount; k++) {
                        if (cell.getSector() == controller.getPlayer(k).getSector())
                            System.out.print(k);
                    }
                }
            }
            System.out.print('\n');
        }        
    }
    
    public void placeSettlementCards() {
        int counter = 1;
        int x, y;
        boolean err;
        
        controller.placeSettlementCard(0, 0, 0);
        controller.placeSettlementCard(0, 6, 1);
        controller.placeSettlementCard(6, 0, 2);
        controller.placeSettlementCard(6, 6, 3);
        
        /*
        Scanner s = new Scanner(System.in);
        System.out.println("\n Choose coordinates of your sector where to change the card: ");
        do {
            System.out.print("Player" + counter + ": ");
            do {
                err = false;
                x = s.nextInt();
                y = s.nextInt();

                try {
                    controller.placeSettlementCard(x, y, counter-1);
                } catch(IllegalArgumentException e) {
                    if (e.getMessage().compareTo("wrong sector") == 0)
                        System.out.print("Wrong coordinates, type again: ");
                    err = true;
                }
            } while (err);   
            counter += 1;
        } while (counter <= Constants.PlayerCount);
                
        */
    }
}
