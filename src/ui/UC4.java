package ui;

import domain.DomainController;
import java.io.IOException;

/**
 *
 * @author Lukas.Pasta
 */

public class UC4 {
    private DomainController controller;
    public UC4(DomainController controllerParam) {
        controller = controllerParam;        
    }
    
    public void playNextRound() {
        UC5 turn = new UC5(controller);
        System.out.println("Number of turns: " + controller.throwDice()+ " each player");
        
        int turns = controller.throwDice();
        controller.createNewRound(turns);
        while (!controller.isRoundEnd()) {
            System.out.print("ENTER to play next turn");
            try {
                System.in.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
            
            
            if (controller.isNextRoundCardMCard())
                System.out.println("Misfortune card played");
            else {
                int player = controller.getNextRoundCard();
                turn.playNextTurn(player);
            }
        }
    }
}
