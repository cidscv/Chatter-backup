package chatter;


import javafx.collections.ObservableList;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Scanner;
import java.net.InetAddress;
import javafx.collections.FXCollections;
import java.net.SocketException;
import java.net.UnknownHostException;

import javafx.application.Platform;


public class Client implements Runnable {

    public ObjectOutputStream outputStream;
    public Socket chatSocket;
    public ObjectInputStream inputStream;
    private int port = 8080;
    public InetAddress host = InetAddress.getLocalHost();
    public ClientGUI clientGUI;

    public String username;
    public ObservableList<String> chatLog;

    public Client() throws IOException {
        chatLog = FXCollections.observableArrayList();
        //clientGUI =
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
    public void sendString(String message) throws IOException {
        Input string = new Input();
        string.setType(Input.inputType.TEXT);
        string.setString(message);
        outputStream.writeObject(string);
        outputStream.flush();
        System.out.println("sent");
    }
    public void displayString(Input input){
        Platform.runLater(() -> chatLog.add(input.getString()));

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
                input = (Input) inputStream.readObject();
                if(input!=null) {
                    switch (input.getType()) {
                        case TEXT:
                            displayString(input);
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                input = null;
            }
        }

    }
}