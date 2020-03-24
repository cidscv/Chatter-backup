package chatter;

import java.io.Serializable;

public class Input implements Serializable {
    public inputType type;
    private String text;


    public enum inputType {
        FILE, IMAGE, TEXT
    }
    public void setType(inputType type){
        this.type = type;
    }
    public inputType getType() {
        return type;
    }
    public String getString() {
        return text;
    }

    public void setString(String msg) {
        this.text = msg;
    }
}
