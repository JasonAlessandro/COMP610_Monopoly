/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.monopoly;

/**
 *
 * @author taki
 */
public class Horse {
    private double winProbability;
    private double payoutRatio;

    public Horse(double winProbability, double payoutRatio) {
        this.winProbability = winProbability;
        this.payoutRatio = payoutRatio;
    }

    public double getWinProbability() {
        return winProbability;
    }

    public double getPayoutRatio() {
        return payoutRatio;
    }
}

