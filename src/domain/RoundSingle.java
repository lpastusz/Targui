package domain;

/**
 *
 * @author Lukas.Pasta
 */
public class RoundSingle extends Round{
    RoundSingle(int roundNum) {
        super(roundNum);
        moveActionsAvailable = purchaseActionsAvailable = 1;
    }
    
    @Override
    protected void setNewActionsAvailable() {
        moveActionsAvailable = purchaseActionsAvailable = 1;
    }
   
}
