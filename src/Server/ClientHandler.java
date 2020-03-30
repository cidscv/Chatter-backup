package Server;

import javafx.application.Platform;
import util.*;

import java.io.*;

import java.util.ArrayList;

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

    public Socket getClient() { return this.client; }

    @Override
    public void run() {
        /*try {
            server.updateClientlist();
        } catch (IOException e) {
            System.out.println("failed update clientlist");
            e.printStackTrace();
        }*/

        while (client.isConnected()) {
            System.out.println("listening");
            Input input = null;
            try {
                DataInputStream inputFromClient = new DataInputStream(client.getInputStream());
                DataOutputStream outputToClient = new DataOutputStream(client.getOutputStream());
                input = (Input)inputStream.readObject();
                if (input != null) {
                    System.out.println("received data");
                    switch (input.getOperation()) {
                        /*
                        case "register":
                            try {
                                User u = dh.createUser(input.getUser());
                                Input res = new Input("res-register");
                                res.setUser(u);
                                this.outputStream.writeObject(res);
                            } catch(Exception e) {
                                e.printStackTrace();
                            }
                            break;
                        case "login":
                            try {
                                User u = dh.getUser(input.getUser().getId());
                                Input res = new Input("res-login");
                                res.setUser(u);
                                this.outputStream.writeObject(res);
                            } catch(Exception e) {
                                e.printStackTrace();
                            }
                            break;
                        case "getAllUsers":
                            try {
                                ArrayList<User> u = dh.getAllUsers();
                                Input res = new Input("res-getAllUsers");
                                res.setUserList(u);
                                this.outputStream.writeObject(res);
                            } catch(Exception e) {
                                e.printStackTrace();
                            }
                            break;
                        case "getMessagesForChannel":
                            try {
                                ArrayList<Message> m = dh.getMessagesForChannel(input.getChannel().getId());
                                Input res = new Input("res-getMessagesForChannel");
                                res.setMessageList(m);
                                this.outputStream.writeObject(res);
                            } catch(Exception e) {
                                e.printStackTrace();
                            }
                            break;
                        case "getUsersForChannel":
                            try {
                                ArrayList<User> u = dh.getUsersForChannel(input.getChannel().getId());
                                Input res = new Input("res-getUsersForChannel");
                                res.setUserList(u);
                                this.outputStream.writeObject(res);
                            } catch(Exception e) {
                                e.printStackTrace();
                            }
                            break;
                        case "getChannelsForUser":
                            try {
                                ArrayList<Channel> c = dh.getChannelsForUser(input.getUser().getId());
                                Input res = new Input("res-getChannelsForUser");
                                res.setChannelList(c);
                                this.outputStream.writeObject(res);
                            } catch(Exception e) {
                                e.printStackTrace();
                            }
                            break;

                         */
                        case "postMessage":
                            // TODO figure out how to send message to everyone
                            try {
                                System.out.println("WORKING");
                                Message m = input.getMessage();
                                String messge = inputFromClient.readUTF();

                                System.out.println(messge);
                                outputToClient.writeUTF(messge);
                                Input res = new Input("res-postMessage");

                                res.setMessage(m);
                                this.outputStream.writeObject(res);
                            } catch(Exception e) {
                                e.printStackTrace();
                            }
                            break;
                            /*
                        case "addToChannel":
                            try {
                                User u = dh.addToChannel(input.getUser(), input.getChannel());
                                Input res = new Input("res-addToChannel");
                                res.setUser(u);
                                this.outputStream.writeObject(res);
                            } catch(Exception e) {
                                e.printStackTrace();
                            }
                            break;
                        case "removeFromChannel":
                            try {
                                User u = dh.removeFromChannel(input.getUser(), input.getChannel());
                                Input res = new Input("res-removeFromChannel");
                                res.setUser(u);
                                this.outputStream.writeObject(res);
                            } catch(Exception e) {
                                e.printStackTrace();
                            }
                            break;

                             */
                    }
                }
                //means the socket is closed PROBABLY OR THERE'S SOME OTHER ERROR ಥ_ಥ
            } catch (IOException | ClassNotFoundException e) {
                System.out.println(client+": connection closed");

                //terminate thread
                return;
            }
        }
    }


}
