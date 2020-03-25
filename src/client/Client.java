package client;

import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.time.Instant;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.net.InetAddress;
import javafx.collections.FXCollections;
import java.net.SocketException;
import java.net.UnknownHostException;

import javafx.application.Platform;


public class Client implements Runnable {

    public PrintWriter outputStream;
    public Socket chatSocket;
    public Scanner inputStream;
    public BufferedReader serverInput;
    private int port = 8080;
    public InetAddress host = InetAddress.getLocalHost();

    public String username;
    public ObservableList<String> chatLog;

    public Client() throws IOException {
        chatLog = FXCollections.observableArrayList();
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
        //handle input/output
        serverInput = new BufferedReader(new InputStreamReader(chatSocket.getInputStream(),"UTF-8"));
        outputStream = new PrintWriter(chatSocket.getOutputStream());

        //get username
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Username");
        username = scanner.next();
    }

    public void logout() throws IOException{
        chatSocket.close();
        System.exit(0);

        return;
    }

    //thread listens for server input and updates [observablelist chatlog] <- might get too big
    @Override
    public void run() {
        System.out.println("listening");
        while (true) {
            try {
                System.out.println("client thread running");
                String serverinput = serverInput.readLine();
                if(serverinput!=null) {
                    System.out.println(serverinput);
                    Platform.runLater(new Runnable() {
                        public void run() {
                            chatLog.add(serverinput);
                        }
                    });
                }
            } catch (SocketException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
