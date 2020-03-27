package util;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {
  private Date id;
  private User user;
  private Channel channel;
  private String message;
  private byte[] bytes;

  public Message(User user, Channel channel, String message) {
    this.user = user;
    this.channel = channel;
    this.message = message;
  }

  public Message(User user, Channel channel, byte[] bytes) {
    this.user = user;
    this.channel = channel;
    this.bytes = bytes;
  }

  public Message(Date id, User user, Channel channel, String message) {
    this.id = id;
    this.user = user;
    this.channel = channel;
    this.message = message;
  }

  // id functions

  public Date getId() {
    return this.id;
  }

  public void setId(Date id) {
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

  // bytes functions
  public byte[] getBytes() {
    return this.bytes;
  }

}
