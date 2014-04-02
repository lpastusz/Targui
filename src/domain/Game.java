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
    
    private boolean isPlayerInGame(Player player) {
        for (int i = 0; i < Constants.BoardSize; i++)
            for (int j = 0; j < Constants.BoardSize; j++)
                if (board.getCell(i, j).getOwner() == player)
                    return true;
        return false;
    }
    
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
    
    public void placeSettlementCard(int row, int column, Player player) {
        Cell cell = board.getCell(row, column);
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
        if (counter++ < mCardsNumber) mCards.add(new MineAttackedMCard(board, plRepository));
        if (counter++ < mCardsNumber) mCards.add(new SilverDiscoveredMCard(board, plRepository));
        if (counter++ < mCardsNumber) mCards.add(new SettlementsAttackedMCard(board, plRepository));
        if (counter++ < mCardsNumber) mCards.add(new WaterPoisonedMCard(board, plRepository));
        if (counter++ < mCardsNumber) mCards.add(new ErgChangedMCard(board, plRepository));
        if (counter++ < mCardsNumber) mCards.add(new GueltaCamelsBornMCard(board, plRepository));
        if (counter++ < mCardsNumber) mCards.add(new RagRainfallMCard(board, plRepository));
        if (counter++ < mCardsNumber) mCards.add(new EntireBoardAttackedMCard(board, plRepository));    
        if (counter++ < mCardsNumber) mCards.add(new PlagueBreakingOutMCard(board, plRepository));
        if (counter++ < mCardsNumber) mCards.add(new CaravanAttackedMCard(board, plRepository));
        if (counter++ < mCardsNumber) mCards.add(new SaharaDroughtMCard(board, plRepository));
        if (counter++ < mCardsNumber) mCards.add(new SaharaSandstormMCard(board, plRepository)); 
        for (int i = 0; i < Constants.PlayerCount; i++)
            if (counter++ < mCardsNumber) mCards.add(new PlayerGiftMCard(plRepository.getPlayer(i), board, plRepository));
        
        Collections.shuffle(mCards, new Random(System.currentTimeMillis()));
    }
    
    public boolean isGameEnd() {
        boolean[] playersWithPosession;
        playersWithPosession = new boolean[Constants.PlayerCount];
        for (int i = 0; i < Constants.PlayerCount; i++)
            playersWithPosession[i] = false;
        
        for (int i = 0; i < Constants.BoardSize; i++) {
            for (int j = 0; j < Constants.BoardSize; j++) {
                if (board.getCell(i, j).getOwner() != null)
                    playersWithPosession[board.getCell(i, j).getOwner().getNumber()] = true;
            }
        }
        
        int count = 0;
        for (int i = 0; i < Constants.PlayerCount; i++)
            if (playersWithPosession[i])
                count += 1;
        if (count == 1)
            return true;
        
        if (round == null)
            return false;
        if (round.getNumber() == roundNumber-1)
            return round.isRoundEnd();
        else
            return false;
    }
   
    
    public int throwDice() {
        return dice.getNumber();
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
        do {
            int playerNumber = round.getNextCard();
            // you should instanceof
            if (playerNumber == -1)
                return playerNumber;

            if (isPlayerInGame(plRepository.getPlayer(playerNumber)))
                return playerNumber; 
        }while(true);
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
   
   public void performMove(Player player, int sx, int sy, int dx, int dy, int camels) {
       if (board.getCell(dy, dx).getOwner() != player)
           round.playerLoseCell(player.getNumber());
        board.performMove(player, sx, sy, dx, dy, camels);
        round.performMove();
    }
   
       public boolean isAttack(Player player, int dx, int dy) {
        return board.isAttack(player, dx, dy);
    }
       
    public boolean isOwner(Player player, int x, int y) {
        return board.isOwner(player, x, y);
    }
    
    public void buyCamels(Player player, int amount) {
        try {
            player.subtractSilverForCamels(amount);
        } catch(IllegalArgumentException e) {
            throw e;
        }
    }
    
    public void placeCamels(Player player, int x, int y, int amount) {
        if (player != board.getCell(x,y).getOwner())
            throw new IllegalArgumentException();
        
        if (board.getCell(y, x).getTCard() == TCard.CHOTT)
            throw new IllegalArgumentException();
        board.placeCamels(x, y, amount); 
        round.performPurchase();
    }
    
    public Player getPlayer(int x, int y) {
        return board.getPlayer(x, y);
    }
    
    public void attack(int sx, int sy, int dx, int dy, int num) {
        Cell cellDestination = board.getCell(dy, dx);
        Cell cellSource = board.getCell(sy, sx);
        
        int dmg = num;
        dmg += (round.didErgStrategicValueChanged() && (cellSource.getTCard() == TCard.ERG)) ? Constants.ErgUpdatedStrategicValue : cellSource.getTCard().getStrategicValue();
        dmg /= 2;
        cellDestination.removeCamels(dmg);
    }
    
    public boolean isWithCamels(int dx, int dy) {
        return (board.getCell(dy, dx).getCamels() > 0);
    }
    
    public void giveLevy() {
        int[] count;
        count = new int[Constants.PlayerCount];
        
        for (int row = 0; row < Constants.BoardSize; row++) {
            for (int column = 0; column < Constants.BoardSize; column++) {
                Cell cell = board.getCell(row, column);
                if (cell.getOwner() != null) {
                    if ((cell.getTCard() == TCard.MOUNTAIN) && (round.didMountanEconomicValueChanged()))
                        count[cell.getOwner().getNumber()] += Constants.MountainUpdatedEconomicValue;
                    else
                        count[cell.getOwner().getNumber()] += cell.getTCard().getEconomicValue();
                }
            }
        }
        
        for (int row = 0; row < Constants.BoardSize; row++) {
            for (int column = 0; column < Constants.BoardSize; column++) {
                Cell cell = board.getCell(row, column);
               if (cell.getTCard() == TCard.SETTLEMENT) {
                   if (cell.getSector() != cell.getOwner().getSector()) {
                       count[plRepository.getPlayer(cell.getSector()).getNumber()] = 0;                    
                   }
               }
            }
        }
        
        
        for (int i = 0; i < Constants.PlayerCount; i++)
            if (!round.didPlayerLostCell(i))
                plRepository.getPlayer(i).addSilver(count[i]);
    }
    
    
    public boolean isNextRoundCardMCard() {
        return round.isNextRoundCardMCard();
    }
}
