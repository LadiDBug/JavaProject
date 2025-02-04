package org.blackjack.model;

import java.util.ArrayList;
import java.util.List;

/**
 * The Hand class represents a player's hand.
 *
 * @author Diana Pamfile
 */
public class Hand {

    private List<GameCard> hand;
    private int totalPoints;

    /*
     * Default constructor that initializes an empty hand with zero points.
     */
    public Hand() {
        this.hand = new ArrayList<>();
        this.totalPoints = 0;
    }

    /**
     * @return the total points of the hand.
     */
    public int getTotalPoint() {
        return totalPoints;
    }

    /**
     * @return the list of cards in the hand.
     */
    public List<GameCard> getHand() {
        return hand;
    }

    /**
     * @param card the GameCard to add to the hand.
     */
    public void addCard(GameCard card) {
        hand.add(card);
    }


    /**
     * Calculates the total value of the cards in the hand.
     * Aces are initially counted as 11, but if the total points exceed 21, they are counted as 1.
     *
     * @return total points of the hand.
     */
    public int calculateHandValue() {
        totalPoints = 0;
        int totalAce = numberOfAce();

//		Add points for cards that are not Aces	   
        for (GameCard card : hand) {
            totalPoints += (card.getValue() == Value.ACE) ? 0 : card.getValue().getPoints();
        }

//      Add Aces as 11 points initially	    
        for (int i = 0; i < totalAce; i++) {
            totalPoints += 11;
        }

//		Adjust the value of Aces if thetotal exceed 21
        while (totalPoints > 21 && totalAce > 0) {
            totalPoints -= 10;
            totalAce--;
        }

        return totalPoints;
    }

    /**
     * Counts the number of Aces in the hand.
     *
     * @return the number of Aces.
     */
    public int numberOfAce() {
        int numberOfAce = 0;
        for (GameCard card : hand) {
            if (card.getValue() == Value.ACE) {
                numberOfAce++;
            }
        }
        return numberOfAce;
    }

    /*
     * Clears the hand, removing all cards and resetting the total points.
     */
    public void clearHand() {
        hand.clear();
        totalPoints = 0;
    }
}
