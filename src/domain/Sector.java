package domain;
import java.util.ArrayList;
/**
 *
 * @author Lukas.Pasta
 */
public class Sector {
    static int sectorNumber = 0;
    
    private int number;
    
    Sector() {
        number = sectorNumber++;
    }
    
    public int getNumber() {
        return number;
    }
}
