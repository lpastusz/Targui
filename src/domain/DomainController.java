package domain;
import targui.Constants;
/**
 *
 * @author Lukas.Pasta
 */
public class DomainController {
    Game game;
    PlayerRepository plRepository;
    
    public void startGame() {
        game = new Game();
        plRepository = new PlayerRepository();
        plRepository.setGame(game);
        game.setPlayerRepository(plRepository);
    }
    
    public void registerPlayer(String name, String color, int sector) {
        try {
            plRepository.registerPlayer(name, color, sector);
        } catch(IllegalArgumentException e) {
            throw e;
        }
    }
    
    public void placeTCardsOnBoard() {
        game.placeTCardsOnBoard();
    }
    
    public Cell getCell(int row, int column) {
        return game.getCell(row, column);
    }
    
    public Player getPlayer(int i) {
        return plRepository.getPlayer(i);
    }
    
    public void placeSettlementCard(int row, int column, int playerNumber) {
        try {
            game.placeSettlementCard(row, column, playerNumber);
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }
    
    public void registerRounds(int roundsNumber) {
        try {
            game.registerRounds(roundsNumber);
        } catch(IllegalArgumentException e) {
            throw e;
        }
    }
    
    public boolean isGameEnd() {
        return game.isGameEnd();
    }
    
    public boolean isRoundEnd() {
        return game.isRoundEnd();
    }
    
    public int throwTurnNumber() {
        return game.throwTurnNumber();
    }
    
    public int getNextRoundCard() {
       return game.getNextRoundCard();
    }
    
    public void createNewRound(int turns) {
        game.createNewRound(turns);
    }
    
    public void performMove() {
        game.performMove();
    }
    
   public void performPurchase() {
        game.performPurchase();
    }
    
    public boolean canPerformMove() {
        return game.canPerformMove();
    }
    
    public boolean canPerformPurchase() {
        return game.canPerformPurchase();
    }
}
