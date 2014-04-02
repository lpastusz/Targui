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
public class SettlementsAttackedMCard extends MCard{
    public SettlementsAttackedMCard(Board boardParam, PlayerRepository plRepositoryParam) {
        super(boardParam, plRepositoryParam);
    } 
    
    public void proceed() {
        boolean[] p = new boolean[Constants.PlayerCount];
        for (int pl = 0; pl < Constants.PlayerCount; pl++)
            p[pl] = false;
        
        for (int i = 0; i < Constants.BoardSize; i++) {
            for (int j = 0; j < Constants.BoardSize; j++) {
                Cell cell = board.getCell(j, i);
                if (cell.getTCard() == TCard.SETTLEMENT) {
                    for (int pl = 0; pl < Constants.PlayerCount; pl++)
                        if (cell.getOwner() == plRepository.getPlayer(pl))
                            p[pl] = true;
                }     
            }
        }
        
        for (int pl = 0; pl < Constants.PlayerCount; pl++)
            if (p[pl] == true)
                plRepository.getPlayer(pl).removeSilver();
    }
}
