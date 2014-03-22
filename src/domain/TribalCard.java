package domain;

/**
 *
 * @author Lukas.Pasta
 */
public class TribalCard extends RoundCard{
    private Player player;
    
    public TribalCard(Player playerParam) {
        player = playerParam;
    }
    
    public Player getPlayer() {
        return player;
    }
}
