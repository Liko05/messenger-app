package dev.cwute.messagingapp.exception;

/** This exception is thrown when a user is not authorized to perform an action. */
public class UnauthorizedUser extends RuntimeException {
  public UnauthorizedUser(String message) {
    super(message);
  }
}
