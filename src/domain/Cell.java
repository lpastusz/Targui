package domain;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 *
 * @author Lukas.Pasta
 */
public class Cell {
    private Sector sector;
    private ObjectProperty <TCard> tCard;
    private ObjectProperty<Player> owner;
    private IntegerProperty camels;
    Cell() {
        tCard = new SimpleObjectProperty<TCard>();
        camels = new SimpleIntegerProperty();
        owner = new SimpleObjectProperty<Player>();
    }
    
    Cell(Sector sectorParam) {
        sector = sectorParam;
        camels = new SimpleIntegerProperty();
        camels.set(0);
        owner = new SimpleObjectProperty<Player>();
        tCard = new SimpleObjectProperty<TCard>();
    }
    
    public void setTCard(TCard tCardParam) {
        tCard.set(tCardParam);
    }
    
    public TCard getTCard() {
        return tCard.get();
    }
    
    public Sector getSector() {
        return sector;
    }
    
    public void addCamels(int num) {
        camels.set(camels.get() + num);
    }
    
    public int getCamels() {
        return camels.get();
    }
    
    public void removeCamels() {
        camels.set(0);
    }
    
    public void removeCamels(int num) {
        if (camels.get() >= num)
            camels.set(camels.get()-num);
        else
            camels.set(0);
    }
    
    public void setCamels(int num) {
        camels.set(0);
    }
    
    public void setOwner(Player player) {
        owner.set(player);
    }
    
    public Player getOwner() {
        return owner.get();
    }
    
    public ObjectProperty<TCard> getTCardProperty() {
        return tCard;
    }
    
    public ObjectProperty<Player> getPlayerProperty() {
        return owner;
    }
    
    public IntegerProperty getCamelsProperty() {
        return camels;
    }
}
