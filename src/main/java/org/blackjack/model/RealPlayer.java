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
    private SaveData saveData;

    /**
     * Constructor that initializes a RealPlayer with a username.
     * The player starts with a level of 0, 5000 fiches, and no games played.
     *
     * @param username
     */
    public RealPlayer(String username) {
        super(username, TypePlayer.PLAYER);
        this.saveData = new SaveData();

        //Se i dati non sono presenti, inizializzo file
        if (saveData.readFromFile("Username") == null) {
            saveData.initializeFile(saveData.getPath(), username);
        }
        //Se i dati sono presenti li carico dal file
        this.level = saveData.getLevel();
        this.totalGames = saveData.getTotalGames();
        this.wonGames = saveData.getTotalWins();
        this.lostGames = saveData.getTotalLosses();
        this.totalFiches = saveData.getTotalFiches();
        this.bet = 0;
        this.avatar = saveData.getAvatar();


    }

    /**
     * Getter and setter methods for player attributes..
     */

    public int getTotalGames() {
        return totalGames;
    }

    public void setTotalGames(int totalGames) {
        this.totalGames = totalGames;
        saveData.setTotalGames(totalGames);
    }


    public void setBet(int bet) {
        this.bet = bet;
    }

    public int getWonGames() {
        return wonGames;
    }

    public int getLostGames() {
        return lostGames;
    }

    public int getTotalFiches() {
        return totalFiches;
    }

    public int getLevel() {
        return level;
    }

    /**
     * Deducts the current bet amount from the player's total fiches when they place a bet.
     */
    public void toBet() {
        this.totalFiches -= bet;
        saveData.setTotalFiches(totalFiches);
    }

    /*
     * Increase the player's level by 1 for every games won.
     */
    public void increaseLevel() {
        if (wonGames % 5 == 0) {
            this.level++;
            saveData.setLevel(level);
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

        saveData.setTotalWins(wonGames);
        saveData.setTotalGames(totalGames);
        saveData.setTotalFiches(totalFiches);
    }

    /**
     * Increases the count of lost games by 1, increments the total games played,
     * and subtracts the bet from the player's total fiches.
     */
    public void increaseLostGames() {
        this.lostGames++;
        this.totalGames++;
        this.totalFiches -= bet;

        saveData.setTotalLosses(lostGames);
        saveData.setTotalGames(totalGames);
        saveData.setTotalFiches(totalFiches);
    }


    /**
     * This method sets to true "standing" if the player will no longer wants to asks cards.
     */
    public void stand() {
        this.standing = true;
    }


}
