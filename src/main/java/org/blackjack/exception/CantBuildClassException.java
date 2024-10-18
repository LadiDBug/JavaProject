package org.blackjack.exception;

public class CantBuildClassException extends RuntimeException {

    public CantBuildClassException(String errorMessage) {
        super(errorMessage);
    }

}
