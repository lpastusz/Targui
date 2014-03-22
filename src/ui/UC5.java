package ui;
import domain.DomainController;
import java.util.Scanner;
/**
 *
 * @author Lukas.Pasta
 */
public class UC5 {
    private DomainController controller;
    public UC5(DomainController controllerParam) {
        controller = controllerParam;
    }
    
    public void playNextTurn(int player) {
        boolean canMove, canPurchase;
        String input;
        Scanner s = new Scanner(System.in);
        while ( ((canMove = controller.canPerformMove()) || (canPurchase = controller.canPerformPurchase())) &&
                ((canPurchase = controller.canPerformPurchase()) || (canMove = controller.canPerformMove()))) {
        
            System.out.print("Choose action (");
            if (canMove) System.out.print("move");
            if (canMove && canPurchase) System.out.print(" | ");
            if (canPurchase) System.out.print("buy");
            System.out.print(") : ");
            
            input = s.next();
            
            if (input.compareTo("move") == 0) {
                /*
                System.out.print("Source coordinates: ");
                int sx = s.nextInt(); int sy = s.nextInt();
                
                System.out.print("Destination coordinates: ");
                int dx = s.nextInt(); int dy = s.nextInt();
                */
                controller.performMove();
            }
            
            
            if (input.compareTo("buy") == 0) {
                /*
                System.out.print("How many camels: ");
                int num = s.nextInt();
                System.out.print("Where to put: ");
                int x = s.nextInt(); int y = s.nextInt();
                */
                controller.performPurchase();
            }
        }
    }
}
