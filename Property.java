package monopoly;

public class Property {

    private String name;
    private int cost;
    private int rent;
    private Player owner;
    private int cellAddress;
    private int propertyLevel;
    private int upgradeCost;
    
    public Property(String name, int cost, int rent,int upgradeCost,int propertyLevel, Player owner, int cellAddress) {
        this.name = name;
        this.cost = cost;
        this.rent = rent;
        this.upgradeCost = upgradeCost;
        this.propertyLevel = propertyLevel;
        this.owner = null;
        this.cellAddress = cellAddress;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public int getRent() {
        return rent;
    }
    public void setRent(int rent){
        this.rent = rent;
    }

    public void setOwner(Player player) {
        this.owner = player;
    }

    public Player getOwner() {
        return owner;
    }

    public int getCellAddress() {
        return cellAddress;
    }
    
     public int getUpgradeCost() {
        return upgradeCost;
    }

    public int getPropertyLevel() {
        return propertyLevel;
    }

    public void setPropertyLevel(int propertyLevel) {
        this.propertyLevel = propertyLevel;
    }
    
     

}
