package Client;
import util.Input;

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
    public void start(Stage primaryStage) throws Exception {
        VBox vBox = new VBox();
        MenuBar menuBar = createMenuBar();
        Separator separator = new Separator();
        HBox bottomBar = createStatusBar();

        client.sendFile("test.txt");
        System.out.println("log");

        ListView<String> chatlog = new ListView<>(client.chatLog);
        ListView<String> userlist = new ListView<>(client.userList);
        // work here
        SplitPane mainPane = createMainPane();
        vBox.setVgrow(mainPane, Priority.ALWAYS);
        vBox.getChildren().addAll(menuBar, mainPane, bottomBar);

        Scene scene = new Scene(vBox, 900, 600);
        primaryStage.setTitle("Instance for " + client.username);
        primaryStage.setScene(scene);
        primaryStage.setMinHeight(scene.getHeight());
        primaryStage.setMinWidth(scene.getWidth());
        primaryStage.show();

    }

    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();
        menuBar.setStyle("-fx-background-color: midnightblue;" + "-fx-border-color: dimgrey;" + "-fx-border-width: 2");

        Menu fileMenu = new Menu("File");
        fileMenu.setStyle("-fx-background-color: blanchedalmond");

        MenuItem saveChatLog = new MenuItem("Get chat log");

        fileMenu.getItems().addAll(saveChatLog);

        menuBar.getMenus().addAll(fileMenu);

        return menuBar;
    }

    private SplitPane createMainPane() {
        SplitPane mainPane = new SplitPane();

        VBox leftPane = createChannelList();
        VBox middlePane = createChatPane();
        ScrollPane rightPane = createUserList();

        // Pane adjustments
        mainPane.setDividerPosition(0,0.1f);
        mainPane.setDividerPosition(1,0.8f);
        leftPane.setMinWidth(150);
        leftPane.setMaxWidth(150);
        rightPane.setMinWidth(220);
        rightPane.setMaxWidth(220);


        mainPane.getItems().addAll(leftPane, middlePane, rightPane);

        return mainPane;
    }

    private VBox createChannelList() {
        VBox channelList = new VBox(10);
        channelList.setStyle("-fx-background-color: #545454;" + "-fx-border-color: black;" + "-fx-border-width: 1");
        channelList.setAlignment(Pos.TOP_CENTER);

        for (int i = 0; i < 15; i++) {
            if (i % 2 == 0) {
                Button bt = new Button("test");
                bt.setMaxWidth(135);
                bt.setStyle("-fx-background-color: linear-gradient(darkgrey,#999999, grey, #707070,darkgrey);" + "-fx-border-radius: 0;");
                channelList.getChildren().add(bt);
            } else {
                Separator sp = new Separator();
                sp.setStyle("-fx-fill: #fff");
                //channelList.getChildren().add(new Separator());
            }

        }

        return channelList;
    }

    private VBox createChatPane() {
        VBox chatPane = new VBox();

        ListView<String> chatLog = new ListView<String>();
        chatLog.setItems(client.chatLog);

        ScrollPane chatArea = new ScrollPane(chatLog);
        chatArea.setFitToHeight(true);
        chatArea.setFitToWidth(true);

        HBox chatInput = createTextInput();
        chatInput.setMaxHeight(100);

        VBox.setVgrow(chatArea, Priority.ALWAYS);
        chatPane.getChildren().addAll(chatArea, chatInput);

        return chatPane;
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

    private HBox createUser()
    {
        HBox userBar = new HBox();
        userBar.setStyle("-fx-padding: 4;" + "-fx-border-color: black;");
        userBar.setSpacing(7);
        userBar.setAlignment(Pos.CENTER_LEFT);


        Image userImage = new Image(new File("src/Images/pic.png").toURI().toString());

        Circle border = new Circle(20);
        border.setFill(new ImagePattern(userImage));
        border.setStroke(Color.BLACK);

        Text userName = new Text("Username");
        userName.setFont(Font.font("Verdana", 22));

        userName.setFill(Color.BLACK);


        userBar.getChildren().add(border);
        userBar.getChildren().add(userName);


        return userBar;
    }

    private ScrollPane createUserList()
    {
        VBox userList = new VBox();
        ScrollPane scrollList = new ScrollPane();


        userList.setStyle("-fx-background-color: blanchedalmond;" + "-fx-border-color: dimgrey;" + "-fx-border-width: 2");

        userList.getChildren().add(createUser());
        userList.getChildren().add(createUser());
        userList.getChildren().add(createUser());
        userList.getChildren().add(createUser());
        userList.getChildren().add(createUser());
        userList.getChildren().add(createUser());
        userList.getChildren().add(createUser());
        userList.getChildren().add(createUser());
        userList.getChildren().add(createUser());
        userList.getChildren().add(createUser());
        userList.getChildren().add(createUser());
        userList.getChildren().add(createUser());
        userList.getChildren().add(createUser());

        scrollList.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        scrollList.setContent(userList);
        scrollList.setFitToHeight(true);
        scrollList.setFitToWidth(true);


        return scrollList;
    }

}
