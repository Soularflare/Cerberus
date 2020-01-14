package com.project.cerberus.jumble.exception;

public class NativeAudioException extends AudioException {
    public NativeAudioException(String message) {
        super(message);
    }

    public NativeAudioException(Throwable throwable) {
        super(throwable);
    }

    public NativeAudioException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
