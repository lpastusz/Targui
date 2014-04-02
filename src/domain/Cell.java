package domain;

/**
 *
 * @author Lukas.Pasta
 */
public class Cell {
    private Sector sector;
    private TCard tCard;
    private Player owner;
    private int camels;
    Cell() {
        
    }
    
    Cell(Sector sectorParam) {
        sector = sectorParam;
        camels = 0;
        owner = null;
    }
    
    public void setTCard(TCard tCardParam) {
        tCard = tCardParam;
    }
    
    public TCard getTCard() {
        return tCard;
    }
    
    public Sector getSector() {
        return sector;
    }
    
    public void addCamels(int num) {
        camels += num;
    }
    
    public int getCamels() {
        return camels;
    }
    
    public void removeCamels() {
        camels = 0;
    }
    
    public void removeCamels(int num) {
        camels = (camels >= num) ? camels-num : 0; 
    }
    
    public void setCamels(int num) {
        camels = num;
    }
    
    public void setOwner(Player player) {
        owner = player;
    }
    
    public Player getOwner() {
        return owner;
    }
}
