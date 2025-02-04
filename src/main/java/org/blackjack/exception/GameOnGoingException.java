package org.blackjack.exception;

/**
 * This exception is thrown when a game is already in progress and the user tries to start a new one.
 * It is used in the GameController class.
 * {@link org.blackjack.controller.GameController}
 *
 * @author Diana Pamfile
 */
public class GameOnGoingException extends RuntimeException {
    public GameOnGoingException(String message) {
        super(message);
    }
}
