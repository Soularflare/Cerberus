package com.project.cerberus.jumble.util;

/**
 * An interface for reporting user-readable information.
 * Created by andrew on 12/07/14.
 */
public interface JumbleLogger {
    void logInfo(String message);
    void logWarning(String message);
    void logError(String message);
}