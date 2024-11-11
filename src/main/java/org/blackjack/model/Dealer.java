package org.blackjack.model;

public class Dealer extends Player {


    public Dealer() {
        super("DEALER");
        this.avatar = "/org/blackjack/view/croupier.png";
    }

    public void stand() {
        this.standing = true;
    }

    public void decideAction(GameCard card) {
        if (hand.calculateHandValue() < 17) {
            hit(card);
        }
        stand();
    }


}
