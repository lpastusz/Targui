package domain;
import java.util.Map;
import persistence.PersistenceController;
/**
 *
 * @author Lukas.Pasta
 */
public class DomainController {
    PersistenceController persistenceController;
    Game game;
    PlayerRepository plRepository;
    boolean loadedGame = false;
    
    public DomainController() {
        persistenceController = PersistenceController.getInstance();
        plRepository = new PlayerRepository();
    }
    
    public void startGame() {
        game = new Game();
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
            game.placeSettlementCard(row, column, plRepository.getPlayer(playerNumber));
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
        return (game.isRoundEnd() || game.isGameEnd());
    }
    
    public int getNextRoundCard() {
       return game.getNextRoundCard();
    }
    
    public void createNewRound(int turns) {
        game.createNewRound(turns);
    }
    
    public void performMove(int player, int sx, int sy, int dx, int dy, int camels) {
        game.performMove(plRepository.getPlayer(player), sx, sy, dx, dy, camels);
    }
    
    
    public boolean canPerformMove() {
        return game.canPerformMove();
    }
    
    public boolean canPerformPurchase() {
        return game.canPerformPurchase();
    }
    
    public boolean isAttack(int player, int dx, int dy) {
        return game.isAttack(plRepository.getPlayer(player), dx, dy);
    }
    
    public boolean isOwner(int player, int x, int y) {
        return game.isOwner(plRepository.getPlayer(player), x, y);
    }
    
    public void buyCamels(int player, int amount) {
        try {
            game.buyCamels(plRepository.getPlayer(player), amount);
        } catch(IllegalArgumentException e) {
            throw e;
        }
    }
    
    public void placeCamels(int player, int x, int y, int amount) {
        try {
        game.placeCamels(plRepository.getPlayer(player), x, y, amount);
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }
    
    public int getOpponent(int x, int y) {
        return game.getPlayer(x, y).getNumber();
    }
    
    public int throwDice() {
        return game.throwDice();
    }
    
    public void attack(int sx, int sy, int dx, int dy, int num) {
        game.attack(sx, sy, dx, dy, num);
    }
    
    public boolean isWithCamels(int dx, int dy) {
        return game.isWithCamels(dx, dy);
    }
    
    public void giveLevy() {
        game.giveLevy();
    }
    
    public boolean isNextRoundCardMCard() {
        return game.isNextRoundCardMCard();
    }
    
    public Player getWiner() {
        return game.getWiner();
    }
    
    public Map<String, Integer> getGames() 
    {
            return persistenceController.getGames();
    }
    
    public void saveGame(String gameName) {
        persistenceController.addGame(game, plRepository, gameName);
    }
    
    public void loadGame(int gameId) {
        Game gameLoaded = persistenceController.loadGame(gameId);
        game = gameLoaded;
        plRepository = game.getPlayerRepository();     
    }
    
    public void setGameLoaded(boolean loadedParam) {
        loadedGame = loadedParam;
    }
    
    public boolean wasGameLoaded() {
        return loadedGame;
    }
    
    public boolean isGameInProgress() {
        return (!(game == null));
    }
    
    public void resetGame() {
        game = null;
        plRepository = new PlayerRepository();
    }
    
    public boolean isFirstRound() {
        return game.isFirstRound();
    }
         
}
