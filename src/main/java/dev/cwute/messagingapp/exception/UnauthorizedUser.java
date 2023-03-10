package dev.cwute.messagingapp.exception;

public class UnauthorizedUser extends RuntimeException {
  public UnauthorizedUser(String message) {
    super(message);
  }
}
