package org.blackjack.model;

import org.blackjack.view.TypePlayer;

/**
 * This class represents the dealer in the game.
 *
 * @author Diana Pamfile
 */
public class Dealer extends Player {

    /**
     * Constructor of the Dealer.
     * The dealer is a player with the name "DEALER" and the type TypePlayer.DEALER.
     * The avatar of the dealer is set to a default image.
     */
    public Dealer() {
        super("DEALER", TypePlayer.DEALER);
        this.avatar = "/org/blackjack/view/croupier.png";
    }

    /**
     * This method set the dealer's hand to standing.
     */
    public void stand() {
        this.standing = true;
    }


}
