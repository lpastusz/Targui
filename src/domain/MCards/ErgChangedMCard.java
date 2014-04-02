package domain.MCards;

import domain.Board;
import domain.MCard;
import domain.PlayerRepository;

/**
 *
 * @author Lukas.Pasta
 */
public class ErgChangedMCard extends MCard{
    public ErgChangedMCard(Board boardParam, PlayerRepository plRepositoryParam) {
        super(boardParam, plRepositoryParam);
    } 
    
    public void proceed() {
        
    }
}
