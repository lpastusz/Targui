package domain.MCards;
import domain.Board;
import domain.Cell;
import domain.MCard;
import domain.PlayerRepository;
import domain.TCard;
import targui.Constants;

/**
 *
 * @author Lukas.Pasta
 */
public class SaharaDroughtMCard extends MCard{
    public SaharaDroughtMCard(Board boardParam, PlayerRepository plRepositoryParam) {
        super(boardParam, plRepositoryParam);
    }   
    
    public void proceed() {
         for (int i = 0; i < Constants.BoardSize; i++) {
            for (int j = 0; j < Constants.BoardSize; j++) {
                Cell cell = board.getCell(j, i);
                if (cell.getTCard() == TCard.GUELTA) {
                    if (cell.getCamels() > 15)
                        cell.setCamels(15);
                }
            }
         }
    }
}
