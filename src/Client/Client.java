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
            Input allusers = new Input("postMessage");
            outputStream.writeObject(allusers);
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
                            break;
                        case "res-getMessagesForChannel":
                            break;
                        case "res-getUsersForChannel":
                            break;
                        case "res-postMessage":
                            break;
                        case "res-addToChannel":
                            break;
                        case "res-removeFromChannel":
                            break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }
}

