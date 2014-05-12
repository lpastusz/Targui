package domain.MCards;
import domain.MCard;
import domain.Player;
import domain.PlayerRepository;
import domain.Board;

/**
 *
 * @author Lukas.Pasta
 */
public class PlayerGiftMCard extends MCard{
    private Player player;
    public PlayerGiftMCard(Player playerParam, Board boardParam, PlayerRepository plRepositoryParam) {
        super(boardParam, plRepositoryParam);
        player = playerParam;
    }
    
    public void proceed() {
        board.getSettlementCell(player).addCamels(10);
    }
    
    public Player getPlayer() {
        return player;
    }
}
