package org.blackjack.exception;

public class GameOnGoingException extends RuntimeException {
    public GameOnGoingException(String message) {
        super(message);
    }
}
