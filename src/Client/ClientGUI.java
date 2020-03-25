package chatter;

import java.io.*;
import java.net.Socket;
import javafx.application.Application;
import static javafx.application.Application.launch;

import javafx.application.Platform;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class ClientGUI extends Application {

    private Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private Client client;


    public ClientGUI() throws IOException {
        System.out.println("log");

        client = new Client();
        Thread t = new Thread(client);
        t.start();
        System.out.println("log");

        this.socket = client.chatSocket;
        this.outputStream = client.outputStream;
        this.inputStream = client.inputStream;
        System.out.println("log");
    }
    @Override
    public void stop() throws IOException {
        System.out.println("Stage is closing");
        outputStream.close();
        inputStream.close();
        System.exit(0);
    }


    @Override
    public void start(Stage primaryStage) throws IOException {
        new FileOutputStream("test.txt", true).close();

        client.sendFile("test.txt");
        System.out.println("log");

       ListView<String> chatlog = new ListView<>(client.chatLog);
        TextField messageField = new TextField();
        messageField.setPromptText("Type...");
        ListView<String> userlist = new ListView<>(client.userList);


        Button send = new Button("Send");
        send.setDefaultButton(true);
        send.setOnAction((ev) -> {
            String text = messageField.getText();
            System.out.println(messageField.getText());
            try {
                client.sendString(text);
            } catch (IOException e) {
                e.printStackTrace();
            }
            messageField.clear();
            System.out.println("Sent: " + text);
        });

        HBox hbox = new HBox(userlist, chatlog, messageField, send);
        VBox vbox = new VBox(hbox);
        Scene scene = new Scene(vbox);
        System.out.println("log");

        primaryStage.setTitle("chatter");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        launch(args);
    }

}