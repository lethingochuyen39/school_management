package com.school.management.payload.response;

public class MessageResponse {

  private String message;

  public MessageResponse(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public class MessageResponseNotFoundException extends RuntimeException {
    public MessageResponseNotFoundException(String message) {
      super(message);
    }
  }
}
