package domain;
import targui.Constants;
import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author Lukas.Pasta
 */
public class Player {
    static int playerNumber = 0;
    private final int               number;
    private final String            name;
    private final Sector            sector;
    private final String            color;
    private int                     silver;
    private ArrayList<TribalCard>   tCards;
    
    public Player(String nameParam, String colorParam, Sector sectorParam) {
        number = playerNumber++;
        name = nameParam;
        sector = sectorParam;
        color = colorParam;
        silver = Constants.StartingSilver;
        tCards = new ArrayList<TribalCard>();
        for (int i = 0; i < 5; i++)
            tCards.add(new TribalCard(this));
    }
    
    public int getNumber() {
        return number;
    }
    
    public String getName() {
        return name;
    }
    
    public Sector getSector() {
        return sector;
    }
    
    public String getColor() {
        return color;
    }
    
    public TribalCard getTCard() {
        return tCards.remove(0);
    }
    
    public void insertTCard(TribalCard card) {
        tCards.add(card);
    }
    
    public int getSilver() {
        return silver;
    }
    
    public void subtractSilverForCamels(int amount) {
        if (silver < amount)
            throw new IllegalArgumentException();
        silver -= amount;
    }
    
    public void addSilver(int num) {
        silver = num;
    }
    
    public void removeSilver(int num) {
        silver = (silver >= num) ? silver-num : 0;
    }
    
    public void removeSilver() {
        silver = 0;
    }
}
