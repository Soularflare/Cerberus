package com.project.cerberus.jumble.exception;

public class AudioException extends Exception {
    public AudioException(String message) {
        super(message);
    }

    public AudioException(Throwable throwable) {
        super(throwable);
    }

    public AudioException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
