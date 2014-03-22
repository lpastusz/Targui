package domain;
import java.util.ArrayList;
import java.lang.Exception.*;
import domain.MCards.*;
import targui.Constants;
import java.util.Collections;
import java.util.Random;
/**
 *
 * @author Lukas.Pasta
 */
public class Game {
    private Dice dice;
    private Board board;
    private ArrayList<MCard>  mCards;
    private ArrayList<TCard>  tCards;
    private ArrayList<TCard>  tSettlementCards;
    private PlayerRepository  plRepository;
    private Round             round;

    private int roundNumber;
    
    public Game() {
        board = new Board();
        mCards = new ArrayList<MCard>();
        tCards = new ArrayList<TCard>();
        tSettlementCards = new ArrayList<TCard>();
        dice = new Dice();
        createTCards();
    }
    
    public void setPlayerRepository(PlayerRepository plRepositoryParam) {
        plRepository = plRepositoryParam;
    }
    
    
    private void createTCards() {
        for (int i = 0; i < Constants.TCardCounts.Settlement; i++)
            tSettlementCards.add(TCard.SETTLEMENT);
        for (int i = 0; i < Constants.TCardCounts.Guleta; i++)
            tCards.add(TCard.GUELTA);
        for (int i = 0; i < Constants.TCardCounts.Erg; i++)
            tCards.add(TCard.ERG);
        for (int i = 0; i < Constants.TCardCounts.Reg; i++)
            tCards.add(TCard.REG);
        for (int i = 0; i < Constants.TCardCounts.Mountain; i++)
            tCards.add(TCard.MOUNTAIN);
        for (int i = 0; i < Constants.TCardCounts.Mine; i++)
            tCards.add(TCard.MINE);
        for (int i = 0; i < Constants.TCardCounts.Fesh; i++)
            tCards.add(TCard.FESH);
        for (int i = 0; i < Constants.TCardCounts.Chott; i++)
            tCards.add(TCard.CHOTT); 
    }
    
    public void placeTCardsOnBoard() {
        TCard mine = null;
        for (TCard t : tCards) {
            if (t.equals(TCard.MINE)) {
                mine = t;
            }
        }
        tCards.remove(mine);
        
        Collections.shuffle(tCards, new Random(System.currentTimeMillis()));
        board.placeCardsOnBoard(mine, tCards);
    }
    
    public Cell getCell(int row, int column) {
        return board.getCell(row, column);
    }
    
    public Sector getSector(int sectorNum) {
        return board.getSector(sectorNum);
    }
    
    public void placeSettlementCard(int row, int column, int playerNumber) {
        Cell cell = board.getCell(row, column);
        Player player = plRepository.getPlayer(playerNumber);
        if (cell.getSector() != player.getSector())
            throw new IllegalArgumentException("wrong sector");
        else {
            tCards.add(cell.getTCard());
            cell.setTCard(tSettlementCards.remove(0));
            cell.setOwner(player);
            cell.addCamels(10);
        }
    }
    
    public void registerRounds(int roundNumberParam) {
        if (roundNumberParam < Constants.MinRounds || roundNumberParam > Constants.MaxRounds)
            throw new IllegalArgumentException();

        roundNumber = roundNumberParam;
        
        createMCards(roundNumberParam);
    }
    
    private void createMCards(int mCardsNumber) {
        int counter = 0;
        if (counter++ < mCardsNumber) mCards.add(new MineAttackedMCard());
        if (counter++ < mCardsNumber) mCards.add(new SilverDiscoveredMCard());
        if (counter++ < mCardsNumber) mCards.add(new SettlementsAttackedMCard());
        if (counter++ < mCardsNumber) mCards.add(new WaterPoisonedMCard());
        if (counter++ < mCardsNumber) mCards.add(new ErgChangedMCard());
        if (counter++ < mCardsNumber) mCards.add(new GueltaCamelsBornMCard());
        if (counter++ < mCardsNumber) mCards.add(new RagRainfallMCard());
        if (counter++ < mCardsNumber) mCards.add(new EntireBoardAttackedMCard());    
        if (counter++ < mCardsNumber) mCards.add(new PlagueBreakingOutMCard());
        if (counter++ < mCardsNumber) mCards.add(new CaravanAttackedMCard());
        if (counter++ < mCardsNumber) mCards.add(new SaharaDroughtMCard());
        if (counter++ < mCardsNumber) mCards.add(new SaharaSandstormMCard()); 
        for (int i = 0; i < Constants.PlayerCount; i++)
            if (counter++ < mCardsNumber) mCards.add(new PlayerGiftMCard(plRepository.getPlayer(i)));
        
        Collections.shuffle(mCards, new Random(System.currentTimeMillis()));
    }
    
    public boolean isGameEnd() {
        if (round == null)
            return false;
        if (round.getNumber() == roundNumber-1)
            return round.isRoundEnd();
        else
            return false;
    }
   
    
    public int throwTurnNumber() {
        int num = dice.getNumber();       
        return num;
    }
    
    public boolean isRoundEnd() {
        return round.isRoundEnd();
    }
    
    public void createNewRound(int turns) 
    {
        if (round != null) {
            round.returnCards();
        }      
        
        if (turns == 6) {
            turns = 1;
            round = new RoundDouble();
        }
        else {
            round = new RoundSingle();
        }
        
        round.addCard(mCards.remove(0));
        for (int i = 0;i < Constants.PlayerCount; i++)
            for (int j = 0; j < turns; j++)
                round.addCard(plRepository.getPlayer(i).getTCard());
        
        round.shuffleCards();
    }
    
    public int getNextRoundCard() {
        return round.getNextCard();    
    }
    
    public boolean canPerformMove() {
        return round.canPerformMove();
    }
    
    public boolean canPerformPurchase() {
        return round.canPerformPurchase();
    }
    
    public void performMove() {
        round.performMove();
    }
    
   public void performPurchase() {
        round.performPurchase();
    }
}
