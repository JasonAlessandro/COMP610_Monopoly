package monopoly;

public class PlayerProperty {

    private int money;
    private int numberOfProperties;

    //Constructor and Get Set methods
    public PlayerProperty(int initialMoney) {
        this.money = initialMoney;
        this.numberOfProperties = 0;
    }

    public int getMoney() {
        return money;
    }

    public int getNumberOfProperties() {
        return numberOfProperties;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void addMoney(int amount) {
        //Increases the player's money by the specified amount
        this.money += amount;
    }

    public void subtractMoney(int amount) {
        // Decreases the player's money by the specified amount.
        this.money -= amount;
    }

    public void addProperty() {
        // Increases the number of properties owned by the player by 1.
        this.numberOfProperties++;
    }

}
