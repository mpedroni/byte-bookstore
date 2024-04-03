package com.mpedroni.bytebookstore.localization.state.exceptions;

public class StateAlreadyExists extends RuntimeException {
    public StateAlreadyExists(String message) {
        super(message);
    }
}
