package domain.MCards;
import domain.*;
import targui.Constants;

/**
 *
 * @author Lukas
 */
public class SaharaSandstormMCard extends MCard{
    public SaharaSandstormMCard(Board boardParam, PlayerRepository plRepositoryParam) {
        super(boardParam, plRepositoryParam);
    }    
    
    public void proceed() {
        for (int i = 0; i < Constants.BoardSize; i++) {
            for (int j = 0; j < Constants.BoardSize; j++) {
                Cell cell = board.getCell(j, i);
                if (cell.getTCard() == TCard.ERG) {
                    cell.removeCamels(3);
                }
            }
        }
    }
}
