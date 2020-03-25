package util;

import java.time.LocalDateTime;

public class Message {
  private LocalDateTime id;
  private User user;
  private Channel channel;
  private String message;

  public Message(User user, Channel channel, String message) {
    this.id = LocalDateTime.now();
    this.user = user;
    this.message = message;
  }

  // id functions

  public LocalDateTime getId() {
    return this.id;
  }

  public void setId(LocalDateTime id) {
    this.id = id;
  }

  // user functions

  public User getUser() {
    return this.user;
  }

  // message functions

  public String getMessage() {
    return this.message;
  }

  public void editMessage(String message) {
    this.message = message;
    this.id = LocalDateTime.now();
  }
}