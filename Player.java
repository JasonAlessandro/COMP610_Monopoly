package com.mycompany.monopoly;

import java.awt.Color;
import java.util.HashSet;

public class Player {

    private String name;
    private Color color;
    private int position;
    private PlayerProperty property;
    private boolean hasEndedTurn;
    private HashSet<Integer> ownedProperties;

    public Player(String name, Color color, int startingMoney) {
        this.name = name;
        this.color = color;
        this.position = 0;
        this.property = new PlayerProperty(startingMoney);
        this.hasEndedTurn = false;
        this.ownedProperties = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public PlayerProperty getProperty() {
        return property;
    }

    public void setPlayerProperty(PlayerProperty property) {
        this.property = property;
    }

    public boolean getHasEndedTurn() {
        return hasEndedTurn;
    }

    public void setHasEndedTurn(boolean hasEndedTurn) {
        this.hasEndedTurn = hasEndedTurn;
    }

    public HashSet<Integer> getOwnedProperties() {
        return ownedProperties;
    }

    public void addProperty(int position) {
        ownedProperties.add(position);
    }

}
