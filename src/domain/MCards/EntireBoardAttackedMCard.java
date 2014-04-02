package domain.MCards;
import domain.Board;
import domain.MCard;
import domain.PlayerRepository;
import targui.Constants;

/**
 *
 * @author Lukas.Pasta
 */
public class EntireBoardAttackedMCard extends MCard{
    public EntireBoardAttackedMCard(Board boardParam, PlayerRepository plRepositoryParam) {
        super(boardParam, plRepositoryParam);
    }    
    
    public void proceed() {
         for (int i = 0; i < Constants.BoardSize; i++)
            for (int j = 0; j < Constants.BoardSize; j++)
                board.getCell(j, i).removeCamels(1);
    }
}
