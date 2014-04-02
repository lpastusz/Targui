package domain.MCards;
import domain.PlayerRepository;
import domain.MCard;
import domain.Board;
import domain.PlayerRepository;
import targui.Constants;

/**
 *
 * @author Lukas.Pasta
 */
public class CaravanAttackedMCard extends MCard{
    public CaravanAttackedMCard(Board boardParam, PlayerRepository plRepositoryParam) {
        super(boardParam, plRepositoryParam);
    }
    
    public void proceed() {
        for (int i = 0; i < Constants.PlayerCount; i++)
            plRepository.getPlayer(i).addSilver(10);
    }
}
