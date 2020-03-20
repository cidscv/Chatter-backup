package client;

import java.io.*;
import java.net.Socket;
import javafx.application.Application;
import static javafx.application.Application.launch;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class ClientGUI extends Application {

    private Socket socket;
    private BufferedReader inputStream;
    private PrintWriter outputStream;
    private Client client = new Client();
    public ListView listView = new ListView();


    public ClientGUI() throws IOException {
        Thread t = new Thread(client);
        t.setDaemon(true);
        t.start();
        this.socket = client.chatSocket;
        this.outputStream = client.outputStream;
        this.inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        ListView listView = new ListView();

        listView.getItems().add("Item 1");
        listView.getItems().add("Item 2");
        listView.getItems().add("Item 3");
        listView.getItems().add(client.chatLog);
        HBox h1 = new HBox(listView);

        ListView<String> chatlog = new ListView<String>();
        chatlog.getItems().add("Item 1");

        chatlog.setItems(client.chatLog);

        TextField messageField = new TextField();
        messageField.setPromptText("Type...");

        Button send = new Button("Send");


        send.setOnAction((ev) -> {
            TextArea msg = new TextArea(messageField.getText());
            messageField.clear();

            String message = msg.getText();
            outputStream.write(message+"\n");
            outputStream.flush();
            System.out.println("Sent: " + msg.getText());
        });

        HBox hbox = new HBox(messageField, send);
        VBox vbox = new VBox(h1, chatlog,hbox);
        Scene scene = new Scene(vbox, 300, 300);

        primaryStage.setTitle("Chat App");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        launch(args);
    }

}
