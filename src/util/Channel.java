package util;
import java.util.ArrayList;

public class Channel implements Serializable {
  private int id;
  private String name;
  private ArrayList<Message> messages;

  public Channel(String name) {
    this.name = name;
  }
  public Channel(int id, String name) {
    this.id = id;
    this.name = name;
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

  // message functions
  public ArrayList<Message> getMessages() {
    return this.messages;
  }

  public void addMessage(Message message) {
    this.messages.add(message);
  }
}
