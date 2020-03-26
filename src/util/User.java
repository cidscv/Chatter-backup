package util;
import java.util.ArrayList;
import org.mindrot.jbcrypt.*;

public class User {
  private int id;
  private String username;
  private String password;
  private ArrayList<Channel> channels;

  public User(String username, String password) {
    this.username = username;
    setPassword(password);
    this.channels = new ArrayList<Channel>();
  }

  public User(int id, String username, String password, ArrayList<Channel> channels) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.channels = channels;
  }

  // id functions

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  // username functions

  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  // password functions (need to be hashed)
  public String getHash() { return this.password; }

  public void setPassword(String password) {
    this.password = BCrypt.hashpw(password, BCrypt.gensalt(10));
  }

  public boolean verifyPassword(String candidate) {
    return BCrypt.checkpw(candidate, this.password);
  }

  // channel functions

  public ArrayList<Channel> getChannels() {
    return this.channels;
  }

  public void addChannel(Channel channel) {
    this.channels.add(channel);
  }

  public void delChannel(Channel channel) {
    this.channels.removeIf(c -> {
      return channel.getId() == c.getId();
    });
  }
}
