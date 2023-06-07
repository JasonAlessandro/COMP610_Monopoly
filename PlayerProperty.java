package com.mycompany.monopoly;

public class PlayerProperty {

    private int money;
    private int numberOfProperties;

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

    public void addMoney(int amount) {
        this.money += amount;
    }

    public void subtractMoney(int amount) {
        this.money -= amount;
    }

    public void addProperty() {
        this.numberOfProperties++;
    }

}
