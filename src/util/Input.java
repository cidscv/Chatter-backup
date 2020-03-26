package util;
import java.io.*;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.*;


public class Input implements Serializable {
    public inputType type;
    private String text;
    private ArrayList<String> userlist;
    private byte[] bytes;
    private String filename;


    public enum inputType {
        FILE, IMAGE, TEXT, USERLIST
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
    public ArrayList<String> getUserlist(){
        return userlist;
    }
    public byte[] getByteArray(){
        return bytes;
    }
    public String getFilename(){
        return filename;
    }
    public void setFile(byte[] bytes, String filename){
        this.bytes=bytes;
        this.filename=filename;
    }

    public void setUserlist(ArrayList<String> userlist){
        userlist.add("userlist is null");
        if(userlist!=null){
        this.userlist = userlist;}
    }
    public void setString(String msg) {
        this.text = msg;
    }
}
