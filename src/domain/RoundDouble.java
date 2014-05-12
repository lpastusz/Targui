package domain;

import java.util.ArrayList;

/**
 *
 * @author Lukas.Pasta
 */
public class RoundDouble extends Round{
    RoundDouble(int roundNum) {
        super(roundNum);
        moveActionsAvailable = purchaseActionsAvailable = 2;
    }
    
    @Override
    protected void setNewActionsAvailable() {
        moveActionsAvailable = purchaseActionsAvailable = 2;
    }
}
