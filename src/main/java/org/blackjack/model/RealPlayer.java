package org.blackjack.model;

import org.blackjack.view.TypePlayer;

/**
 * The RealPlayer class represents a human player in the game.
 * This class extends the abstract Player class, adding additional attributes.
 *
 * @author Diana Pamfile
 */
public class RealPlayer extends Player {
    private int level;
    private int totalGames;
    private int wonGames;
    private int lostGames;
    private int totalFiches;
    private int bet;


    /**
     * Constructor that initializes a RealPlayer with a username.
     * The player starts with a level of 0, 5000 fiches, and no games played.
     *
     * @param username
     */
    public RealPlayer(String username) {
        super(username, TypePlayer.PLAYER);
        this.level = 0;
        this.totalGames = 0;
        this.wonGames = 0;
        this.lostGames = 0;
        this.totalFiches = 5000;
        this.bet = 0;
    }

    /**
     * Getter and setter methods for player attributes..
     */
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getTotalGames() {
        return totalGames;
    }

    public void setTotalGames(int totalGames) {
        this.totalGames = totalGames;
    }

    public int getWonGames() {
        return wonGames;
    }

    public void setWonGames(int wonGames) {
        this.wonGames = wonGames;
    }

    public int getLostGames() {
        return lostGames;
    }

    public void setLostGames(int lostGames) {
        this.lostGames = lostGames;
    }

    public int getTotalFiches() {
        return totalFiches;
    }

    public void setTotalFiches(int totalFiches) {
        this.totalFiches = totalFiches;
    }

    public int getBet() {
        return bet;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

    /**
     * Deducts the current bet amount from the player's total fiches when they place a bet.
     */
    public void toBet() {
        this.totalFiches -= bet;
    }

    /*
     * Increase the player's level by 1 for every games won.
     */
    public void increaseLevel() {
        if (wonGames % 5 == 0) {
            this.level++;
        }

    }

    /**
     * Increases the count of won games by 1, increments the total games played,
     * and doubles the player's fiches based on the current bet.
     */
    public void increaseWonGames() {
        this.wonGames++;
        this.totalGames++;
        this.totalFiches += bet * 2;
    }

    /**
     * Increases the count of lost games by 1, increments the total games played,
     * and subtracts the bet from the player's total fiches.
     */
    public void increaseLostGames() {
        this.lostGames++;
        this.totalGames++;
        this.totalFiches -= bet;
    }


    /**
     * This method sets to true "standing" if the player will no longer wants to asks cards.
     */
    public void stand() {
        this.standing = true;
    }


    public void SaveData() {
        //chiamare metodo save data
    }

}
