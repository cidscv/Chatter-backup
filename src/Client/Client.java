package Client;


import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import javafx.collections.FXCollections;
import javafx.application.Platform;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.net.InetAddress;

import java.util.ArrayList;
import util.*;




public class Client implements Runnable {

    public ObjectOutputStream outputStream;
    public Socket chatSocket;
    public ObjectInputStream inputStream;

    private int port = SocketSettings.getPort();
    public InetAddress host = InetAddress.getLocalHost();
    public ClientGUI clientGUI;

    public String username;
    public ObservableList<String> chatLog;
    public ObservableList<String> userList;
    private ClientViewController controller;

    public User currentuser;


    public Client() throws IOException {
        chatLog = FXCollections.observableArrayList();
        userList = FXCollections.observableArrayList();
        currentuser = null;
        userList.add("initial test");
        initialize();
    }

    private void initialize() throws IOException {
        // connect to server
        System.out.println("Connecting to server...");
        try {
            host = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.out.println("Unknown Host");
        }
        System.out.println("Connected to: " + host.getHostAddress());

        try {
            chatSocket = new Socket(host, port);
            chatSocket.setReuseAddress(true);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("error connecting");
        }
        outputStream = new ObjectOutputStream(chatSocket.getOutputStream());
        inputStream = new ObjectInputStream(chatSocket.getInputStream());
    }
    public void setController(ClientViewController controller){
        this.controller = controller;
    }

    public void register(Input reginput) {
        try {
            outputStream.writeObject(reginput);
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void login(Input loginput) {
        try {
            outputStream.writeObject(loginput);
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getAllUsers() {
        try {
            Input allusers = new Input("getAllUsers");
            outputStream.writeObject(allusers);
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(Message newmessage) {
        try {
            Input postmessages = new Input("postMessage");
            outputStream.writeObject(postmessages);
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getMessagesForChannel() {
        try {
            Input getmessages = new Input("getMessages");
            outputStream.writeObject(getmessages);
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getUsersForChannel() {
        try {
            Input getusers = new Input("getUsers");
            outputStream.writeObject(getusers);
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addToChannel() {
        try {
            Input addtochannel = new Input("addToChannel");
            outputStream.writeObject(addtochannel);
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeFromChannel() {
        try {
            Input removefromchannel = new Input("removeFromChannel");
            outputStream.writeObject(removefromchannel);
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        System.out.println("listening");
        while (true) {
            //update rate i crashed my computer hey
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Input input=null;
            try {
                input = (Input)inputStream.readObject();
                if (input != null) {
                    System.out.println("received data");
                    switch (input.getOperation()) {
                        case "res-register":
                            this.currentuser = input.getUser();
                            break;
                        case "res-login":
                            this.currentuser = input.getUser();
                            break;
                        case "res-getAllUsers":
                            // use this user list to add a user to a channel
                            break;
                        case "res-getMessagesForChannel":
                            //update messages for a channel in channels here
                            break;
                        case "res-getUsersForChannel":
                            // use this userlist to display in the side bar
                            break;
                        case "res-getChannelsForUser":
                            // display channels on the side
                            break;
                        case "res-postMessage":
                            // send a message/file
                            break;
                        case "res-addToChannel":
                            // add a user to channel
                            break;
                        case "res-removeFromChannel":
                            // remove a user from the channel
                            break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }
}

