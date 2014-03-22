/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ui;
import domain.DomainController;
import java.util.Scanner;
import java.lang.Exception.*;

/**
 *
 * @author Lukas.Pasta
 */
public class UC1 {
    private DomainController controller;
    public UC1(DomainController controllerParam) {
        controller = controllerParam;
    }  
    
    void startGame() {
        controller.startGame();
    }
    
    void registerPlayers() {
        int counter = 1;
        Scanner s = new Scanner(System.in);
        System.out.println("Enter informations about players:"); 
        
        controller.registerPlayer("Lukas", "green", 1);
        controller.registerPlayer("Marek", "blue", 2);
        controller.registerPlayer("Petr", "white", 3);
        controller.registerPlayer("Honza", "yellow", 4);
        /*
        do {
            String name, color;
            int sector; 
            System.out.println("Player" + counter + ": ");
            System.out.print("Name: ");
            name = s.next();
            System.out.print("Color: ");
            color = s.next();
            System.out.print("Sector: ");
            sector = s.nextInt();
            
            boolean err;
            do {
                try {
                    err = false;
                    controller.registerPlayer(name, color, sector);
                } catch(IllegalArgumentException e) {
                    if (e.getMessage().compareTo("name") == 0)  {
                        System.out.print("Invalid name, type another: ");
                        name = s.next();                 
                    }
                    else if (e.getMessage().compareTo("color") == 0)  {
                        System.out.print("Invalid color, type another: ");
                        color = s.next();                 
                    }
                    else if (e.getMessage().compareTo("sector") == 0)  {
                        System.out.print("Invalid sector, type another: ");
                        sector = s.nextInt();                 
                    } 
                    err = true;
                }     
            } while (err);
            counter += 1;
        } while (counter <= Constants.PlayerCount);
                */
    }
}
