package org.blackjack.model;

import org.blackjack.view.TypePlayer;

public class Dealer extends Player {


    public Dealer() {
        super("DEALER", TypePlayer.DEALER);
        this.avatar = "/org/blackjack/view/croupier.png";
    }

    public void stand() {
        this.standing = true;
    }

    /*
    public void decideAction(GameCard card) {
        if (hand.calculateHandValue() < 17) {
            hit(card);
        }
        stand();

     */


}
