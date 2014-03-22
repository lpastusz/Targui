package domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.lang.Exception.*;

/**
 *
 * @author Lukas.Pasta
 */
public class Round {
    private static int roundNumber = 0;
    private ArrayList<RoundCard> rCards;
    private ArrayList<RoundCard> usedCards;
    private int number;
    protected int moveActionsAvailable, purchaseActionsAvailable;
    Round() {        
        number = roundNumber++;
        rCards = new ArrayList<RoundCard>();
        usedCards = new ArrayList<RoundCard>();
    }
    
    public int getNumber() {
        return number;
    }
    
    public boolean isRoundEnd() {
        return (rCards.isEmpty());
    }
    
    public void addCard(RoundCard card) {
        rCards.add(card);
    }
    
    public int getNextCard() {
        RoundCard card = rCards.remove(0);
        setNewActionsAvailable();
        
        usedCards.add(card);
        if (card instanceof TribalCard) {
            Player player = ((TribalCard)card).getPlayer();
            return player.getNumber();
        }
        else if (card instanceof MCard) {
            //just destroying Misfortune card
            return -1;
        }
        return 0;
    }
    
    protected void setNewActionsAvailable() {
        throw new UnsupportedOperationException();
    }
    
    public void shuffleCards() {
        Collections.shuffle(rCards, new Random(System.currentTimeMillis()));
    }
    
    public void returnCards() {
        RoundCard card;
        
        while(!usedCards.isEmpty()) {
            card = usedCards.remove(0);
            if (card instanceof TribalCard) {
                ((TribalCard)card).getPlayer().insertTCard((TribalCard)card);
            }
        }     
    }
    
    
    public boolean canPerformMove() {
        return (moveActionsAvailable != 0);
    }
    
    public boolean canPerformPurchase() {
        return (purchaseActionsAvailable != 0);
    }
    
    public void performMove() {
        decreaseMoveAvailable();
    }
    
   public void performPurchase() {
        decreasePurchaseAvailable();
    }
   
   private void decreaseMoveAvailable() {
       moveActionsAvailable -= 1;
       if (moveActionsAvailable < purchaseActionsAvailable - 1) purchaseActionsAvailable -= 1;
   };
   private void decreasePurchaseAvailable() {
       purchaseActionsAvailable -= 1;
       if (moveActionsAvailable > purchaseActionsAvailable) moveActionsAvailable -= 1;
   }
}
