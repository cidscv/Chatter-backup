package Server;

import javafx.application.Platform;
import util.*;

import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;


public class Server {

    private final int port = SocketSettings.getPort();
    private ServerSocket socket;
    private ArrayList<ClientHandler> clientList;
    private ObjectOutputStream outputStream;

    public Server(){
        try {
            socket = new ServerSocket(port);
            socket.setReuseAddress(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String serverAddress() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress();
    }

    public void startServer() throws IOException {
        /*
        System.out.println("chat server started on "+ serverAddress());
        System.out.println("listening on port "+port);
        clientList = new ArrayList<ClientHandler>();
        //continuously listen for client connections
        while(true){
            Socket client = socket.accept();
            System.out.println("New client: " + client.getRemoteSocketAddress());
            //start thread for new client
            ClientHandler handler = new ClientHandler(client,this);
            clientList.add(handler);
            Thread t = new Thread(handler);
            t.start();
            //updateClientlist();
            System.out.println("Total clients: " + clientList.size());
        }*/
        new Thread( () -> {
            try {

                System.out.println("MultiThreadServer started at " + new Date() + '\n');

                while (true) {

                    Socket client = socket.accept();

                    System.out.println("Starting thread for client " + client.getRemoteSocketAddress() + " at " + new Date() + '\n');

                    InetAddress inetAddress = socket.getInetAddress();
                    System.out.println("Client " + client.getRemoteSocketAddress() + "'s host name is " + inetAddress.getHostName() + "\n");
                    System.out.println("Client " + client.getRemoteSocketAddress() + "'s IP Address is " + inetAddress.getHostAddress() + "\n");



                    new Thread(new ClientHandler(client, this)).start();
                }
            }
            catch(IOException ex) {
                System.err.println(ex);
            }
        }).start();
    }
    public void updateClientlist(ClientHandler handler) throws IOException {
        clientList.remove(handler);
        pushUserList();
    }
    public void updateClientlist() throws IOException {
        pushUserList();
    }

    public void pushUserList() throws IOException {
        System.out.println("pushing userlist");
        ArrayList<String> userList = new ArrayList<String>();

        Iterator<ClientHandler> clientlist=clientList.iterator();
        do{
            ClientHandler handler = clientlist.next();
            userList.add(handler.getClient().toString());
        }while(clientlist.hasNext());
        if (userList!=null){
            Input input = new Input("res-usersOnline");
            pushInput(input);}
        else {
            System.out.print("userlist is null");
        }
    }

    //handler for output stream CALL THIS FOR EVERYTHING
    public synchronized void pushInput(Input input) throws IOException {
        Iterator<ClientHandler> clientlist=clientList.iterator();
        while(clientlist.hasNext())
        {
            ClientHandler handler = clientlist.next();
            if( !handler.client.isClosed() )
            {
                outputStream = handler.getOOS();
                System.out.println("pushing message");
                outputStream.writeObject(input);
                outputStream.reset();
                outputStream.flush();
                System.out.println("pushed message");
            }
        }
    }

     public static void main(String[] args) throws IOException {
            new Server().startServer();
    }

}
