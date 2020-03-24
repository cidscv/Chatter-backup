package client;

import javafx.application.Application;

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
    private BufferedReader inputStream;
    private PrintWriter outputStream;
    private Client client = new Client();

    public ClientGUI() throws IOException {
        Thread t = new Thread(client);
        t.setDaemon(true);
        t.start();
        this.socket = client.chatSocket;
        this.outputStream = client.outputStream;
        this.inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox vBox = new VBox();

        MenuBar menuBar = createMenuBar();
        Separator separator = new Separator();

        HBox bottomBar = getStatusBar();

        // work here
        SplitPane mainPane = createMainPane();

        vBox.setVgrow(mainPane, Priority.ALWAYS);
        vBox.getChildren().addAll(menuBar, separator, mainPane, bottomBar);

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
        VBox channelList = new VBox();
        channelList.setStyle("-fx-background-color: blanchedalmond;" + "-fx-border-color: dimgrey;" + "-fx-border-width: 2");

        Button btTest1 = new Button("test");
        btTest1.setMaxWidth(150);


        Button btTest2 = new Button("test");
        btTest2.setMaxWidth(150);
        Button btTest3 = new Button("test");
        btTest3.setMaxWidth(150);
        Button btTest4 = new Button("test");
        btTest4.setMaxWidth(150);
        Button btTest5 = new Button("test");
        btTest5.setMaxWidth(150);
        Button btTest6 = new Button("test");
        btTest6.setMaxWidth(150);

        Separator separator1 = new Separator();
        Separator separator2 = new Separator();
        Separator separator3 = new Separator();
        Separator separator4 = new Separator();
        Separator separator5 = new Separator();


        channelList.getChildren().addAll(btTest1, separator1, btTest2, separator2 ,btTest3, separator3, btTest4,
                separator4 ,btTest5, separator5, btTest6);

        return channelList;
    }


    // get text input from the socket here
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
                sendText(userText.getText());
                userText.clear();
            }
        });


        Button sendButton = new Button("Send");
        sendButton.setMinSize(100,50);
        sendButton.setMaxSize(100,50);
        sendButton.setOnAction(e ->
        {
            sendText(userText.getText());
            userText.clear();
        });

        HBox.setHgrow(userText, Priority.ALWAYS);
        textArea.getChildren().addAll(userText, sendButton);

        return textArea;
    }

    private HBox getStatusBar(){
        HBox statusBar = new HBox(0);
        statusBar.maxHeight(50);

        Button buttonOne = new Button("A");
        Button buttonTwo = new Button("B");
        Button buttonThree = new Button("C");

        buttonOne.setMinWidth(80);
        buttonTwo.setMinWidth(80);
        buttonThree.setMinWidth(80);

        statusBar.getChildren().add(buttonOne);
        statusBar.getChildren().add(buttonTwo);
        statusBar.getChildren().add(buttonThree);

        return statusBar;

    }

    private void sendText(String text) {
        outputStream.write("(" + client.username + "): " + text + "\n");
        outputStream.flush();

        return;

    }

    private HBox createUser()
    {
        HBox userBar = new HBox();
        userBar.setStyle("-fx-padding: 4;" + "-fx-border-color: black;");
        userBar.setSpacing(7);
        userBar.setAlignment(Pos.CENTER_LEFT);



        Image userImage = new Image(new File("src/images/pic.png").toURI().toString());

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