package domain.MCards;

import domain.Board;
import domain.MCard;
import domain.PlayerRepository;
import targui.Constants;
import domain.Cell;
import domain.TCard;

/**
 *
 * @author Lukas.Pasta
 */
public class RagRainfallMCard extends MCard{
    public RagRainfallMCard(Board boardParam, PlayerRepository plRepositoryParam) {
        super(boardParam, plRepositoryParam);
    }  
    
    public void proceed() {
         for (int i = 0; i < Constants.BoardSize; i++) {
            for (int j = 0; j < Constants.BoardSize; j++) {
                Cell cell = board.getCell(j, i);
                if (cell.getTCard() == TCard.REG)
                    cell.removeCamels(5);
            }        
         }
    }
}
