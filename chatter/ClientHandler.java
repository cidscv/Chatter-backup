package chatter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler implements Runnable {

    public Socket client;
    private Server server;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    public ClientHandler(Socket client, Server server) throws IOException {
        this.client = client;
        this.server = server;
        outputStream = new ObjectOutputStream(client.getOutputStream());
        inputStream = new ObjectInputStream(client.getInputStream());
    }
    public ObjectOutputStream getOOS(){
        return this.outputStream;
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("listening");
            Input input =null;
            try {
                input = (Input) inputStream.readObject();
                if (input != null) {
                    System.out.println("received msg");
                    switch (input.getType()) {
                        case TEXT:
                            server.pushInput(input);
                            System.out.println(client.getRemoteSocketAddress() + input.getString());
                            input = null;
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
