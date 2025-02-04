package org.blackjack.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * This class represents a standard deck of playing cards.
 * It implements the Singleton patter to ensure that there is only one
 * instance of the deck in the game.
 *
 * @author Diana Pamfile
 */
public class Deck {
    private List<GameCard> deck;
    private static Deck instance;

    /**
     * Constructor of the new Deck.
     */
    private Deck() {
        deck = new ArrayList<>();
        initializeDeck();
    }

    /**
     * @return the instance of the deck.
     */
    public static Deck getInstance() {
        if (instance == null) {
            instance = new Deck();
        }
        return instance;
    }

    /**
     * This method initialize the deck with a 52 standard playing cards,
     * and clear existing cards if any.
     */
    public void initializeDeck() {
        deck.clear();
        Arrays.stream(Suit.values()).forEach(suit ->
                Arrays.stream(Value.values()).forEach(value ->
                        deck.add(new GameCard(suit, value))
                )
        );
    }

    public void shuffleDeck() {
        Collections.shuffle(deck);
    }


    /**
     * Draws the top card from the deck and remove it from the deck.
     *
     * @return the drawn GameCard
     */
    public GameCard drawCard() {
        return deck.remove(0);
    }


    /**
     * @return the number of remaining cards in the deck.
     */
    public int remainingCards() {
        return deck.size();
    }


    /*
     * Refills the deck if the number of remaining cards is less than 39 cards.
     */
    public void refillDeck() {
        if (remainingCards() < 20) {
            initializeDeck();
            shuffleDeck();
        }
    }


}
