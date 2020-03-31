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
import java.util.Scanner;

import util.*;

public class Client implements Runnable {

    public ObjectOutputStream outputStream;
    public Socket chatSocket;
    public ObjectInputStream inputStream;

    DataOutputStream toServer = null;
    DataInputStream fromServer = null;

    private int port = SocketSettings.getPort();
    public InetAddress host = InetAddress.getLocalHost();
    public ClientGUI clientGUI;

    public String username;
    public ObservableList<String> chatLog;
    public ObservableList<String> userList;
    private ClientViewController controller;

    private User currentuser;
    private ArrayList<User> allUsers;
    private Channel currentchannel;
    private ArrayList<Message> messages;
    private ArrayList<User> channelUsers;
    private ArrayList<Channel> userChannels;

    public Client() throws IOException {
        chatLog = FXCollections.observableArrayList();
        userList = FXCollections.observableArrayList();
        currentuser = null;
        currentchannel = null;
        userList.add("initial test");
        initialize();
    }
    public Client(String host, int port) throws IOException {
        this.host = InetAddress.getByName(host);
        this.port = port;
        chatLog = FXCollections.observableArrayList();
        userList = FXCollections.observableArrayList();
        currentuser = null;
        currentchannel = null;
        userList.add("initial test");
        initialize();
    }

    private void initialize() throws IOException {
        // connect to server
        BufferedReader configfile = new BufferedReader(new FileReader("config.txt"));
        configfile.readLine();
        String hostname = configfile.readLine();
        System.out.println("host " + hostname);
        configfile.close();
        System.out.println("Connecting to server...");
        try {
            host = InetAddress.getLocalHost();
            System.out.println(host);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.out.println("Unknown Host");
        }
        System.out.println("Connected to: " + host.getHostAddress());

        try {
            chatSocket = new Socket(hostname, port);
            chatSocket.setReuseAddress(true);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("error connecting");
        }
        outputStream = new ObjectOutputStream(chatSocket.getOutputStream());
        inputStream = new ObjectInputStream(chatSocket.getInputStream());
    }

    public User getCurrentUser() {
        return this.currentuser;
    }

    public Channel getCurrentChannel() {
        return this.currentchannel;
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
            System.out.println("HI");
            //outputStream.writeObject(loginput);
            //outputStream.flush();
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
            toServer = new DataOutputStream(chatSocket.getOutputStream());
            Input postmessages = new Input("postMessage");
            System.out.println(newmessage.getMessage());
            String m = newmessage.getMessage();
            System.out.println(m);
            outputStream.writeObject(postmessages);
            outputStream.flush();
            toServer.writeUTF(m);
            toServer.flush();


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String loadFile() throws IOException {

            BufferedReader configfile = new BufferedReader(new FileReader("config.txt"));
            String username = configfile.readLine();
            configfile.close();
            return username;

    }

    public String getMessage() throws IOException {
        fromServer = new DataInputStream(chatSocket.getInputStream());
        String finalmessage = fromServer.readUTF();
        return finalmessage;
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

    public void getChannelsForUser() {
        try {
            Input getchannels = new Input("getChannels");
            outputStream.writeObject(getchannels);
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
                //input = (Input)inputStream.readObject();
                if (input != null) {
                    System.out.println("received data");
                    switch (input.getOperation()) {
                        case "res-register":
                            this.currentuser = input.getUser();
                            System.out.println("received");
                            break;
                        case "res-login":
                            this.currentuser = input.getUser();

                            break;
                        case "res-getAllUsers":
                            this.allUsers = input.getUserList();
                            break;
                        case "res-getMessagesForChannel":
                            //update messages for a channel in channels here
                            this.messages = input.getMessageList();
                            break;
                        case "res-getUsersForChannel":
                            // use this userlist to display in the side bar
                            this.channelUsers = input.getUserList();
                            break;
                        case "res-getChannelsForUser":
                            // display channels on the side
                            this.userChannels = input.getChannelList();
                            break;
                        case "res-postMessage":

                            break;
                        case "res-addToChannel":
                            // add a user to channel
                            this.currentuser.addChannel(input.getChannel());
                            break;
                        case "res-removeFromChannel":
                            // remove a user from the channel
                            this.currentuser.delChannel(input.getChannel());
                            break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }
}

