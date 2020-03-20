package handlers;

import java.time.LocalDateTime;

public class Message {
  private int id;
  private User user;
  private Channel channel;
  private String message;
  private LocalDateTime time;

  public Message(User user, Channel channel, String message) {
    this.user = user;
    this.channel = channel;
    this.message = message;
    this.time = LocalDateTime.now();
  }

  // id functions

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  // user functions

  public User getUser() {
    return this.user;
  }

  // channel functions

  public Channel getChannel() {
    return this.channel;
  }

  // message functions

  public String getMessage() {
    return this.message;
  }

  public void editMessage(String message) {
    this.message = message;
    this.time = LocalDateTime.now();
  }

  // time functions

  public LocalDateTime getTime() {
    return this.time;
  }

}
