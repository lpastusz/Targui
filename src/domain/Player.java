package domain;
import targui.Constants;
import java.util.ArrayList;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

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
    private IntegerProperty         silver;
    private ArrayList<TribalCard>   tCards;
    
    public Player(String nameParam, String colorParam, Sector sectorParam) {
        number = playerNumber++;
        name = nameParam;
        sector = sectorParam;
        color = colorParam;
        silver = new SimpleIntegerProperty();
        silver.set(Constants.StartingSilver);
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
        return silver.get();
    }
    
    public void subtractSilverForCamels(int amount) {
        if (silver.get() < amount)
            throw new IllegalArgumentException();
        silver.set(silver.get() - amount);
    }
    
    public void addSilver(int num) {
        silver.set(num);
    }
    
    public void removeSilver(int num) {
        if (silver.get() >= num)
            silver.set(silver.get() - num);
        else
            silver.set(0);
    }
    
    public void removeSilver() {
        silver.set(0);
    }
    
    public IntegerProperty getSilverProperty() {
        return silver;
    }
}
