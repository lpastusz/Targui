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
    private final Color             color;
    private int                     silver;
    private ArrayList<TribalCard>   tCards;
    
    public Player(String nameParam, Color colorParam, Sector sectorParam) {
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
    
    public Color getColor() {
        return color;
    }
    
    public TribalCard getTCard() {
        return tCards.remove(0);
    }
    
    public void insertTCard(TribalCard card) {
        tCards.add(card);
    }
}