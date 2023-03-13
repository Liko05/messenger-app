package dev.cwute.messagingapp.exception;

/**
 * Exception thrown when a user is not found.
 */
public class UserNotFound extends RuntimeException {
    public UserNotFound(String message) {
        super(message);
    }
}
