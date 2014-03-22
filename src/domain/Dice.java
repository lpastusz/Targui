package domain;
import java.util.Random;
/**
 *
 * @author Lukas.Pasta
 */
public class Dice {
    
    public int getNumber() {
        Random generator = new Random(System.currentTimeMillis());
        return (generator.nextInt(6)+1);
    }
}
