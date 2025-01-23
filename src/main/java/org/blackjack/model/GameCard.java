package org.blackjack.model;

/**
 * This class represents a playing card in the game.
 * This class implements the {@link Card} interface.
 *
 * @author Diana Pamfile
 **/
public class GameCard implements Card {

    private final Suit suit;
    private final Value value;
    private boolean visible;

    /**
     * Contructor of a GameCard with a specified suit and value.
     * By default, the card is set to visible.
     *
     * @param suit
     * @param value
     */
    public GameCard(Suit suit, Value value) {
        this.suit = suit;
        this.value = value;
        this.visible = true;
    }

    /**
     * @return the {@link Suit} of this card.
     */
    @Override
    public Suit getSuit() {
        return suit;
    }

    /**
     * @return the {@link Value} of this card.
     */
    @Override
    public Value getValue() {
        return value;
    }


    /**
     * @return true if the card is visible, false otherwise.
     **/
    public boolean getVisible() {
        return visible;
    }

    /**
     * @param visible true to make the card visible, false to hide it.
     **/
    public void setVisible(boolean visible) {
        this.visible = visible;
    }


}
