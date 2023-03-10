package dev.cwute.messagingapp.exception;

public class MessageNotFound extends RuntimeException {
  public MessageNotFound(String message) {
    super(message);
  }
}
