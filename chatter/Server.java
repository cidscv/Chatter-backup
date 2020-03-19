package chatter;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;

public class Server {

    private final int port = 8080;
    private ServerSocket socket;
    private ArrayList<Socket> clientList;

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
        System.out.println("chat server started on "+ serverAddress());
        System.out.println("listening on port "+port);
        clientList = new ArrayList<>();
        //continuously listen for client connections
        while(true)
        {
            Socket client = socket.accept();
            clientList.add(client);
            System.out.println("New client: " + client.getRemoteSocketAddress());
            System.out.println("Total clients: " + clientList.size());
            //start thread for new client
            ClientHandler handler = new ClientHandler(client,this);
            Thread t = new Thread(handler);
            t.start();
        }
    }

    //handler for output stream to console, for other applications handle outputstream directly
    public synchronized void sendChatMessageToAll(String msg) throws IOException {
        Iterator<Socket> clientlist=clientList.iterator();
        while(clientlist.hasNext())
        {
            Socket client = clientlist.next();
            if( !client.isClosed() )
            {
                PrintWriter outputStream = new PrintWriter(client.getOutputStream(),true);
                outputStream.println(msg);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new Server().startServer();
    }

}