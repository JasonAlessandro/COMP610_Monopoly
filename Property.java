package com.mycompany.monopoly;

public class Property {

    private String name;
    private int cost;
    private int rent;
    private Player owner;
    private int cellAddress;

    public Property(String name, int cost, int rent, Player owner, int cellAddress) {
        this.name = name;
        this.cost = cost;
        this.rent = rent;
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

    public void setOwner(Player player) {
        this.owner = player;
    }

    public Player getOwner() {
        return owner;
    }

    public int getCellAddress() {
        return cellAddress;
    }
    
    public void upgradeProperty(Player player) {
        if (player.getProperty().getMoney() >= upgradeCost) {
            player.getProperty().subtractMoney(upgradeCost);

            propertyLevel++;
            rent += rent;

            System.out.println(player.getName() + " has upgraded the property " + getName() + ".");
        } else {

            System.out.println(player.getName() + " doesn't have enough money to upgrade the property " + getName() + ".");
        }
    }

}
