package client;

import utils.Input;

import javafx.application.Application;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;


public class ClientGUI extends Application {

    private Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private Client client;

    public ClientGUI() throws IOException {
        System.out.println("log");

        client = new Client();
        Thread t = new Thread(client);
        t.setDaemon(true);
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


        System.out.println("loading fxml");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ClientView.fxml"));
        System.out.println("log");

        Parent root = loader.load();
        System.out.println("log");

        ClientViewController controller = (ClientViewController) loader.getController();
        controller.setClient(client);
        controller.setChatView();
        client.setController(controller);
        System.out.println("log");

        primaryStage.setTitle("chatter/v1-1");
        primaryStage.setScene(new Scene(root, 300, 275,Color.TRANSPARENT));
        primaryStage.setMinWidth(1080);
        primaryStage.setMinHeight(720);
        primaryStage.show();
    }
    private HBox createTextInput() {
        HBox textArea = new HBox();

        TextField userText = new TextField();
        userText.setPromptText("Enter message");
        userText.setPrefSize(500,50);
        userText.setMinSize(100,50);
        userText.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                try {
                    sendText(userText.getText());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                userText.clear();
            }
        });


        Button sendButton = new Button("Send");
        sendButton.setMinSize(100,50);
        sendButton.setMaxSize(100,50);
        sendButton.setOnAction(e ->
        {
            try {
                sendText(userText.getText());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            userText.clear();
        });

        HBox.setHgrow(userText, Priority.ALWAYS);
        textArea.getChildren().addAll(userText, sendButton);

        return textArea;
    }
    private void logout() throws IOException {
        System.out.println("Stage is closing");
        outputStream.close();
        inputStream.close();
        Platform.exit();
        System.exit(0);
    }

    private HBox createStatusBar(){
        HBox statusBar = new HBox(0);
        statusBar.maxHeight(50);

        Button btLogout = new Button("Log out");
        btLogout.setMinWidth(80);

        btLogout.setOnMouseClicked(e -> {
            try {
                logout();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        statusBar.getChildren().add(btLogout);
        return statusBar;

    }

    private void sendText(String text) throws IOException {
        client.sendString(text);
        return;
    }


}