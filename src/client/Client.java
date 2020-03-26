package client;


import javafx.collections.ObservableList;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.net.InetAddress;
import javafx.collections.FXCollections;

import javafx.application.Platform;
import util.Input;
import javafx.stage.FileChooser;


public class Client implements Runnable {

    public ObjectOutputStream outputStream;
    public Socket chatSocket;
    public ObjectInputStream inputStream;
    private int port;
    private InetAddress host;

    public String username;
    public ObservableList<String> chatLog;
    public ObservableList<String> userList;
    private ClientViewController controller;

    public Client(String host, int port) throws IOException {
        this.port = port;
        this.host = InetAddress.getByName(host);
        chatLog = FXCollections.observableArrayList();
        userList = FXCollections.observableArrayList();
        userList.add("initial test");
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
    public void setController(ClientViewController controller){
        this.controller = controller;
    }

    public void sendString(String message) throws IOException {
        Input string = new Input();
        string.setType(Input.inputType.TEXT);
        string.setString(message);
        outputStream.writeObject(string);
        outputStream.flush();
        System.out.println("sent message");
    }
    public void displayString(Input input){
        Platform.runLater(() -> chatLog.add(input.getString()));
    }

    //assumes utf-8 encoding, size<16mb
    public Input writeFileToBytes(String filename) throws FileNotFoundException {
        Input input = new Input();
        File file = new File(filename);
        FileInputStream fis = new FileInputStream(file);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[16384];
        int n;

        try {
            while ((n = fis.read(buffer, 0, buffer.length)) != -1) {
                bos.write(buffer, 0, n);
                System.out.println("sent " + n + " bytes to output");
            }
        } catch (IOException ex) {
        }
        byte[] bytes = bos.toByteArray();
        input.setType(Input.inputType.FILE);
        input.setFile(bytes, file.getName());
        return input;
    }
    public void sendFile(File filename) throws IOException {
        outputStream.writeObject(writeFileToBytes(filename.getAbsolutePath()));
        outputStream.flush();
        System.out.println("sent file as byte array");
    }

    //assumes utf-8 encoding, size<16mb
    public void readFileFromBytes(Input input, File selectedFile) throws IOException {
        FileOutputStream fos = new FileOutputStream(selectedFile);
        fos.write(input.getByteArray());
        fos.flush();
        fos.close();
    }

    public void updateUserlist(Input input){
        userList.clear();
        ArrayList list = input.getUserlist();
        for(int i = 0; i < list.size(); i++)
        {
            System.out.println(list.get(i));
        };
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
                            break;
                        case USERLIST:
                            System.out.println("hello why is this running");
                            updateUserlist(input);
                            break;
                        case FILE:
                            System.out.println("received file "+input.getFilename());
                            Input finalInput = input;
                            //run on fx thread
                            Platform.runLater(() -> {
                                try {
                                    controller.receivedFile(finalInput);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            });
                            break;

                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                input = null;
            }
        }

    }
}