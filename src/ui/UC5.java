package ui;
import domain.DomainController;
import java.io.IOException;
import java.util.Scanner;
import java.lang.Math.*;
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
                boolean err;
                int sx, sy;
                int dx, dy;
                do {
                    err = false;
                    System.out.print("Source coordinates: ");
                    sx = s.nextInt(); sy = s.nextInt();
                    if(controller.isOwner(player, sx, sy))
                        err = true;
                } while (err);

                do {
                    err = false;
                    System.out.print("Destination coordinates: ");
                    dx = s.nextInt(); dy = s.nextInt();
                    if (((dx == sx) && (dy == sy)) || ((Math.abs(dx-sx) >  1) || (Math.abs(dy-sy) > 1))) 
                        err = true;
                } while (err);

                
                if(controller.isAttack(player, dx, dy)) {
                    //Attack 
                    int opponent = controller.getOpponent(dx, dy);
                    
                    boolean attackEnd = false, attackerWin = false;
                    do {
                        System.out.print("PL1: Enter to throw a dice");
                        try {
                            System.in.read();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        
                        
                        int pl1Dice = controller.throwDice();
                        controller.attack(sx, sy, dx, dy, pl1Dice);
                        if (!controller.isWithCamels(dx, dy)) {
                            attackerWin = attackEnd = true;
                            break;
                        }

                        System.out.print("PL2: Enter to throw a dice");
                        try {
                            System.in.read();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        int pl2Dice = controller.throwDice(); 
                        controller.attack(dx, dy, sx, sy, pl2Dice);
                        if (!controller.isWithCamels(sx, sy)) {
                            attackEnd = true;
                            break;
                        }

                        System.out.println("Do you want to continue the attack? (yes/no)");
                        String str = s.next();
                        if (str.compareTo("yes") == 0)
                            attackEnd = true;
                    } while (!attackEnd);
                    
                    
                    if (attackerWin) {
                        System.out.print("How many camels do you want to move: ");
                        int camels = s.nextInt();
                        controller.performMove(player, sx, sy, dx, dy, camels);                        
                    }
                }
                
                else {
                    // move
                    System.out.print("How many camels: ");
                    int camels = s.nextInt();
                    controller.performMove(player, sx, sy, dx, dy, camels);
                }
            }
            
            
            if (input.compareTo("buy") == 0) {
                boolean err;
                int amount;
                do {
                    err = false;
                    System.out.print("How many camels: ");
                    amount = s.nextInt();
                    try {
                        controller.buyCamels(player, amount);
                    } catch(IllegalArgumentException e) {
                        err = true;
                    }
                } while (err);
                
                do {
                    err = false;
                    System.out.print("Where to put: ");
                    int x = s.nextInt(); int y = s.nextInt();
                    try {
                        controller.placeCamels(player, x, y, amount);
                     } catch(IllegalArgumentException e) {
                        err = true;
                    }
                } while (err);
            }
        }
    }
}
