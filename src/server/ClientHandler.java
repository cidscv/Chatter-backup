package Server;

import util.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.Socket;

public class ClientHandler implements Runnable {

    public Socket client;
    private Server server;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    public ClientHandler(Socket client, Server server) throws IOException {
        this.client = client;
        this.server = server;
        this.outputStream = new ObjectOutputStream(client.getOutputStream());
        this.inputStream = new ObjectInputStream(client.getInputStream());
    }
    public ObjectOutputStream getOOS(){
        return this.outputStream;
    }

    @Override
    public void run() {
        try {
            server.updateClientlist();
        } catch (IOException e) {
            System.out.println("failed update clientlist");
            e.printStackTrace();
        }

        while (client.isConnected()) {
            System.out.println("listening");
            Input input = null;
            DataHandler dh = new DataHandler();
            try {
                input = (Input)inputStream.readObject();
                dh =
                if (input != null) {
                    System.out.println("received data");
                    switch (input.getOperation()) {
                        case "register":
                            break;
                        case "login":
                            break;
                        case "getAllUsers":
                            break;
                        case "getMessagesForChannel":
                            break;
                        case "getUsersForChannel":
                            break;
                        case "postMessage":
                            break;
                        case "addToChannel":
                            break;
                        case "removeFromChannel":
                            break;
                    }
                }
                //means the socket is closed PROBABLY OR THERE'S SOME OTHER ERROR ಥ_ಥ
            } catch (IOException | ClassNotFoundException e) {
                System.out.println(client+": connection closed");
                try {
                    server.updateClientlist(this);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                //terminate thread
                return;
            }
        }
    }


}
