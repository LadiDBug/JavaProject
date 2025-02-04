package org.blackjack.api;

/**
 * Enum for the different types of packages that can be sent from the model to the view.
 */
public enum PackageType {
    DRAW,
    BLACKJACK,
    BUST,
    WIN,
    LOSE,
    TIE,
    SETUP,
    SCORE,
    HIT,
    DRAW_DEALER,
    RESET,
    RESET_DECK,
    UPDATE
}
