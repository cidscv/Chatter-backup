package chatter;


import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.time.Instant;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.net.InetAddress;

public class Client implements Runnable {

    private Socket chatSocket;
    private Scanner inputStream;
    private int port = 8080;
    InetAddress host = InetAddress.getLocalHost();

    public Client() throws IOException {
        initialize();
    }

    private void initialize() throws IOException {
        // connect to server
        System.out.println("Connecting to server...");
        try {
            host = InetAddress.getLocalHost();
        } catch (UnknownHostException e1) {
            e1.printStackTrace();
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
        inputStream = new Scanner(chatSocket.getInputStream());
        PrintWriter outputStream = new PrintWriter(chatSocket.getOutputStream());

        //get username
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Username");
        String username = scanner.next();

        //new to listen to server input
        Thread t = new Thread(this);
        t.start();
        // listen to user input
        while (scanner.hasNextLine()) {
            String msg = scanner.nextLine();
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            outputStream.println("["+timestamp+"]"+username+": "+msg);
            outputStream.flush();
        }
    }

    public static void main(String[] args) throws Exception {
        new Client();
    }

    @Override
    public void run() {
        while (true) {
            if (inputStream.hasNextLine())
                System.out.println(inputStream.nextLine());
        }
    }
}