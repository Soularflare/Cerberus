package com.project.cerberus.jumble.exception;

public class AudioInitializationException extends AudioException {
    public AudioInitializationException(String message) {
        super(message);
    }

    public AudioInitializationException(Throwable throwable) {
        super(throwable);
    }

    public AudioInitializationException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
