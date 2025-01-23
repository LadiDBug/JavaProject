package org.blackjack.view;

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
