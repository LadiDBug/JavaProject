package org.blackjack.exception;

/**
 * Exception thrown when a class can't be built
 *
 * @author Diana Pamfile
 */
public class CantBuildClassException extends RuntimeException {

    public CantBuildClassException(String errorMessage) {
        super(errorMessage);
    }

}
