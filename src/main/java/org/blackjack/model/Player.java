package org.blackjack.model;

/**
 * This class represents an abstract player in the game.
 * It is the base class for the Real Player, Computer Player and the Dealer.
 *
 * @author Diana Pamfile
 */
public abstract class Player {
    protected String avatar;
    protected String username;
    protected Hand hand;
    protected int score;
    protected boolean standing;

    /**
     * Constructor that initializes a player with a username.
     * The other attribute are initialize by default.
     *
     * @param username
     */
    public Player(String username) {
        this.username = username;
        this.avatar = "/org/blackjack/view/defaultUser.png";
        this.hand = new Hand();
        this.score = 0;
        this.standing = true;
    }

    /**
     * Getter and setter of all the attributes.
     */
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    ;

    public Hand getHand() {
        return hand;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    ;

    public boolean getstanding() {
        return standing;
    }

    public void setstanding(boolean standing) {
        this.standing = standing;
    }

    /**
     * Adds a card to the player's hand when the player wants to hit.
     *
     * @param card
     */
    public void hit(GameCard card) {
        hand.addCard(card);
        this.score = calculateScore();
    }

    /**
     * Check if the player has gone bust. This means their hand's total value exceed 21.
     *
     * @return true if the has gone bust, false otherwise.
     */
    public boolean bust() {
        if (hand.calculateHandValue() > 21) {
            return true;
        }
        return false;
    }

    /**
     * Calculates and returns the player's current score.
     * The current score is the total value of their hand.
     *
     * @return the total point value of the player's hand.
     */
    public int calculateScore() {
        return hand.calculateHandValue();
    }

    /**
     * Resets the player's hand.
     */
    public void resetHand() {
        hand.clearHand();
    }
}
