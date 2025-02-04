package org.blackjack.view;

/**
 * This class is an enum used for the type of message to display
 * at the end of a game or if the player "bust" or do "blackjack"
 *
 * @author Diana Pamfile
 */
public enum MessageType {
    BUST("BUST!"),
    BLACKJACK("BLACKJACK!"),
    WIN("WIN!"),
    LOSE("LOSE!"),
    TIE("TIE!");

    private final String message;

    MessageType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
