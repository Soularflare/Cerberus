package com.project.cerberus.jumble.audio;

public class InvalidSampleRateException extends Exception {
    public InvalidSampleRateException(Exception e) {
        super(e);
    }

    public InvalidSampleRateException(String message) {
        super(message);
    }
}
