package org.blackjack.model;

import org.blackjack.view.TypePlayer;

/**
 * This class represents an AI player in the game.
 * This class extends the {@link Player} class and implements the computer behavior.
 *
 * @author Diana Pamfile
 */
public class ComputerPlayer extends Player {

    /**
     * Constructor of a ComputerPlayer with a specified username, avatar and type.
     *
     * @param username
     * @param avatar
     * @param type
     */
    public ComputerPlayer(String username, String avatar, TypePlayer type) {
        super(username, type);
        this.avatar = avatar;
    }

    /**
     * This method makes the computer hit if the hand value is less than 15.
     *
     * @param card
     */
    @Override
    public void hit(GameCard card) {
        if (hand.calculateHandValue() <= 15) {
            hand.addCard(card);
            this.score = calculateScore();
        }
    }

    /*
     * The AI player stands if the hand value is greater than 16.
     */
    public void stand() {
        if (hand.calculateHandValue() > 16) {
            this.standing = true;
        }
    }
}
