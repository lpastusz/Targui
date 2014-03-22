package domain.MCards;
import domain.MCard;
import domain.Player;

/**
 *
 * @author Lukas.Pasta
 */
public class PlayerGiftMCard extends MCard{
    private Player player;
    public PlayerGiftMCard(Player playerParam) {
        player = playerParam;
    }
}
