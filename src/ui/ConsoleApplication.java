package ui;
import domain.DomainController;
import java.util.Scanner;

/**
 *
 * @author Lukas.Pasta
 */
public class ConsoleApplication {
    private DomainController controller;
    
    public ConsoleApplication(DomainController controllerParam) {
        controller = controllerParam;
    }
    
    public void Start() {
        Scanner s = new Scanner(System.in);
        int choice;

        do {
            do
            {
                printStartMenu();
                choice = s.nextInt();
            } while (choice < 0 || choice > 3);

            switch (choice)
            {
                case 1:
                    doUC1();
                    break;
                case 2:
                    doUC2();
                    break;
                case 3:
                    doUC3();
                    break;
            }
        } while (choice != 0);
    }
    
    private void printStartMenu() {
        System.out.println("Choose one of the options: ");
        System.out.println("0. End of game ");
        System.out.println("1. UC1 - Create new game ");
        System.out.println("2. UC2 - Initialize game ");
        System.out.println("3. UC3 - Play game");
    }
    
    private void doUC1() {
        UC1 UseCase = new UC1(controller);
        UseCase.startGame();
        UseCase.registerPlayers();
    }
    
    private void doUC2() {
        UC2 UseCase = new UC2(controller);
        UseCase.placeTCardsOnBoard();
        UseCase.printBoardWithSectors();
        UseCase.printBoardWithTCards();
        UseCase.placeSettlementCards();
        UseCase.printBoardWithTCards();
    }
    
    private void doUC3() {
        UC3 UseCase = new UC3(controller);
        UseCase.chooseNumberOfRounds();
        UseCase.playGame();
    }
}
