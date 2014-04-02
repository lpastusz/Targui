package domain.MCards;

import domain.Board;
import domain.MCard;
import domain.PlayerRepository;

/**
 *
 * @author Lukas.Pasta
 */
public class MineAttackedMCard extends MCard{
    public MineAttackedMCard(Board boardParam, PlayerRepository plRepositoryParam) {
        super(boardParam, plRepositoryParam);
    }    
    
    public void proceed() {
        board.getCell(3, 3).removeCamels();
    }
}
