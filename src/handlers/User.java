package handlers;
import java.util.ArrayList;

public class User {
  private int id;
  private String username;
  private String password;
  private ArrayList<Channel> channels;
  private ArrayList<Channel> adminChannels;

  public User(String username, String password) {
    this.username = username;
    this.password = password;
    this.channels = new ArrayList<Channel>();
    this.adminChannels = new ArrayList<Channel>();
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

  // password functions

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    // todo hash the password (currently not protected)
    this.password = password;
  }

  // channel functions

  public ArrayList<Channel> getChannels() {
    return this.channels;
  }

  public void addChannel(Channel channel) {
    this.channels.add(channel);
    channel.channelAddUser(this);
  }

  public void delChannel(Channel channel) {
    this.channels.removeIf(c -> {
      return channel.getId() == c.getId();
    });
    channel.channelDelUser(this);
  }
  // admin channel functions

  public ArrayList<Channel> getAdminChannels() {
    return this.channels;
  }

  public void addAdminChannel(Channel channel) {
    this.channels.add(channel);
    channel.channelAddAdmin(this);
  }

  public void delAdminChannel(Channel channel) {
    this.channels.removeIf(c -> {
      return channel.getId() == c.getId();
    });
    channel.channelDelAdmin(this);
  }

  // not to be called outside of Channel Class

  protected void userAddChannel(Channel channel) {
    this.channels.add(channel);
  }

  protected void adminAddChannel(Channel channel) {
    this.adminChannels.add(channel);
  }

  protected void userDelChannel(Channel channel) {
    this.channels.removeIf(c -> {
      return channel.getId() == c.getId();
    });
  }

  protected void adminDelChannel(Channel channel) {
    this.channels.removeIf(c -> {
      return channel.getId() == c.getId();
    });
  }

}
