package ui;

import domain.DomainController;
import java.util.Scanner;
import java.io.IOException;

/**
 *
 * @author Lukas.Pasta
 */
public class UC3 {
   private DomainController controller;
    public UC3(DomainController controllerParam) {
        controller = controllerParam;
    }
    
    public void chooseNumberOfRounds() {
        int rounds;
        Scanner s = new Scanner(System.in);
        System.out.print("Choose number of rounds (1-16) : ");
        rounds = s.nextInt();
        
        boolean err;
        do {
            err = false;
            try {
            controller.registerRounds(rounds);
            } catch(IllegalArgumentException e) {
                System.out.print("Wrong number of rounds, enter again: ");
                rounds = s.nextInt();
                        
                err = true;
            }
        } while (err);
    }
    
    public void playGame() {
        UC4 round = new UC4(controller);
        while (!controller.isGameEnd()) {
            System.out.print("ENTER to play next round");
            try {
                System.in.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
            round.playNextRound();
        }
    }
}
