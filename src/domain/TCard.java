package domain;

/**
 *
 * @author Lukas.Pasta
 */

public enum TCard {
    SETTLEMENT  (4,4),
    GUELTA      (3,3),
    ERG         (1,0),
    REG         (2,0),
    MOUNTAIN    (0,1),
    MINE        (5,5),
    FESH        (0,2),
    CHOTT       (0,0);
    private final int economicValue;
    private final int strategicValue;
    TCard(int economicValueParam, int strategicValueParam) {
        economicValue = economicValueParam;
        strategicValue = strategicValueParam;
    }
    
    public int getEconomicValue() {
        return economicValue;
    }
    
    public int getStrategicValue() {
        return strategicValue;
    }
}