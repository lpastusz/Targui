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
public class PlagueBreakingOutMCard extends MCard{
    public PlagueBreakingOutMCard(Board boardParam, PlayerRepository plRepositoryParam) {
        super(boardParam, plRepositoryParam);
    }
    
    public void proceed() {
         for (int i = 0; i < Constants.BoardSize; i++) {
            for (int j = 0; j < Constants.BoardSize; j++) {
                Cell cell = board.getCell(j, i);
                if (cell.getTCard() == TCard.SETTLEMENT) {
                    int camels = cell.getCamels();
                    if ((camels % 2) == 1) camels -= 1;
                    cell.setCamels(camels/2);
                }
            }
         }        
    }
}
