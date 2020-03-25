package util;
import java.util.ArrayList;

public class Channel {
  private int id;
  private String name;

  public Channel(String name) {
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

}
