package org.blackjack.model;

import org.blackjack.view.TypePlayer;

/**
 * ComputerPlayer class represents an AI player.
 * This class implements methods according a pattern, simulating a real player in the game.
 *
 * @author Diana Pamfile
 */
public class ComputerPlayer extends Player {


    public ComputerPlayer(String username, String avatar, TypePlayer type) {
        super(username, type);
        this.avatar = avatar;
    }

    @Override
    public void hit(GameCard card) {
        if (hand.calculateHandValue() <= 15) {
            hand.addCard(card);
            this.score = calculateScore();
        }
    }

    public void stand() {
        if (hand.calculateHandValue() > 16) {
            this.standing = true;
        }
    }

    public boolean split(boolean choiceToSplit) {
        choiceToSplit = true;
        return choiceToSplit;
    }


}
