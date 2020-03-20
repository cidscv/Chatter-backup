package handlers;
import java.util.ArrayList;

public class Channel {
  private int id;
  private String name;
  private ArrayList<User> users;
  private ArrayList<User> admins;

  public Channel(int id, String name, User creator) {
    this.id = id;
    this.name = name;
    this.users = new ArrayList<User>();
    this.users.add(creator);
    this.admins.add(creator);
  }

  // id functions

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  // name functions

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  // user functions

  public ArrayList<User> getUsers() {
    return this.users;
  }

  public void addUser(User user) {
    this.users.add(user);
    user.userAddChannel(this);
  }

  public void delUser(User user) {
    this.users.removeIf(u -> {
      return user.getId() == u.getId();
    });
    user.userDelChannel(this);
  }

  // admin functions

  public ArrayList<User> getAdmins() {
    return this.admins;
  }

  public void addAdmin(User user) {
    this.admins.add(user);
    user.adminAddChannel(this);
  }

  public void delAdmin(User user) {
    this.admins.removeIf(u -> {
      return user.getId() == u.getId();
    });
    user.adminDelChannel(this);
  }

  // not to be called outside of User Class

  protected void channelAddUser(User user) {
    this.users.add(user);
  }

  protected void channelAddAdmin(User user) {
    this.users.add(user);
  }

  protected void channelDelUser(User user) {
    this.users.removeIf(u -> {
      return user.getId() == u.getId();
    });
  }

  protected void channelDelAdmin(User user) {
    this.users.removeIf(u -> {
      return user.getId() == u.getId();
    });
  }

}
